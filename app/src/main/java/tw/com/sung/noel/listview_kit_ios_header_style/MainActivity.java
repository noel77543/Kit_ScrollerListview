package tw.com.sung.noel.listview_kit_ios_header_style;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview.iOSHeaderListView;
import tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview.iOSHeaderListViewAdapter;

import static tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview.iOSHeaderListViewAdapter.HEADER;

public class MainActivity extends AppCompatActivity implements iOSHeaderListView.OniOSHeaderListViewItemClickListener, iOSHeaderListView.OniOSHeaderListViewScrollListener, iOSHeaderListView.OniOSHeaderListViewItemLongClickListener, iOSHeaderListView.OniOSHeaderListViewHeaderClickListener, iOSHeaderListView.OniOSHeaderListViewRightScrollListener, iOSHeaderListView.OniOSHeaderListViewLeftScrollListener {

    @BindView(R.id.listview)
    iOSHeaderListView listview;

    private TestAdapter adapter;
    private ArrayList<TestModel> testModles;

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
        listview.setOniOSHeaderListViewScrollListener(this);
        listview.setOniOSHeaderListViewItemLongClickListener(this);
        listview.setOniOSHeaderListViewHeaderClickListener(this);
        listview.setOniOSHeaderListViewRightScrollListener(this);
//        listview.setOniOSHeaderListViewLeftScrollListener(this);

    }

    private ArrayList<TestModel> getData() {
        testModles = new ArrayList<>();
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
        TestModel testModel = (TestModel) adapterView.getAdapter().getItem(i);
        Log.e("第" + i, testModel.getAge());
    }

    @Override
    public void oniOSHeaderListViewScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void oniOSHeaderListViewScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void oniOSHeaderListViewItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void oniOSHeaderListViewHeaderClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onHeaderRightScroll(int position) {
        Log.e("scroll","onHeaderRightScroll");
    }

    @Override
    public void onItemRightScroll(int position) {
        Log.e("scroll","onItemRightScroll");
    }

    @Override
    public void onHeaderLeftScroll(int position) {

    }

    @Override
    public void onItemLeftScroll(int position) {

    }
}
