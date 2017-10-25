package tw.com.sung.noel.listview_kit_ios_header_style.scrollLayout;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
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

    private final double SCROLL_SPEED = 30.0;
    private final int TEXTVIEW_DELETE_WIDTH = 250;
    private Context context;

    private String deleteType;
    private ViewDragHelper viewDragHelper;
    private View contentView;
    private TextView tvDelete;
    private int draggedX;
    private OnDeleteClickListener onDeleteClickListener;

    public ScrollLayout(Context context) {
        super(context);
        this.context = context;

        initDeleteTextView();
        addView(tvDelete);
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
     * 設置deleteType
     * */
    public void setDeleteType(String deleteType) {
        this.deleteType = deleteType;
    }

    public String getDeleteType() {
        return deleteType;
    }

    //-------------------------------

    /**
     * 初始化DeleteTextView
     */
    private void initDeleteTextView() {
        tvDelete = new TextView(context);
        tvDelete.setText("DELETE");
        tvDelete.setTextSize(16);
        tvDelete.setTextColor(Color.WHITE);
        tvDelete.setBackgroundColor(Color.RED);
        tvDelete.setLayoutParams(getViewLayoutParams(TEXTVIEW_DELETE_WIDTH, ViewGroup.LayoutParams.MATCH_PARENT));
        tvDelete.setGravity(Gravity.CENTER);
        tvDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/10/24  點擊後需要做 依照判斷是屬於header還是item  分別做 整類別刪除 與該項刪除

                onDeleteClickListener.onDeleteClick(getDeleteType());
            }
        });
    }

    //-------------------------------

    /**
     * interface 當按下delete 的接口
     */
    public interface OnDeleteClickListener {
        void onDeleteClick(String deleteType);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }


    //-------------------------------

    /**
     * 當所有addview 行為結束
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        contentView = getChildAt(0);
        tvDelete = (TextView) getChildAt(1);
        tvDelete.setVisibility(GONE);
    }

    //------------------------
    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View view, int i) {
            return view == contentView || view == tvDelete;
        }

        //------------------------
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            draggedX = left;
            if (changedView == contentView) {
                tvDelete.offsetLeftAndRight(dx);
            } else {
                contentView.offsetLeftAndRight(dx);
            }
            if (tvDelete.getVisibility() == View.GONE) {
                tvDelete.setVisibility(View.VISIBLE);
            }
            invalidate();
        }

        //------------------------

        /**
         * 定義水平滑動
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == contentView) {
                final int leftBound = getPaddingLeft();
                final int minLeftBound = -leftBound - TEXTVIEW_DELETE_WIDTH;
                final int newLeft = Math.min(Math.max(minLeftBound, left), 0);
                return newLeft;
            } else {
                final int minLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() - TEXTVIEW_DELETE_WIDTH;
                final int maxLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() + getPaddingRight();
                final int newLeft = Math.min(Math.max(left, minLeftBound), maxLeftBound);
                return newLeft;
            }
        }
        //------------------------

        /**
         * 第二個VIEW的寬度
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return TEXTVIEW_DELETE_WIDTH;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            boolean settleToOpen = false;

            if (xvel < -SCROLL_SPEED || draggedX <= -TEXTVIEW_DELETE_WIDTH ) {
                settleToOpen = true;
            }

            final int settleDestX = settleToOpen ? -TEXTVIEW_DELETE_WIDTH : 0;
            viewDragHelper.smoothSlideViewTo(contentView, settleDestX, 0);
            ViewCompat.postInvalidateOnAnimation(ScrollLayout.this);
        }
    }

    //------------------------

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (viewDragHelper.shouldInterceptTouchEvent(ev)) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }
    //------------------------

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
