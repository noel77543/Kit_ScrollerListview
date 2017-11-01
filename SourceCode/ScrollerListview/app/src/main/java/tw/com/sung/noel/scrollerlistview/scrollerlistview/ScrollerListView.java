package tw.com.sung.noel.scrollerlistview.scrollerlistview;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import tw.com.sung.noel.scrollerlistview.implement.OnHorizontalScrollListener;
import tw.com.sung.noel.scrollerlistview.implement.OnItemClickListener;
import tw.com.sung.noel.scrollerlistview.implement.OnItemLongClickListener;
import tw.com.sung.noel.scrollerlistview.implement.OnLeftScrollListener;
import tw.com.sung.noel.scrollerlistview.implement.OnRightScrollListener;
import tw.com.sung.noel.scrollerlistview.implement.OnVerticalScrollListener;
import tw.com.sung.noel.scrollerlistview.topheaderlayout.TopHeaderLayout;
import tw.com.sung.noel.scrollerlistview.bottomlistview.BottomListView;

import static tw.com.sung.noel.scrollerlistview.bottomlistview.BottomListView.LEFT;
import static tw.com.sung.noel.scrollerlistview.bottomlistview.BottomListView.RIGHT;


/**
 * Created by noel on 2017/10/12.
 */

public class ScrollerListView extends LinearLayout implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, OnHorizontalScrollListener, View.OnClickListener, View.OnLongClickListener {

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnVerticalScrollListener onVerticalScrollListener;
    private OnRightScrollListener onRightScrollListener;
    private OnLeftScrollListener onLeftScrollListener;


    private ScrollerListViewAdapter adapter;
    private BottomListView bottomListView;
    private Context context;
    private View headerView;
    private TopHeaderLayout topHeaderLayout;


    //置頂header 與 header 與 item
    public static final int TOP_HEADER = -1;
    public static final int HEADER = 0;
    public static final int ITEM = 1;


