package tw.com.sung.noel.scrollerlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import java.util.ArrayList;

import tw.com.sung.noel.scrollerlistview.implement.OnItemClickListener;
import tw.com.sung.noel.scrollerlistview.implement.OnItemLongClickListener;
import tw.com.sung.noel.scrollerlistview.implement.OnLeftScrollListener;
import tw.com.sung.noel.scrollerlistview.implement.OnRightScrollListener;
import tw.com.sung.noel.scrollerlistview.implement.OnVerticalScrollListener;
import tw.com.sung.noel.scrollerlistview.scrollerlistview.ScrollerListView;
import tw.com.sung.noel.scrollerlistview.scrollerlistview.ScrollerListViewAdapter;

public class MainActivity extends AppCompatActivity implements OnRightScrollListener, OnLeftScrollListener, OnItemLongClickListener, OnItemClickListener, OnVerticalScrollListener {

    private ScrollerListView scrollerListView;
    private TestAdapter adapter;
    private ArrayList<TestModel> testModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollerListView = (ScrollerListView)findViewById(R.id.listview);
        adapter = new TestAdapter(this);


        testModels = new ArrayList<>();
        testModels.add(new TestModel("A","A"));
        testModels.add(new TestModel("A","A"));
        testModels.add(new TestModel("A","A"));
        testModels.add(new TestModel("A","A"));
        testModels.add(new TestModel("A","A"));
        testModels.add(new TestModel("A","A"));
        testModels.add(new TestModel("A","A"));
        testModels.add(new TestModel("A","A"));
        testModels.add(new TestModel("A","A"));
        testModels.add(new TestModel("A","A"));
        testModels.add(new TestModel("A","A"));

        testModels.add(new TestModel("B","B"));
        testModels.add(new TestModel("B","B"));
        testModels.add(new TestModel("B","B"));
        testModels.add(new TestModel("B","B"));
        testModels.add(new TestModel("B","B"));
        testModels.add(new TestModel("A","A"));
        testModels.add(new TestModel("A","A"));
        testModels.add(new TestModel("B","B"));
        testModels.add(new TestModel("B","B"));
        testModels.add(new TestModel("B","B"));
        testModels.add(new TestModel("C","C"));

        testModels.add(new TestModel("C","C"));
        testModels.add(new TestModel("C","C"));
        testModels.add(new TestModel("C","C"));
        testModels.add(new TestModel("C","C"));
        testModels.add(new TestModel("C","C"));
        testModels.add(new TestModel("C","C"));
        testModels.add(new TestModel("C","C"));
        testModels.add(new TestModel("C","C"));
        testModels.add(new TestModel("A","A"));
        testModels.add(new TestModel("A","A"));
        adapter.updateData(testModels);
        scrollerListView.setAdapter(adapter);
        adapter.setGroupType(ScrollerListViewAdapter.GROUP_TYPE_REGROUP);
        scrollerListView.setOnItemClickListener(this);
        scrollerListView.setOnItemLongClickListener(this);
        scrollerListView.setOnVerticalScrollListener(this);
        scrollerListView.setOnRightScrollListener(this);
        scrollerListView.setOnLeftScrollListener(this);



    }

    @Override
    public void onHeaderRightScroll(int position) {

    }

    @Override
    public void onItemRightScroll(int position) {

    }

    @Override
    public void onHeaderLeftScroll(int position) {

    }

    @Override
    public void onItemLeftScroll(int position) {

    }

    @Override
    public void onVerticalScrollStateChanged(AbsListView absListView, int scrollState) {

    }

    @Override
    public void onVerticalScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onHeaderLongClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onHeaderClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
