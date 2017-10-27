package tw.com.sung.noel.listview_kit_ios_header_style.scrolllayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Scroller;

import tw.com.sung.noel.listview_kit_ios_header_style.scrolllistview.ScrollListView;

import static tw.com.sung.noel.listview_kit_ios_header_style.scrolllistview.ScrollListView.LEFT;
import static tw.com.sung.noel.listview_kit_ios_header_style.scrolllistview.ScrollListView.NONE;
import static tw.com.sung.noel.listview_kit_ios_header_style.scrolllistview.ScrollListView.RIGHT;

/**
 * Created by noel on 2017/10/27.
 */

public class ScrollHeader extends LinearLayout implements View.OnTouchListener {

    //右滾動開關
    private boolean isRightScrollable = false;
    //左滾動開關
    private boolean isLeftScrollable = false;


    //手指按下Y的坐標
    private int downY;
    //手指按下X的坐標
    private int downX;
    //屏幕寬度
    private int screenWidth;

    //滑動類
    private Scroller scroller;
    //速度追蹤對象
    private VelocityTracker velocityTracker;
    //滑動的主要開關，預設為不執行
    private boolean isSlide = false;
    //滑動的最小距離
    private int scrollMinDistance;
    //右滾動的接口
    private OnHeaderRightScrollListener onHeaderRightScrollListener;
    //左滾動的接口
    private OnHeaderLeftScrollListener onHeaderLeftScrollListener;
    /**
     * 用來得知item滑出屏幕的方向,向左或者向右或不動
     */
    private
    @ScrollListView.scrollDirection
    int scrollDirection = NONE;


    public ScrollHeader(Context context) {
        super(context);
        screenWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        scroller = new Scroller(context);
        scrollMinDistance = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        setOnTouchListener(this);
    }

    public ScrollHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        postInvalidate(); // 刷新itemView

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
        postInvalidate(); // 刷新itemView
    }


    //----------------------------

    /**
     * 根據手指滾動itemView的距離來判斷是滾動到開始位置還是向左或者向右滾動
     */
    private void scrollByDistanceX() {
        // 如果向左滾動的距離大於屏幕的8分之1，就進行滑動該方向到底的動畫
        if (getScrollX() >= screenWidth / 8) {
            scrollLeft();
        } else if (getScrollX() <= -screenWidth / 8) {
            scrollRight();
        } else {
            // 回到原始位置
            scrollTo(0, 0);
        }
    }
    //----------------------------

    /**
     * 處理拖曳
     */

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isSlide) {
            requestDisallowInterceptTouchEvent(true);
            addVelocityTracker(motionEvent);
            final int action = motionEvent.getAction();
            int x = (int) motionEvent.getX();
            switch (action) {
                case MotionEvent.ACTION_MOVE:

                    MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (motionEvent.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    onTouchEvent(cancelEvent);

                    int deltaX = downX - x;
                    downX = x;

                    // 手指拖動itemView滾動, deltaX大於0向左滾動，小於0向右滾
                    if (isRightScrollable() && deltaX < 0) {
                        scrollBy(deltaX, 0);
                    } else if (isLeftScrollable() && deltaX > 0) {
                        scrollBy(deltaX, 0);
                    }
                    return false; //拖動的時候ListView不滾動
                case MotionEvent.ACTION_UP:

                    scrollByDistanceX();
                    closeVelocityTracker();
                    // 手指離開的時候就不響應左右滾動
                    isSlide = false;
                    break;
            }
        }

        //否則直接交給ListView來處理onTouchEvent事件
        return super.onTouchEvent(motionEvent);
    }

    //----------------------------
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                addVelocityTracker(motionEvent);
                // 假如scroller滾動還沒有結束，直接return
                if (!scroller.isFinished()) {
                    return true;
                }
                //手指接觸螢幕時的X座標
                downX = (int) motionEvent.getX();
                //手指接觸螢幕時的Y座標
                downY = (int) motionEvent.getY();
                break;

            case MotionEvent.ACTION_MOVE:

                //向右移動 與 向左移動
                if ((Math.abs(motionEvent.getX() - downX) > scrollMinDistance && Math.abs(motionEvent.getY() - downY) < scrollMinDistance)) {
                    isSlide = true;
                    //return 給onTouchEvent 進行對應行為
                    return super.onTouchEvent(motionEvent);
                }
                break;
            case MotionEvent.ACTION_UP:

                closeVelocityTracker();
                //return 給onTouchEvent 進行對應行為
                return super.onTouchEvent(motionEvent);
        }

        //否則直接交給ListView來處理onTouchEvent事件
        return true;
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
            if (scroller.isFinished()) {
                scrollTo(0, 0);
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


    public void setOnHeaderRightScrollListener(OnHeaderRightScrollListener onHeaderRightScrollListener) {
        this.onHeaderRightScrollListener = onHeaderRightScrollListener;

    }

    public interface OnHeaderRightScrollListener {
        void onHeaderRightScroll(int scrollDirection);
    }

    public void setOnHeaderLeftScrollListener(OnHeaderLeftScrollListener onHeaderLeftScrollListener) {
        this.onHeaderLeftScrollListener = onHeaderLeftScrollListener;

    }

    public interface OnHeaderLeftScrollListener {
        void onHeaderScroll(int scrollDirection);
    }
}
