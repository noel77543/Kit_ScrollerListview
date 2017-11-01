package tw.com.sung.noel.scrollerlistview.implement;

import android.widget.AbsListView;

/**
 * Created by noel on 2017/11/1.
 */

public interface OnVerticalScrollListener {
    void onVerticalScrollStateChanged(AbsListView absListView, int scrollState);

    void onVerticalScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount);
}
