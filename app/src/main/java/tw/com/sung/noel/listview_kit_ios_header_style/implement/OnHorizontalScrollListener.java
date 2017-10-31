package tw.com.sung.noel.listview_kit_ios_header_style.implement;

import tw.com.sung.noel.listview_kit_ios_header_style.scrolllistview.ScrollListView;

/**
 * Created by noel on 2017/10/30.
 */

public interface OnHorizontalScrollListener {
    void onHorozontalScroll(@ScrollListView.scrollDirection int direction, int position);
}
