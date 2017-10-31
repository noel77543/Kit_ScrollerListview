package tw.com.sung.noel.listview_kit_ios_header_style.scrolllistview;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Scroller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import tw.com.sung.noel.listview_kit_ios_header_style.implement.OnHorizontalScrollListener;

/**
 * Created by noel on 2017/10/26.
 */

public class ScrollListView extends ListView {

    //用來控制可不可滑動, 需開啟右滑接口才會true
    private boolean isRightScrollable = false;
    //用來控制可不可滑動, 需開啟左滑接口才會true
    private boolean isLeftScrollable = false;

    //當前滑動的ListView　position
    private int scrollPosition;
    //手指按下Y的坐標
    private int downY;
    //手指按下X的坐標
    private int downX;
    //屏幕寬度
    private int screenWidth;
    //ListView的item
    private View itemView;
    //滑動類
    private Scroller scroller;
    //速度追蹤對象
    private VelocityTracker velocityTracker;
    //滑動的主要開關，預設為不執行
    private boolean isScrollable = false;
    //滑動的最小距離
    private int scrollMinDistance;
    //滑動後的接口
    private OnHorizontalScrollListener onHorizontalScrollListener;

    //滑動方向 向左 向右 不動
    public static final int LEFT = 9487;
    public static final int RIGHT = 7777;
    public static final int NONE = 6666;

    @IntDef({LEFT, RIGHT, NONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface scrollDirection {

    }

    /**
     * 用來得知item滑出屏幕的方向,向左或者向右或不動
     */
    private
    @scrollDirection
    int scrollDirection = NONE;

    public ScrollListView(Context context) {
        super(context);
        screenWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        scroller = new Scroller(context);
        scrollMinDistance = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public ScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //----------------------------

    /**
     * 判斷點擊的item
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                addVelocityTracker(event);

                // 假如scroller滾動還沒有結束，直接return
                if (!scroller.isFinished()) {
                    return super.dispatchTouchEvent(event);
                }
                //手指接觸螢幕時的X座標
                downX = (int) event.getX();
                //手指接觸螢幕時的Y座標
                downY = (int) event.getY();

                //透過點選的x,y座標位置取得 該項index
                scrollPosition = pointToPosition(downX, downY);

                // 無效的position, 不做任何處理
                if (scrollPosition == AdapterView.INVALID_POSITION) {
                    return super.dispatchTouchEvent(event);
                }

                // 獲取點擊的item view
                itemView = getChildAt(scrollPosition - getFirstVisiblePosition());
                break;

            case MotionEvent.ACTION_MOVE:
                //向右移動 與 向左移動
                if ((Math.abs(event.getX() - downX) > scrollMinDistance && Math.abs(event.getY() - downY) < scrollMinDistance)) {
                    isScrollable = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                closeVelocityTracker();
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    //----------------------------

    /**
     * 往右滑動，getScrollX()返回的是左邊緣的距離，就是以View左邊緣為原點到開始滑動的距離，所以向右邊滑動為負值
     */
    private void scrollRight() {
        scrollDirection = RIGHT;
        //左邊緣的距離越遠 速度越快
        final int delta = (screenWidth + itemView.getScrollX());
        // startScroll方法來設置一些滾動的參數，在computeScroll()方法中調用scrollTo來滾動item
        //從(startＸ,startY)到(dX,dY),時間duration
        scroller.startScroll(itemView.getScrollX(), 0, -delta, 0,
                Math.abs(delta));
        postInvalidate(); // 刷新itemView
    }

    //----------------------------

    /**
     * 向左滑動
     */
    private void scrollLeft() {
        scrollDirection = LEFT;
        final int delta = (screenWidth - itemView.getScrollX());
        scroller.startScroll(itemView.getScrollX(), 0, delta, 0,
                Math.abs(delta));
        postInvalidate(); // 刷新itemView
    }

    //----------------------------

    /**
     * 根據手指滾動itemView的距離來判斷是滾動到開始位置還是向左或者向右滾動
     */
    private void scrollByHorizontalDistance() {
        // 如果向左滾動的距離大於屏幕的3分之1，就進行滑動該方向到底
        if (itemView.getScrollX() >= screenWidth / 3) {
            scrollLeft();
        } else if (itemView.getScrollX() <= -screenWidth / 3) {
            scrollRight();
        } else {
            // 回到原始位置
            itemView.scrollTo(0, 0);
        }
    }

    //----------------------------

    /**
     * 處理該itemView的拖曳
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isScrollable && scrollPosition != AdapterView.INVALID_POSITION) {
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
                        itemView.scrollBy(deltaX, 0);
                    } else if (isLeftScrollable() && deltaX > 0) {
                        itemView.scrollBy(deltaX, 0);
                    }
                    return false;
                case MotionEvent.ACTION_UP:

                    scrollByHorizontalDistance();
                    closeVelocityTracker();
                    // 手指離開的時候就不響應左右滾動
                    isScrollable = false;
                    break;
            }
        }

        //否則直接交給ListView來處理onTouchEvent事件
        return super.onTouchEvent(motionEvent);
    }

    //----------------------------
    @Override
    public void computeScroll() {
        // 調用startScroll的時候scroller.computeScrollOffset()返回true，
        if (scroller.computeScrollOffset()) {
            // 讓ListView item根據當前的滾動偏移量進行滾動
            itemView.scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();

            // 滾動動畫結束的時候 移除該項目（slidePosition）
            if (scroller.isFinished() && onHorizontalScrollListener != null) {
                itemView.scrollTo(0, 0);
                onHorizontalScrollListener.onHorozontalScroll(scrollDirection, scrollPosition);
            }
        }
    }

    //----------------------------

    /**
     * 添加用戶的速度跟蹤器
     */
    private void addVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }

        velocityTracker.addMovement(event);
    }

    //----------------------------

    /**
     * 移除用戶速度跟蹤器
     */
    private void closeVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    //----------------------------

    /**
     * 左滑開關控制
     */
    public void setRightScrollable(boolean isRightScrollable) {
        this.isRightScrollable = isRightScrollable;
    }

    public boolean isRightScrollable() {
        return isRightScrollable;
    }

    //----------------------------

    /**
     * 右滑開關控制
     */
    public void setLeftScrollable(boolean isLeftScrollable) {
        this.isLeftScrollable = isLeftScrollable;
    }

    public boolean isLeftScrollable() {
        return isLeftScrollable;
    }


    //----------------------------

    /**
     * 當ListView 任何item滑出屏幕執行
     */
    public void setOnHorizontalScrollListener(OnHorizontalScrollListener onHorizontalScrollListener) {
        this.onHorizontalScrollListener = onHorizontalScrollListener;
    }

}
