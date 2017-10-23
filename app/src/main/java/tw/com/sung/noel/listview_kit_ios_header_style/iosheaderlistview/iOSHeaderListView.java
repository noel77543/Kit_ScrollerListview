package tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import tw.com.sung.noel.listview_kit_ios_header_style.MainActivity;


/**
 * Created by noel on 2017/10/12.
 */

public class iOSHeaderListView extends LinearLayout implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private OniOSHeaderListViewItemClickListener oniOSHeaderListViewItemClickListener;
    private OniOSHeaderListViewScrollListener oniOSHeaderListViewScrollListener;
    private OniOSHeaderListViewItemLongClickListener oniOSHeaderListViewItemLongClickListener;

    private ViewGroup.MarginLayoutParams marginLayoutParams;
    private iOSHeaderListViewAdapter adapter;
    private ListView listView;
    private Context context;
    private View headerView;
    private String TAG = "iOSHeaderListView";


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
        listView = new ListView(context);
        initListView();
        addView(listView);

    }

    //----------------------

    /**
     * 初始化listview
     */
    private void initListView() {
        listView.setLayoutParams(getViewLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
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

    //----------------------

    /**
     * 供使用者設置adapter
     */
    public void setiOSHeaderListViewAdapter(iOSHeaderListViewAdapter adapter) {
        this.adapter = adapter;
        listView.setAdapter(adapter);
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

    //對iosheaderlistview 中的listview 套上onitemclick監聽
    private void oniOSListViewItemClick() {
        listView.setOnItemClickListener(this);
    }

    //onItemclick
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        iOSHeaderListViewAdapter adapter = (iOSHeaderListViewAdapter) adapterView.getAdapter();
        if (adapter.getItemViewType(i) != iOSHeaderListViewAdapter.HEADER) {
            //被套件使用者覆寫的行為
            oniOSHeaderListViewItemClickListener.oniOSHeaderListViewItemClick(adapterView, view, i, l);
        }
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

        //被套件使用者覆寫的行為
        oniOSHeaderListViewScrollListener.oniOSHeaderListViewScrollStateChanged(absListView, scrollState);
    }

    //滾動時
    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        //沒資料則直接return
        if (visibleItemCount == 0)
            return;

        //目前最上面項目的種類
        @iOSHeaderListViewAdapter.dataType int nowItemType = absListView.getAdapter().getItemViewType(firstVisibleItem);
        //取得前一個項目的種類
        @iOSHeaderListViewAdapter.dataType int previousItemType = absListView.getAdapter().getItemViewType(firstVisibleItem - 1);

        //第0 或者 前一項type 不等於 目前type 或者 目前type = ITEM
        if (firstVisibleItem == 0 || previousItemType != nowItemType || nowItemType == iOSHeaderListViewAdapter.ITEM) {
            updateHeaderView(firstVisibleItem, absListView);
        }

        //被套件使用者覆寫的行為
        oniOSHeaderListViewScrollListener.oniOSHeaderListViewScroll(absListView, firstVisibleItem, visibleItemCount, totalItemCount);
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

        //被套件使用者覆寫的行為
        oniOSHeaderListViewItemLongClickListener.oniOSHeaderListViewItemLongClick(adapterView, view, i, l);
        return true;
    }
    //----------------------

    /**
     * 更新headerview 並且加入在上方
     */
    private void updateHeaderView(int firstVisibleItem, AbsListView absListView) {

//        View  firstView = absListView.getChildAt(firstVisibleItem-1);
//        View  secondView = absListView.getChildAt(firstVisibleItem);
//        marginLayoutParams = new ViewGroup.MarginLayoutParams(headerView.getLayoutParams());

        if (headerView != null) {
            removeView(headerView);
        }
        headerView = adapter.getCustomHeaderView(firstVisibleItem, headerView, absListView);
        addView(headerView, 0);

//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(headerView.getLayoutParams());
//        layoutParams.setMargins(layoutParams.leftMargin, 0, layoutParams.rightMargin, headerView.getHeight()+10);
//        headerView.setLayoutParams(layoutParams);
    }
}
