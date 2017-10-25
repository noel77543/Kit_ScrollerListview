package tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import tw.com.sung.noel.listview_kit_ios_header_style.scrollLayout.ScrollLayout;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;
import static tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview.iOSHeaderListViewAdapter.HEADER;
import static tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview.iOSHeaderListViewAdapter.VIEW_TYPE_DEFAULT;
import static tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview.iOSHeaderListViewAdapter.VIEW_TYPE_SCROLL;


/**
 * Created by noel on 2017/10/12.
 */

public class iOSHeaderListView extends LinearLayout implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, ScrollLayout.OnDeleteClickListener, iOSHeaderListViewAdapter.OniOSHeaderListViewItemTouchListener {

    private OniOSHeaderListViewHeaderClickListener oniOSHeaderListViewHeaderClickListener;
    private OniOSHeaderListViewItemClickListener oniOSHeaderListViewItemClickListener;
    private OniOSHeaderListViewScrollListener oniOSHeaderListViewScrollListener;
    private OniOSHeaderListViewItemLongClickListener oniOSHeaderListViewItemLongClickListener;

    private iOSHeaderListViewAdapter adapter;
    private ListView listView;
    private Context context;
    private View headerView;
    private String TAG = "iOSHeaderListView";

    private ScrollLayout scrollLayout;
    //touchlistener用
    private float downX = 0;
    private float downY = 0;

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
        scrollLayout = new ScrollLayout(context);
        scrollLayout.setOnDeleteClickListener(this);

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
        adapter.setOniOSHeaderListViewItemTouchListener(this);
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

    //----------------------
    /**
     * 各項目的onTouChListener (Header除外)
     */
    @Override
    public boolean oniOSHeaderListViewItemTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            //手指接觸 view
            case ACTION_DOWN:
                Log.e("event", "ACTION_DOWN");
                downX = motionEvent.getX();//定義此點為基準點
                downY = motionEvent.getY();
                break;
            //手指在移動中
            case ACTION_MOVE:
                Log.e("event", "ACTION_MOVE");
                float moveX = motionEvent.getX();
                float moveY = motionEvent.getY();
                if (downX <= moveX) {
                    Log.e("ＭＯＶＥ", "向右邊");

                }else if(downX >= moveX){
                    Log.e("ＭＯＶＥ", "向左邊");
                }
                break;
            //手指離開螢幕
            case ACTION_UP:
                Log.e("event", "ACTION_UP");
                break;
            //手指在移動中超過該view邊界,但仍然在螢幕上,此時其他ACTION不再觸發
            case ACTION_CANCEL:
                Log.e("event", "ACTION_CANCEL");
                break;
        }
        return true;
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
        if (adapter.getItemViewType(i) != HEADER) {

            if (oniOSHeaderListViewItemClickListener != null) {
                //被套件使用者覆寫的行為
                oniOSHeaderListViewItemClickListener.oniOSHeaderListViewItemClick(adapterView, view, i, l);
            }
        } else {
            listView.setSelection(i + 1);

            if (oniOSHeaderListViewHeaderClickListener != null) {
                //被套件使用者覆寫的行為
                oniOSHeaderListViewHeaderClickListener.oniOSHeaderListViewHeaderClick(adapterView, view, i, l);
            }
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
        @iOSHeaderListViewAdapter.viewType int viewType = adapter.getViewType();
        switch (viewType) {
            case VIEW_TYPE_DEFAULT:
                setViewDefault(firstVisibleItem, absListView);
                break;
            case VIEW_TYPE_SCROLL:
                setViewScrolled(firstVisibleItem, absListView);
                break;
        }
    }
    //----------------------

    /**
     * 橫向滾動置頂header
     */
    private void setViewScrolled(int firstVisibleItem, AbsListView absListView) {
        if (headerView != null) {
            scrollLayout.removeView(headerView);
        }
        if (scrollLayout != null) {
            removeView(scrollLayout);
        }

        headerView = adapter.getCustomHeaderView(firstVisibleItem, headerView, absListView);
        headerView.setLayoutParams(getViewLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(scrollLayout, 0);
        scrollLayout.setContentView(headerView);
        scrollLayout.setDeleteType(adapter.getHeaderAccordingTo(firstVisibleItem));
    }
    //----------------------

    /**
     * 一般置頂header
     */
    private void setViewDefault(int firstVisibleItem, AbsListView absListView) {
        if (headerView != null) {
            removeView(headerView);
        }

        headerView = adapter.getCustomHeaderView(firstVisibleItem, headerView, absListView);
        addView(headerView, 0);
    }
    //----------------------

    /**
     * 當按下delete
     */
    @Override
    public void onDeleteClick(String deleteType) {
        removeAllType( deleteType);
        if(adapter.getCount() == 0){
            scrollLayout.setVisibility(INVISIBLE);
        }
    }

    //----------------------

    /**
     * 移除所有與該項目相同的類別
     */
    private void removeAllType(String deleteType) {
        ArrayList data = adapter.getData();
        ArrayList newData = new ArrayList();
        for (int j = 0; j < data.size(); j++) {
            if (!adapter.getHeaderAccordingTo(j).equals(deleteType)) {
                newData.add(data.get(j));
            }
        }
        adapter.setData(newData);
        adapter.notifyDataSetChanged();
    }
}
