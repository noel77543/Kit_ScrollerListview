package tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import tw.com.sung.noel.listview_kit_ios_header_style.implement.OnHorizontalScrollListener;
import tw.com.sung.noel.listview_kit_ios_header_style.implement.OnScrollItemClickListener;
import tw.com.sung.noel.listview_kit_ios_header_style.implement.OnScrollItemLongClickListener;
import tw.com.sung.noel.listview_kit_ios_header_style.scrolllayout.ScrollHeaderLayout;
import tw.com.sung.noel.listview_kit_ios_header_style.scrolllistview.ScrollListView;

import static tw.com.sung.noel.listview_kit_ios_header_style.scrolllistview.ScrollListView.LEFT;
import static tw.com.sung.noel.listview_kit_ios_header_style.scrolllistview.ScrollListView.RIGHT;


/**
 * Created by noel on 2017/10/12.
 */

public class iOSHeaderListView extends LinearLayout implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, OnHorizontalScrollListener, View.OnClickListener, View.OnLongClickListener {

    private OnScrollItemClickListener onScrollItemClickListener;
    private OnScrollItemLongClickListener onScrollItemLongClickListener;
    private OniOSHeaderListViewVerticalScrollListener oniOSHeaderListViewVerticalScrollListener;
    private OniOSHeaderListViewRightScrollListener oniOSHeaderListViewRightScrollListener;
    private OniOSHeaderListViewLeftScrollListener oniOSHeaderListViewLeftScrollListener;


    private iOSHeaderListViewAdapter adapter;
    private ScrollListView listView;
    private Context context;
    private View headerView;
    private ScrollHeaderLayout scrollHeaderLayout;


    //置頂header 與 header 與 item
    public static final int TOP_HEADER = -1;
    public static final int HEADER = 0;
    public static final int ITEM = 1;


    @IntDef({TOP_HEADER, HEADER, ITEM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface viewType {

    }


    public iOSHeaderListView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public iOSHeaderListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public iOSHeaderListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        listView = new ScrollListView(context);
        scrollHeaderLayout = new ScrollHeaderLayout(context);

        initListView();
        addView(listView);
    }

    //----------------------

    /**
     * 初始化listview
     */
    private void initListView() {
        listView.setLayoutParams(getViewLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        listView.setOnScrollListener(this);//必須監聽
    }
    //----------------------

    /**
     * 供使用者設置adapter
     */
    public void setiOSHeaderListViewAdapter(iOSHeaderListViewAdapter adapter) {
        this.adapter = adapter;
        listView.setAdapter(adapter);
        listView.setOnHorizontalScrollListener(this);
        scrollHeaderLayout.setOnHorizontalScrollListener(this);
    }
    //----------------------

    /**
     * item 與內部HEADER 與置頂HEADER 對外的click接口
     */
    public void setOniOSHeaderListViewItemClickListener(OnScrollItemClickListener onScrollItemClickListener) {
        this.onScrollItemClickListener = onScrollItemClickListener;
        listView.setOnItemClickListener(this);
        scrollHeaderLayout.setOnClickListener(this);
    }

    //listview onItemclick / headerclick
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        iOSHeaderListViewAdapter adapter = (iOSHeaderListViewAdapter) adapterView.getAdapter();
        @viewType int viewType = adapter.getItemViewType(i);

        if (onScrollItemClickListener != null) {
            switch (viewType) {
                case HEADER:
                    //被套件使用者覆寫的行為
                    onScrollItemClickListener.oniOSHeaderListViewHeaderClick(adapterView, view, i, l);
                    break;
                case ITEM:
                    //被套件使用者覆寫的行為
                    onScrollItemClickListener.oniOSHeaderListViewItemClick(adapterView, view, i, l);
                    break;
            }
        }
    }

    //----------------------

    /**
     * item  與內部HEADER 與置頂HEADER 對外的long click接口
     */
    public void setOniOSHeaderListViewItemLongClickListener(OnScrollItemLongClickListener onScrollItemLongClickListener) {
        this.onScrollItemLongClickListener = onScrollItemLongClickListener;
        listView.setOnItemLongClickListener(this);
        scrollHeaderLayout.setOnLongClickListener(this);
    }

    //onItemLongClick
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        iOSHeaderListViewAdapter adapter = (iOSHeaderListViewAdapter) adapterView.getAdapter();
        if (onScrollItemLongClickListener != null) {
            switch (adapter.getItemViewType(i)) {
                case HEADER:
                    //被套件使用者覆寫的行為
                    onScrollItemLongClickListener.oniOSHeaderListViewHeaderLongClick(adapterView, view, i, l);
                    break;
                case ITEM:
                    //被套件使用者覆寫的行為
                    onScrollItemLongClickListener.oniOSHeaderListViewItemLongClick(adapterView, view, i, l);
                    break;
            }
        }
        return true;
    }

