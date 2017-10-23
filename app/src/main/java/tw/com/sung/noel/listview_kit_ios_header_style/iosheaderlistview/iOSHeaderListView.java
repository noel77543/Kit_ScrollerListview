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

    private iOSHeaderListViewAdapter adapter;
    private ListView listView;
    private Context context;
    private TextView textView;
    private View view;
    private String TAG = "iOSHeaderListView";
    private int firstIndex = 0  ;

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
        initView();
    }
    //----------------------

    /**
     * 加入textview 與 listview
     */
    private void initView() {
        setOrientation(VERTICAL);
        listView = new ListView(context);

        initListView();
        addView(listView);
        initHeaderView();
        addView(textView, 0);

    }

    //----------------------

    /**
     * 初始化textview
     */
    private void initHeaderView() {
//        view = new View(context);
//        view.setLayoutParams(getViewLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        addView(view);


        textView = new TextView(context);
        textView.setLayoutParams(getViewLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setTextSize(20);
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundColor(Color.BLACK);
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
        switch (scrollState) {

            // 滚动之前,手还在屏幕上 记录滚动前的下标
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                // view.getLastVisiblePosition()得到当前屏幕可见的第一个item在整个listview中的下标
                firstIndex = absListView.getLastVisiblePosition();
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                Log.e("123","123");
                break;
            // 滚动停止
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                // 记录滚动停止后 记录当前item的位置
                int scrolled = absListView.getLastVisiblePosition();
                // 滚动后下标大于滚动前 向下滚动了
                if (scrolled > firstIndex) {
                    // scroll = false;
                    // UIHelper.ToastMessage(VideoMain.this,"菜单收起");
                    Log.e("方向","往下滾動");

                }
                // 向上滚动了
                else if(scrolled < firstIndex){
                    // UIHelper.ToastMessage(VideoMain.this,"菜单弹出");
                    // scroll = true;
                    Log.e("方向","往上滾動");

                }
                break;
        }



        //被套件使用者覆寫的行為
        oniOSHeaderListViewScrollListener.oniOSHeaderListViewScrollStateChanged(absListView, scrollState);
    }

    //滾動時
    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        //沒資料則直接return
        if (visibleItemCount == 0)
            return;

        //第0個要直接設置title
//            view = adapter.getCustomHeaderView(firstVisibleItem,null,absListView);
//            firstView = absListView.getChildAt(0);
//            secondView = absListView.getChildAt(1);
        //目前最上面項目的種類
        @iOSHeaderListViewAdapter.dataType int nowItemType = absListView.getAdapter().getItemViewType(firstVisibleItem);
        //取得前一個項目的種類
        @iOSHeaderListViewAdapter.dataType int previousItemType = absListView.getAdapter().getItemViewType(firstVisibleItem - 1);

        //一開始 或 前一項跟目前置頂的項目種類不同 則...
        if (firstVisibleItem == 0 || previousItemType != nowItemType) {
            textView.setText(adapter.getHeaderAccordingTo(firstVisibleItem));
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

}
