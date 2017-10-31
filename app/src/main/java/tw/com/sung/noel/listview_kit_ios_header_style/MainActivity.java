package tw.com.sung.noel.listview_kit_ios_header_style;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import tw.com.sung.noel.listview_kit_ios_header_style.implement.OnScrollItemClickListener;
import tw.com.sung.noel.listview_kit_ios_header_style.implement.OnScrollItemLongClickListener;
import tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview.iOSHeaderListView;
import tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview.iOSHeaderListViewAdapter;


public class MainActivity extends AppCompatActivity implements iOSHeaderListView.OniOSHeaderListViewVerticalScrollListener, iOSHeaderListView.OniOSHeaderListViewRightScrollListener, iOSHeaderListView.OniOSHeaderListViewLeftScrollListener, OnScrollItemClickListener, OnScrollItemLongClickListener {

    @BindView(R.id.listview)
    iOSHeaderListView listview;

    private TestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        adapter = new TestAdapter(this);
        listview.setiOSHeaderListViewAdapter(adapter);
        adapter.updateData(getData());
        adapter.setGroupType(iOSHeaderListViewAdapter.GROUP_TYPE_REGROUP);

        listview.setOniOSHeaderListViewItemClickListener(this);
        listview.setOniOSHeaderListViewItemLongClickListener(this);
        listview.setOniOSHeaderListViewVerticalScrollListener(this);
        listview.setOniOSHeaderListViewRightScrollListener(this);
        listview.setOniOSHeaderListViewLeftScrollListener(this);
    }

    private ArrayList<TestModel> getData() {
        ArrayList<TestModel> testModles = new ArrayList<>();
        testModles.add(new TestModel("A", "99", "", "", "A"));
        testModles.add(new TestModel("A", "99", "", "", "A"));
        testModles.add(new TestModel("A", "99", "", "", "A"));
        testModles.add(new TestModel("A", "99", "", "", "A"));
        testModles.add(new TestModel("A", "99", "", "", "A"));
        testModles.add(new TestModel("A", "97", "", "", "A"));
        testModles.add(new TestModel("A", "97", "", "", "A"));
        testModles.add(new TestModel("A", "97", "", "", "A"));
        testModles.add(new TestModel("A", "90", "", "", "A"));
        testModles.add(new TestModel("A", "10", "", "", "A"));
        testModles.add(new TestModel("C", "21", "", "", "C"));

        testModles.add(new TestModel("B", "10", "", "", "B"));
        testModles.add(new TestModel("B", "13", "", "", "B"));
        testModles.add(new TestModel("B", "15", "", "", "B"));
        testModles.add(new TestModel("B", "20", "", "", "B"));
        testModles.add(new TestModel("B", "20", "", "", "B"));
        testModles.add(new TestModel("B", "21", "", "", "B"));
        testModles.add(new TestModel("B", "21", "", "", "B"));
        testModles.add(new TestModel("B", "21", "", "", "B"));
        testModles.add(new TestModel("B", "21", "", "", "B"));
        testModles.add(new TestModel("B", "21", "", "", "B"));
        testModles.add(new TestModel("A", "90", "", "", "A"));

        testModles.add(new TestModel("C", "21", "", "", "C"));
        testModles.add(new TestModel("C", "21", "", "", "C"));
        testModles.add(new TestModel("C", "21", "", "", "C"));
        testModles.add(new TestModel("C", "21", "", "", "C"));
        testModles.add(new TestModel("C", "21", "", "", "C"));
        testModles.add(new TestModel("C", "21", "", "", "C"));
        testModles.add(new TestModel("C", "21", "", "", "C"));
        testModles.add(new TestModel("C", "21", "", "", "C"));
        testModles.add(new TestModel("C", "21", "", "", "C"));
        testModles.add(new TestModel("C", "21", "", "", "C"));
        testModles.add(new TestModel("C", "21", "", "", "C"));
        testModles.add(new TestModel("C", "21", "", "", "C"));

        return testModles;
    }


    @Override
    public void oniOSHeaderListViewItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("Click", "oniOSHeaderListViewItemClick");
    }

    @Override
    public void oniOSHeaderListViewHeaderClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("Click", "oniOSHeaderListViewHeaderClick");
    }

    @Override
    public void oniOSHeaderListViewItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("LongClick", "oniOSHeaderListViewItemLongClick");
    }

    @Override
    public void oniOSHeaderListViewHeaderLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("LongClick", "oniOSHeaderListViewHeaderLongClick");
    }

    @Override
    public void oniOSHeaderListViewVerticalScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//        Log.e("scroll", "oniOSHeaderListViewScroll");
    }

    @Override
    public void oniOSHeaderListViewVerticalScrollStateChanged(AbsListView absListView, int i) {
//        Log.e("scroll", "oniOSHeaderListViewScrollStateChanged");
    }

    @Override
    public void onHeaderRightScroll(int position) {
        Log.e("scroll", "onHeaderRightScroll");
    }

    @Override
    public void onItemRightScroll(int position) {
        Log.e("scroll", "onItemRightScroll");
    }

    @Override
    public void onHeaderLeftScroll(int position) {
        Log.e("scroll", "onHeaderLeftScroll");
    }

    @Override
    public void onItemLeftScroll(int position) {
        Log.e("scroll", "onItemLeftScroll");
    }

}