    //----------------------

    /**
     * scroll 接口
     */
    public void setOniOSHeaderListViewVerticalScrollListener(OniOSHeaderListViewVerticalScrollListener oniOSHeaderListViewVerticalScrollListener) {
        this.oniOSHeaderListViewVerticalScrollListener = oniOSHeaderListViewVerticalScrollListener;
        oniOSHeaderListViewScroll();
    }

    //interface
    public interface OniOSHeaderListViewVerticalScrollListener {
        void oniOSHeaderListViewVerticalScrollStateChanged(AbsListView absListView, int scrollState);

        void oniOSHeaderListViewVerticalScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    }

    //對iosheaderlistview 中的listview 套上OnScroll監聽
    private void oniOSHeaderListViewScroll() {
        listView.setOnScrollListener(this);
    }

    //滾動行為改變時
    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {

        if (oniOSHeaderListViewVerticalScrollListener != null) {
            //被套件使用者覆寫的行為
            oniOSHeaderListViewVerticalScrollListener.oniOSHeaderListViewVerticalScrollStateChanged(absListView, scrollState);
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

        if (oniOSHeaderListViewVerticalScrollListener != null) {
            //被套件使用者覆寫的行為
            oniOSHeaderListViewVerticalScrollListener.oniOSHeaderListViewVerticalScroll(absListView, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    //----------------------

    /**
     * 更新置頂headerview
     */
    private void updateHeaderView(int firstVisibleItem, final AbsListView absListView) {
        if (headerView != null) {
            scrollHeaderLayout.removeView(headerView);
        }
        if (scrollHeaderLayout != null) {
            removeView(scrollHeaderLayout);
        }
        addView(scrollHeaderLayout, 0);
        headerView = adapter.getCustomHeaderView(firstVisibleItem, headerView, absListView);
        headerView.setLayoutParams(getViewLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        scrollHeaderLayout.addView(headerView);

    }

    //----------------------

    /**
     * 當左右滑動listview中的Item或header時
     */
    @Override
    public void onHorozontalScroll(@ScrollListView.scrollDirection int direction, int position) {

        int viewType = TOP_HEADER;//此為置頂header

        if (position > -1) {
            viewType = adapter.getItemViewType(position);
        }


        switch (direction) {
            //左滑
            case LEFT:
                if (oniOSHeaderListViewLeftScrollListener != null) {
                    if (viewType == TOP_HEADER) {
                        //這裡是為了確保置頂header的行為所對應的類別符合邏輯
                        int topHeaderPosition = listView.getFirstVisiblePosition();
                        if (topHeaderPosition > 0) {
                            topHeaderPosition = topHeaderPosition - 1;
                        }
                        oniOSHeaderListViewLeftScrollListener.onHeaderLeftScroll(topHeaderPosition);

                    } else if (viewType == HEADER) {
                        oniOSHeaderListViewLeftScrollListener.onHeaderLeftScroll(position);

                    } else {
                        oniOSHeaderListViewLeftScrollListener.onItemLeftScroll(position);
                    }
                }
                break;
            //右滑
            case RIGHT:
                if (oniOSHeaderListViewRightScrollListener != null) {
                    if (viewType == TOP_HEADER) {
                        //這裡是為了確保置頂header的行為所對應的類別符合邏輯
                        int topHeaderPosition = listView.getFirstVisiblePosition();
                        if (topHeaderPosition > 0) {
                            topHeaderPosition = topHeaderPosition - 1;
                        }
                        oniOSHeaderListViewRightScrollListener.onHeaderRightScroll(topHeaderPosition);
                    } else if (viewType == HEADER) {
                        oniOSHeaderListViewRightScrollListener.onHeaderRightScroll(position);
                    } else {
                        oniOSHeaderListViewRightScrollListener.onItemRightScroll(position);
                    }
                }
                break;
        }
    }
    //----------------------

    /**
     * 置頂header click事件
     */
    @Override
    public void onClick(View view) {
        int topHeaderPosition = listView.getFirstVisiblePosition();
        if (topHeaderPosition > 1) {
            topHeaderPosition = topHeaderPosition - 1;
        }
        onScrollItemClickListener.oniOSHeaderListViewHeaderClick(listView, headerView, topHeaderPosition,topHeaderPosition );
    }
    //----------------------

    /**
     * 置頂header longclick事件
     */
    @Override
    public boolean onLongClick(View view) {
        int topHeaderPosition = listView.getFirstVisiblePosition();
        if (topHeaderPosition > 1) {
            topHeaderPosition = topHeaderPosition - 1;
        }
        onScrollItemLongClickListener.oniOSHeaderListViewHeaderLongClick(listView, headerView, topHeaderPosition, topHeaderPosition);
        return true;
    }

    //----------------------

    /**
     * ITEM往右邊滑動的接口
     */
    public void setOniOSHeaderListViewRightScrollListener(OniOSHeaderListViewRightScrollListener oniOSHeaderListViewRightScrollListener) {
        this.oniOSHeaderListViewRightScrollListener = oniOSHeaderListViewRightScrollListener;
        //允許右滑
        listView.setRightScrollable(true);
        scrollHeaderLayout.setRightScrollable(true);
    }

    public interface OniOSHeaderListViewRightScrollListener {
        void onHeaderRightScroll(int position);

        void onItemRightScroll(int position);
    }

    //----------------------

    /**
     * ITEM往左邊滑動的接口
     */
    public void setOniOSHeaderListViewLeftScrollListener(OniOSHeaderListViewLeftScrollListener oniOSHeaderListViewLeftScrollListener) {
        this.oniOSHeaderListViewLeftScrollListener = oniOSHeaderListViewLeftScrollListener;
        //允許左滑
        listView.setLeftScrollable(true);
        scrollHeaderLayout.setLeftScrollable(true);
    }

    public interface OniOSHeaderListViewLeftScrollListener {
        void onHeaderLeftScroll(int position);

        void onItemLeftScroll(int position);
    }


    //----------------------

    /**
     * 調整元件高度寬度 與 Margine
     */
    private LinearLayout.LayoutParams getViewLayoutParams(int viewWidth, int viewHeight) {
        return new LinearLayout.LayoutParams(viewWidth, viewHeight);
    }

    private LinearLayout.LayoutParams getViewLayoutParams(int viewWidth, int viewHeight, float viewWeight) {
        return new LinearLayout.LayoutParams(viewWidth, viewHeight, viewWeight);
    }

    private LinearLayout.LayoutParams getViewLayoutMargine(LinearLayout.LayoutParams layoutParams, int marginL, int marginT, int marginR, int marginB) {
        layoutParams.setMargins(marginL, marginT, marginR, marginB);
        return layoutParams;
    }
}
