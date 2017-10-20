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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview.iOSHeaderListView;
import tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview.iOSHeaderListViewAdapter;

import static tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview.iOSHeaderListViewAdapter.HEADER;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    @BindView(R.id.listview)
    iOSHeaderListView listview;
    @BindView(R.id.layout_view)
    LinearLayout layoutView;

    private LayoutInflater inflater;
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
        listview.setAdapter(adapter);
        adapter.updateData(getData());
        adapter.setGroupType(iOSHeaderListViewAdapter.GROUP_TYPE_REGROUP);
//        listview.setOnItemClickListener(this);
//        listview.setOnScrollListener(this);
    }

    private ArrayList<TestModel> getData() {
        testModles = new ArrayList<>();
        testModles.add(new TestModel("A", "99", "", "", "A"));
        testModles.add(new TestModel("B", "99", "", "", "A"));
        testModles.add(new TestModel("C", "99", "", "", "A"));
        testModles.add(new TestModel("D", "99", "", "", "A"));
        testModles.add(new TestModel("E", "99", "", "", "A"));
        testModles.add(new TestModel("F", "97", "", "", "A"));
        testModles.add(new TestModel("G", "97", "", "", "A"));
        testModles.add(new TestModel("H", "97", "", "", "A"));
        testModles.add(new TestModel("I", "90", "", "", "A"));
        testModles.add(new TestModel("J", "90", "", "", "A"));
        testModles.add(new TestModel("K", "10", "", "", "A"));
        testModles.add(new TestModel("ZZ", "21", "", "", "C"));

        testModles.add(new TestModel("L", "10", "", "", "B"));
        testModles.add(new TestModel("M", "13", "", "", "B"));
        testModles.add(new TestModel("N", "15", "", "", "B"));
        testModles.add(new TestModel("O", "20", "", "", "B"));
        testModles.add(new TestModel("P", "20", "", "", "B"));
        testModles.add(new TestModel("Q", "21", "", "", "B"));
        testModles.add(new TestModel("R", "21", "", "", "B"));

        testModles.add(new TestModel("S", "21", "", "", "C"));
        testModles.add(new TestModel("T", "21", "", "", "C"));
        testModles.add(new TestModel("U", "21", "", "", "C"));
        testModles.add(new TestModel("V", "21", "", "", "C"));
        testModles.add(new TestModel("W", "21", "", "", "C"));
        testModles.add(new TestModel("X", "21", "", "", "C"));
        testModles.add(new TestModel("Y", "21", "", "", "C"));
        testModles.add(new TestModel("Z", "21", "", "", "C"));

        return testModles;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("第" + i, testModles.get(i).getName());
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        //從第二個開始 因為第一個一定是title了
        if (firstVisibleItem > 0) {
            //幕前最上面項目的種類
            int nowItemType = absListView.getAdapter().getItemViewType(firstVisibleItem);
            //取得前一個項目的種類
            int previousItemType = absListView.getAdapter().getItemViewType(firstVisibleItem - 1);

            //前一項跟目前置頂的項目種類不同 則...
            if (nowItemType != previousItemType) {

            } else {

            }
        }
    }
}
