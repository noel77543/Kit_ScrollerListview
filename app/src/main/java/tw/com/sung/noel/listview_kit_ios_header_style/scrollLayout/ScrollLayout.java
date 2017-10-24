package tw.com.sung.noel.listview_kit_ios_header_style.scrollLayout;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by noel on 2017/10/24.
 */

public class ScrollLayout extends LinearLayout {

    private ViewDragHelper viewDragHelper;

    private final double SCROLL_SPEED = 50.0;
    private final int SECONDVIEW_WIDTH = 250;
    private View contentView;
    private LinearLayout secondView;
    private TextView tvDelete;
    private int dragDistance;
    private int draggedX;

    private Context context;

    public ScrollLayout(Context context) {
        super(context);
        this.context = context;

        initSecondView();
        initDeleteTextView();
        secondView.addView(tvDelete);
        addView(secondView);
        viewDragHelper = ViewDragHelper.create(this, new DragHelperCallback());
    }

    public ScrollLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        viewDragHelper = ViewDragHelper.create(this, new DragHelperCallback());
    }

    public ScrollLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        viewDragHelper = ViewDragHelper.create(this, new DragHelperCallback());
    }
    //-------------------------------

    /**
     * 設置contentview
     * 並重新定義高度
     */
    public void setContentView(View contentView) {
        this.contentView = contentView;
        addView(contentView, 0);
        setLayoutParams(getViewLayoutParams(contentView.getLayoutParams().width, contentView.getLayoutParams().height));
    }
    //-------------------------------

    /**
     * 初始化secondview
     */
    private void initSecondView() {
        secondView = new LinearLayout(context);
        secondView.setLayoutParams(getViewLayoutParams(SECONDVIEW_WIDTH, ViewGroup.LayoutParams.MATCH_PARENT));
        secondView.setGravity(Gravity.END);
    }
    //-------------------------------

    /**
     * 初始化DeleteImageView
     */
    private void initDeleteTextView() {
        tvDelete = new TextView(context);
        tvDelete.setText("DELETE");
        tvDelete.setTextSize(16);
        tvDelete.setTextColor(Color.WHITE);
        tvDelete.setBackgroundColor(Color.RED);
        tvDelete.setLayoutParams(getViewLayoutParams(SECONDVIEW_WIDTH, ViewGroup.LayoutParams.MATCH_PARENT));
        tvDelete.setPadding(15, 0, 15, 0);
        tvDelete.setGravity(Gravity.CENTER);
        tvDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/10/24  點擊後需要做 依照判斷是屬於header還是item  分別做 整類別刪除 與該項刪除




            }
        });
    }

    //-------------------------------
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        contentView = getChildAt(0);
        secondView = (LinearLayout) getChildAt(1);
        secondView.setVisibility(GONE);
    }
    //-------------------------------

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        dragDistance = secondView.getMeasuredWidth();
    }
    //-------------------------------

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View view, int i) {
            return view == contentView || view == secondView;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            draggedX = left;
            if (changedView == contentView) {
                secondView.offsetLeftAndRight(dx);
            } else {
                contentView.offsetLeftAndRight(dx);
            }
            if (secondView.getVisibility() == View.GONE) {
                secondView.setVisibility(View.VISIBLE);
            }
            invalidate();
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == contentView) {
                final int leftBound = getPaddingLeft();
                final int minLeftBound = -leftBound - dragDistance;
                final int newLeft = Math.min(Math.max(minLeftBound, left), 0);
                return newLeft;
            } else {
                final int minLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() - dragDistance;
                final int maxLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() + getPaddingRight();
                final int newLeft = Math.min(Math.max(left, minLeftBound), maxLeftBound);
                return newLeft;
            }
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return dragDistance;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            boolean settleToOpen = false;
            if (xvel > SCROLL_SPEED) {
                settleToOpen = false;
            } else if (xvel < -SCROLL_SPEED) {
                settleToOpen = true;
            } else if (draggedX <= -dragDistance / 2) {
                settleToOpen = true;
            } else if (draggedX > -dragDistance / 2) {
                settleToOpen = false;
            }

            final int settleDestX = settleToOpen ? -dragDistance : 0;
            viewDragHelper.smoothSlideViewTo(contentView, settleDestX, 0);
            ViewCompat.postInvalidateOnAnimation(ScrollLayout.this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (viewDragHelper.shouldInterceptTouchEvent(ev)) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    //----------------------

    /**
     * 調整元件高度寬度 與 Margine
     */
    private LinearLayout.LayoutParams getViewLayoutParams(int viewWidth, int viewHeight) {
        return new LinearLayout.LayoutParams(viewWidth, viewHeight);
    }

    private LinearLayout.LayoutParams getViewLayoutParamsGravity(int viewWidth, int viewHeight, int gravity) {
        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(viewWidth, viewHeight);
        layoutparams.gravity = gravity;//Gravity.CENTER
        return layoutparams;
    }

    private LinearLayout.LayoutParams setViewLayoutMargine(LinearLayout.LayoutParams layoutParams, int marginL, int marginT, int marginR, int marginB) {
        layoutParams.setMargins(marginL, marginT, marginR, marginB);
        return layoutParams;
    }


}
