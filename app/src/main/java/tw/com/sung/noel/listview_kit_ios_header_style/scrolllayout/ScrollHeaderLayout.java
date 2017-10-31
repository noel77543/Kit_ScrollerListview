package tw.com.sung.noel.listview_kit_ios_header_style.scrolllayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

import tw.com.sung.noel.listview_kit_ios_header_style.implement.OnHorizontalScrollListener;
import tw.com.sung.noel.listview_kit_ios_header_style.scrolllistview.ScrollListView;

import static tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview.iOSHeaderListView.TOP_HEADER;
import static tw.com.sung.noel.listview_kit_ios_header_style.scrolllistview.ScrollListView.LEFT;
import static tw.com.sung.noel.listview_kit_ios_header_style.scrolllistview.ScrollListView.NONE;
import static tw.com.sung.noel.listview_kit_ios_header_style.scrolllistview.ScrollListView.RIGHT;

/**
 * Created by noel on 2017/10/27.
 */

public class ScrollHeaderLayout extends FrameLayout implements View.OnTouchListener {

    private OnHorizontalScrollListener onHorizontalScrollListener;

    //滑動的主要開關，預設為不執行
    private boolean isScrollable = false;
    //右滾動開關
    private boolean isRightScrollable = false;
    //左滾動開關
    private boolean isLeftScrollable = false;

    //手指按下Y的坐標
    private int downY;
    //手指按下X的坐標
    private int downX;
    //螢幕寬度
    private int screenWidth;
    //滑動類
    private Scroller scroller;
    //速度追蹤對象
    private VelocityTracker velocityTracker;
    //滑動的最小距離
    private int scrollMinDistance;

    /**
     * 用來得知item滑出屏幕的方向,向左或者向右或不動
     */
    private
    @ScrollListView.scrollDirection
    int scrollDirection = NONE;


    public ScrollHeaderLayout(Context context) {
        super(context);
        screenWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        scroller = new Scroller(context);
        scrollMinDistance = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        setOnTouchListener(this);
    }

    public ScrollHeaderLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollHeaderLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //----------------------------

    /**
     * 往右滑動，getScrollX()返回的是左邊緣的距離，就是以View左邊緣為原點到開始滑動的距離，所以向右邊滑動為負值
     */
    private void scrollRight() {
        scrollDirection = RIGHT;
        //左邊緣的距離越遠 速度越快
        final int delta = (screenWidth + getScrollX());
        // startScroll方法來設置一些滾動的參數，在computeScroll()方法中調用scrollTo來滾動item
        //從(startＸ,startY)到(dX,dY),時間duration
        scroller.startScroll(getScrollX(), 0, -delta, 0,
                Math.abs(delta));
        postInvalidate(); // 刷新

    }

    //----------------------------

    /**
     * 向左滑動
     */
    private void scrollLeft() {
        scrollDirection = LEFT;
        final int delta = (screenWidth - getScrollX());
        scroller.startScroll(getScrollX(), 0, delta, 0,
                Math.abs(delta));
        postInvalidate(); // 刷新
    }


    //----------------------------

    /**
     * 根據手指滾動itemView的水平距離來判斷是滾動到開始位置還是向左或者向右滾動
     */
    private void scrollByHorizontalDistance() {
        // 如果向左滾動的距離大於屏幕的3分之1，就進行滑動該方向到底
        if (getScrollX() >= screenWidth / 3) {
            scrollLeft();
        } else if (getScrollX() <= -screenWidth / 3) {
            scrollRight();
        } else {
            // 回到原始位置
            scrollTo(0, 0);
        }
    }

    //----------------------------

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (isScrollable) {
            requestDisallowInterceptTouchEvent(true);
            int x = (int) event.getX();
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    MotionEvent cancelEvent = MotionEvent.obtain(event);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (event.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    onTouchEvent(cancelEvent);

                    int deltaX = downX - x;
                    downX = x;

                    // 手指拖動itemView滾動, deltaX大於0向左滾動，小於0向右滾
                    if (isRightScrollable() && deltaX < 0) {
                        scrollBy(deltaX, 0);
                    } else if (isLeftScrollable() && deltaX > 0) {
                        scrollBy(deltaX, 0);
                    }
                    break; //拖動的時候ListView不滾動
                case MotionEvent.ACTION_UP:
                    scrollByHorizontalDistance();
                    closeVelocityTracker();
                    // 手指離開的時候就不響應左右滾動
                    isScrollable = false;
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    //----------------------------
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                addVelocityTracker(motionEvent);
                //手指接觸螢幕時的X座標
                downX = (int) motionEvent.getX();
                //手指接觸螢幕時的Y座標
                downY = (int) motionEvent.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                //向右移動 與 向左移動
                if ((Math.abs(motionEvent.getX() - downX) > scrollMinDistance && Math.abs(motionEvent.getY() - downY) < scrollMinDistance)) {
                    isScrollable = true;
                }
                break;
        }

        return false;
    }

    //----------------------------
    @Override
    public void computeScroll() {
        // 調用startScroll的時候scroller.computeScrollOffset()返回true，
        if (scroller.computeScrollOffset()) {
            // 讓ListView item根據當前的滾動偏移量進行滾動
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();

            // 滾動動畫結束的時候 移除該項目（slidePosition）
            if (scroller.isFinished() && onHorizontalScrollListener != null) {
                scrollTo(0, 0);
                //置頂header 因不在listivew中
                onHorizontalScrollListener.onHorozontalScroll(scrollDirection, TOP_HEADER);
            }
        }
    }

    //----------------------------

    /**
     * 用戶的速度跟蹤器
     */
    private void addVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }

        velocityTracker.addMovement(event);
    }

    //----------------------------

    /**
     * 移除速度跟蹤器
     */
    private void closeVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    public boolean isRightScrollable() {
        return isRightScrollable;
    }

    public void setRightScrollable(boolean rightScrollable) {
        isRightScrollable = rightScrollable;
    }

    public boolean isLeftScrollable() {
        return isLeftScrollable;
    }

    public void setLeftScrollable(boolean leftScrollable) {
        isLeftScrollable = leftScrollable;
    }

    /**
     * 滾動監聽
     */
    public void setOnHorizontalScrollListener(OnHorizontalScrollListener onHorizontalScrollListener) {
        this.onHorizontalScrollListener = onHorizontalScrollListener;
    }
}
