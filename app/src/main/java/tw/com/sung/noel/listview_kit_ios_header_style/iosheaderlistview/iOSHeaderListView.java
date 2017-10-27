package tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;


import tw.com.sung.noel.listview_kit_ios_header_style.scrolllayout.ScrollHeader;
import tw.com.sung.noel.listview_kit_ios_header_style.scrolllistview.ScrollListView;

import static tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview.iOSHeaderListViewAdapter.HEADER;
import static tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview.iOSHeaderListViewAdapter.ITEM;
import static tw.com.sung.noel.listview_kit_ios_header_style.scrolllistview.ScrollListView.LEFT;
import static tw.com.sung.noel.listview_kit_ios_header_style.scrolllistview.ScrollListView.RIGHT;


/**
 * Created by noel on 2017/10/12.
 */

public class iOSHeaderListView extends LinearLayout implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, ScrollListView.OnItemScrollListener {

    private OniOSHeaderListViewHeaderClickListener oniOSHeaderListViewHeaderClickListener;
    private OniOSHeaderListViewItemClickListener oniOSHeaderListViewItemClickListener;
    private OniOSHeaderListViewScrollListener oniOSHeaderListViewScrollListener;
    private OniOSHeaderListViewItemLongClickListener oniOSHeaderListViewItemLongClickListener;

    private OniOSHeaderListViewRightScrollListener oniOSHeaderListViewRightScrollListener;
    private OniOSHeaderListViewLeftScrollListener oniOSHeaderListViewLeftScrollListener;