    @IntDef({TOP_HEADER, HEADER, ITEM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface viewType {

    }


    public ScrollerListView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ScrollerListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ScrollerListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    //----------------------

    /**
     * 起始點
     */
    private void init() {
        setOrientation(VERTICAL);
        initViews();
        addView(topHeaderLayout);
        addView(bottomListView);
        bottomListView.setOnHorizontalScrollListener(this);
        topHeaderLayout.setOnHorizontalScrollListener(this);
    }
    //---------------------- todo private method

    /**
     * 調整元件高度寬度 與 Margine
     */
    private LayoutParams getViewLayoutParams(int viewWidth, int viewHeight) {
        return new LayoutParams(viewWidth, viewHeight);
    }

    private LayoutParams getViewLayoutParams(int viewWidth, int viewHeight, float viewWeight) {
        return new LayoutParams(viewWidth, viewHeight, viewWeight);
    }

    //---------------------- todo private method

    /**
     * 更新置頂headerview
     */
    private void updateHeaderView(int firstVisibleItem, final AbsListView absListView) {
        if (headerView != null) {
            topHeaderLayout.removeView(headerView);
        }
        headerView = adapter.getCustomHeaderView(firstVisibleItem, headerView, absListView);
        headerView.setLayoutParams(getViewLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        topHeaderLayout.addView(headerView);
    }

    //---------------------- todo private method

    /**
     * 初始化view
     */
    private void initViews() {
        topHeaderLayout = new TopHeaderLayout(context);

        bottomListView = new BottomListView(context);
        bottomListView.setLayoutParams(getViewLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        bottomListView.setOnScrollListener(this);//必須監聽
    }
    //---------------------- todo public method

    /**
     * 供使用者設置adapter
     */
    public void setAdapter(ScrollerListViewAdapter adapter) {
        this.adapter = adapter;
        bottomListView.setAdapter(adapter);
    }
    //---------------------- todo public method for interface

    /**
     * item 與內部HEADER 與置頂HEADER 對外的click接口
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        bottomListView.setOnItemClickListener(this);
        topHeaderLayout.setOnClickListener(this);
    }

    /**
     * 置頂header click事件
     */
    @Override
    public void onClick(View view) {
        int topHeaderPosition = bottomListView.getFirstVisiblePosition();
        if (topHeaderPosition > 1) {
            topHeaderPosition = topHeaderPosition - 1;
        }
        onItemClickListener.onHeaderClick(bottomListView, headerView, topHeaderPosition, topHeaderPosition);
    }

    //listview onItemclick / headerclick
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ScrollerListViewAdapter adapter = (ScrollerListViewAdapter) adapterView.getAdapter();
        @viewType int viewType = adapter.getItemViewType(i);

        if (onItemClickListener != null) {
            switch (viewType) {
                case HEADER:
                    //被套件使用者覆寫的行為
                    onItemClickListener.onHeaderClick(adapterView, view, i, l);
                    break;
                case ITEM:
                    //被套件使用者覆寫的行為
                    onItemClickListener.onItemClick(adapterView, view, i, l);
                    break;
            }
        }
    }

    //---------------------- todo public method for interface

    /**
     * item  與內部HEADER 與置頂HEADER 對外的long click接口
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
        bottomListView.setOnItemLongClickListener(this);
        topHeaderLayout.setOnLongClickListener(this);
    }

    /**
     * 置頂header longclick事件
     */
    @Override
    public boolean onLongClick(View view) {
        int topHeaderPosition = bottomListView.getFirstVisiblePosition();
        if (topHeaderPosition > 1) {
            topHeaderPosition = topHeaderPosition - 1;
        }
        onItemLongClickListener.onHeaderLongClick(bottomListView, headerView, topHeaderPosition, topHeaderPosition);
        return true;
    }

    //onItemLongClick
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        ScrollerListViewAdapter adapter = (ScrollerListViewAdapter) adapterView.getAdapter();
        if (onItemLongClickListener != null) {
            switch (adapter.getItemViewType(i)) {
                case HEADER:
                    //被套件使用者覆寫的行為
                    onItemLongClickListener.onHeaderLongClick(adapterView, view, i, l);
                    break;
                case ITEM:
                    //被套件使用者覆寫的行為
                    onItemLongClickListener.onItemLongClick(adapterView, view, i, l);
                    break;
            }
        }
        return true;
    }

    //---------------------- todo public method for interface

    /**
     * scroll 接口
     */
    public void setOnVerticalScrollListener(OnVerticalScrollListener onVerticalScrollListener) {
        this.onVerticalScrollListener = onVerticalScrollListener;
        bottomListView.setOnScrollListener(this);
    }

    //滾動行為改變時
    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {

        if (onVerticalScrollListener != null) {
            //被套件使用者覆寫的行為
            onVerticalScrollListener.onVerticalScrollStateChanged(absListView, scrollState);
        }
    }

    //滾動時
    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        //沒資料則直接return
        if (visibleItemCount == 0)
            return;

        //目前最上面項目的種類
        @viewType int nowItemType = adapter.getItemViewType(firstVisibleItem);
        //取得前一個項目的種類
        @viewType int previousItemType = adapter.getItemViewType(firstVisibleItem - 1);


        //第0 或者 前一項type等於目前type（用於由上往下滾動） 或者 前一項type = HEADER（用於由下往上滾動）
        if (firstVisibleItem == 0 || nowItemType == previousItemType || previousItemType == HEADER) {
            updateHeaderView(firstVisibleItem, absListView);
        }

        if (onVerticalScrollListener != null) {
            //被套件使用者覆寫的行為
            onVerticalScrollListener.onVerticalScroll(absListView, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    //---------------------- todo public method for interface

    /**
     * 當左右滑動listview中的Item或header時
     */
    @Override
    public void onHorozontalScroll(@BottomListView.scrollDirection int direction, int position) {

        int viewType = TOP_HEADER;//此為置頂header

        if (position > -1) {
            viewType = adapter.getItemViewType(position);
        }

        switch (direction) {
            //左滑
            case LEFT:
                if (onLeftScrollListener != null) {
                    if (viewType == TOP_HEADER) {
                        //這裡是為了確保置頂header的行為所對應的類別符合邏輯
                        int topHeaderPosition = bottomListView.getFirstVisiblePosition();
                        if (topHeaderPosition > 0) {
                            topHeaderPosition = topHeaderPosition - 1;
                        }
                        onLeftScrollListener.onHeaderLeftScroll(topHeaderPosition);

                    } else if (viewType == HEADER) {
                        onLeftScrollListener.onHeaderLeftScroll(position);

                    } else {
                        onLeftScrollListener.onItemLeftScroll(position);
                    }
                }
                break;
            //右滑
            case RIGHT:
                if (onRightScrollListener != null) {
                    if (viewType == TOP_HEADER) {
                        //這裡是為了確保置頂header的行為所對應的類別符合邏輯
                        int topHeaderPosition = bottomListView.getFirstVisiblePosition();
                        if (topHeaderPosition > 0) {
                            topHeaderPosition = topHeaderPosition - 1;
                        }
                        onRightScrollListener.onHeaderRightScroll(topHeaderPosition);
                    } else if (viewType == HEADER) {
                        onRightScrollListener.onHeaderRightScroll(position);
                    } else {
                        onRightScrollListener.onItemRightScroll(position);
                    }
                }
                break;
        }
    }

    //---------------------- todo public method for interface

    /**
     * ITEM往右邊滑動的接口
     */
    public void setOnRightScrollListener(OnRightScrollListener onRightScrollListener) {
        this.onRightScrollListener = onRightScrollListener;
        //允許右滑
        bottomListView.setRightScrollable(true);
        topHeaderLayout.setRightScrollable(true);
    }

    //---------------------- todo public method for interface

    /**
     * ITEM往左邊滑動的接口
     */
    public void setOnLeftScrollListener(OnLeftScrollListener onLeftScrollListener) {
        this.onLeftScrollListener = onLeftScrollListener;
        //允許左滑
        bottomListView.setLeftScrollable(true);
        topHeaderLayout.setLeftScrollable(true);
    }
}
