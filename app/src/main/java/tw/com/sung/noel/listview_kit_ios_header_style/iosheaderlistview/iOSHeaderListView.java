package tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;


/**
 * Created by noel on 2017/10/12.
 */

public class iOSHeaderListView extends LinearLayout implements AbsListView.OnScrollListener {

    private View headerView;
    private ListView listView;
    private Context context;

    private String TAG = "iOSHeaderListView";
    private View firstView;
    private View secondView;

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
        listView.setOnScrollListener(this);
    }
    //----------------------

    /**
     * 加入textview 與 listview
     */
    private void initView() {
        setOrientation(VERTICAL);
        headerView = new View(context);
        listView = new ListView(context);

        initListView();
        addView(listView);
//        initHeaderView();
    }

    //----------------------

    /**
     * 初始化textview
     */
    private void initHeaderView(iOSHeaderListViewAdapter adapter) {

        headerView = adapter.getView(0, null, listView);
        addView(headerView);
    }
    //----------------------

    /**
     * 初始化listview
     */
    private void initListView() {
        listView.setLayoutParams(getViewLayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.MATCH_PARENT));
    }

    //----------------------

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //沒資料則直接return
        if (visibleItemCount == 0)
            return;

        firstView = absListView.getChildAt(0);
        secondView = absListView.getChildAt(1);


        //從第2個開始
        if (firstVisibleItem > 0) {
            //幕前最上面項目的種類
            @iOSHeaderListViewAdapter.dataType int nowItemType = absListView.getAdapter().getItemViewType(firstVisibleItem);
            //取得前一個項目的種類
            @iOSHeaderListViewAdapter.dataType int previousItemType = absListView.getAdapter().getItemViewType(firstVisibleItem - 1);


            //前一項跟目前置頂的項目種類不同 則...
            if (previousItemType != nowItemType) {

            }
        }
    }

    //----------------------

    private LinearLayout.LayoutParams getViewLayoutParams(int viewWidth, int viewHeight) {
        return new LinearLayout.LayoutParams(viewWidth, viewHeight);
    }

    private LinearLayout.LayoutParams getViewLayoutMargine(LinearLayout.LayoutParams layoutParams, int marginL, int marginT, int marginR, int marginB) {
        layoutParams.setMargins(marginL, marginT, marginR, marginB);
        return layoutParams;
    }

    //----------------------

    /**
     * 供使用者設置adapter
     */
    public void setAdapter(iOSHeaderListViewAdapter adapter) {
        listView.setAdapter(adapter);
//        initHeaderView(adapter);
    }
}