    private iOSHeaderListViewAdapter adapter;
    private ScrollListView listView;
    private Context context;
    private View headerView;
    private ScrollHeader scrollHeader;


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
        scrollHeader = new ScrollHeader(context);

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
        listView.setOnItemScrollListener(this);
    }
    //----------------------

    /**
     * item click 對外的接口
     */
    public void setOniOSHeaderListViewItemClickListener(OniOSHeaderListViewItemClickListener oniOSHeaderListViewItemClickListener) {
        this.oniOSHeaderListViewItemClickListener = oniOSHeaderListViewItemClickListener;
        oniOSListViewItemClick();
    }

    //interface
    public interface OniOSHeaderListViewItemClickListener {
        void oniOSHeaderListViewItemClick(AdapterView<?> adapterView, View view, int i, long l);
    }

    /**
     * header click 對外的接口
     */
    public void setOniOSHeaderListViewHeaderClickListener(OniOSHeaderListViewHeaderClickListener oniOSHeaderListViewHeaderClickListener) {
        this.oniOSHeaderListViewHeaderClickListener = oniOSHeaderListViewHeaderClickListener;
        oniOSListViewItemClick();
    }

    //interface
    public interface OniOSHeaderListViewHeaderClickListener {
        void oniOSHeaderListViewHeaderClick(AdapterView<?> adapterView, View view, int i, long l);
    }

    //對iosheaderlistview 中的listview 套上onitemclick監聽
    private void oniOSListViewItemClick() {
        listView.setOnItemClickListener(this);
    }

    //onItemclick
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        iOSHeaderListViewAdapter adapter = (iOSHeaderListViewAdapter) adapterView.getAdapter();
        int viewType = adapter.getItemViewType(i);
        switch (viewType){
            case HEADER:
                if (oniOSHeaderListViewHeaderClickListener != null) {
                    //被套件使用者覆寫的行為
                    oniOSHeaderListViewHeaderClickListener.oniOSHeaderListViewHeaderClick(adapterView, view, i, l);
                }
                break;
            case ITEM:
                if (oniOSHeaderListViewItemClickListener != null) {
                    //被套件使用者覆寫的行為
                    oniOSHeaderListViewItemClickListener.oniOSHeaderListViewItemClick(adapterView, view, i, l);
                }
                break;
        }
    }

    //----------------------

    /**
     * item long click 對外的接口
     */
    public void setOniOSHeaderListViewItemLongClickListener(OniOSHeaderListViewItemLongClickListener oniOSHeaderListViewItemLongClickListener) {
        this.oniOSHeaderListViewItemLongClickListener = oniOSHeaderListViewItemLongClickListener;
        oniOSListViewItemLongClick();
    }

    //interface
    public interface OniOSHeaderListViewItemLongClickListener {
        void oniOSHeaderListViewItemLongClick(AdapterView<?> adapterView, View view, int i, long l);
    }

    //對iosheaderlistview 中的listview 套上onitemLongclick監聽
    private void oniOSListViewItemLongClick() {
        listView.setOnItemLongClickListener(this);
    }

    //onItemLongClick
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        iOSHeaderListViewAdapter adapter = (iOSHeaderListViewAdapter) adapterView.getAdapter();
        if (adapter.getItemViewType(i) != HEADER) {
            if (oniOSHeaderListViewItemLongClickListener != null) {
                //被套件使用者覆寫的行為
                oniOSHeaderListViewItemLongClickListener.oniOSHeaderListViewItemLongClick(adapterView, view, i, l);
            }
            return true;
        }
        return false;
    }

    //----------------------

    /**
     * scroll 接口
     */
    public void setOniOSHeaderListViewScrollListener(OniOSHeaderListViewScrollListener oniOSHeaderListViewScrollListener) {
        this.oniOSHeaderListViewScrollListener = oniOSHeaderListViewScrollListener;
        oniOSHeaderListViewScroll();
    }

    //interface
    public interface OniOSHeaderListViewScrollListener {
        void oniOSHeaderListViewScrollStateChanged(AbsListView absListView, int scrollState);

        void oniOSHeaderListViewScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    }

    //對iosheaderlistview 中的listview 套上OnScroll監聽
    private void oniOSHeaderListViewScroll() {
        listView.setOnScrollListener(this);
    }

    //滾動行為改變時
    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {

        if (oniOSHeaderListViewScrollListener != null) {
            //被套件使用者覆寫的行為
            oniOSHeaderListViewScrollListener.oniOSHeaderListViewScrollStateChanged(absListView, scrollState);
        }
    }

    //滾動時
    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        //沒資料則直接return
        if (visibleItemCount == 0)
            return;

        //目前最上面項目的種類
        @iOSHeaderListViewAdapter.dataType int nowItemType = adapter.getItemViewType(firstVisibleItem);
        //取得前一個項目的種類
        @iOSHeaderListViewAdapter.dataType int previousItemType = adapter.getItemViewType(firstVisibleItem - 1);


        //第0 或者 前一項type等於目前type（用於由上往下滾動） 或者 前一項type = HEADER（用於由下往上滾動）
        if (firstVisibleItem == 0 || nowItemType == previousItemType || previousItemType == iOSHeaderListViewAdapter.HEADER) {
            updateHeaderView(firstVisibleItem, absListView);
        }

        if (oniOSHeaderListViewScrollListener != null) {
            //被套件使用者覆寫的行為
            oniOSHeaderListViewScrollListener.oniOSHeaderListViewScroll(absListView, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    //----------------------

    /**
     * 更新置頂headerview
     */
    private void updateHeaderView(int firstVisibleItem, AbsListView absListView) {
        if (headerView != null) {
            scrollHeader.removeView(headerView);
        }
        if (scrollHeader != null) {
            removeView(scrollHeader);
        }

        headerView = adapter.getCustomHeaderView(firstVisibleItem, headerView, absListView);
        headerView.setLayoutParams(getViewLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(scrollHeader, 0);
        scrollHeader.addView(headerView);
    }

    //----------------------

    /**
     * 當滑動清單中的Item或header時
     */
    @Override
    public void onItemScroll(@ScrollListView.scrollDirection int direction, int position) {

        int viewType = adapter.getItemViewType(position);

        switch (direction) {
            case LEFT:
                if (oniOSHeaderListViewLeftScrollListener != null) {
                    if (viewType == HEADER) {
                        oniOSHeaderListViewLeftScrollListener.onHeaderLeftScroll(position);
                    } else {
                        oniOSHeaderListViewLeftScrollListener.onItemLeftScroll(position);
                    }
                }
                break;
            case RIGHT:
                if (oniOSHeaderListViewRightScrollListener != null) {
                    if (viewType == HEADER) {
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
     * ITEM往右邊滑動的接口
     */
    public void setOniOSHeaderListViewRightScrollListener(OniOSHeaderListViewRightScrollListener oniOSHeaderListViewRightScrollListener) {
        this.oniOSHeaderListViewRightScrollListener = oniOSHeaderListViewRightScrollListener;
        //允許右滑
        listView.setRightScrollable(true);
        scrollHeader.setRightScrollable(true);
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
        scrollHeader.setLeftScrollable(true);
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
