package tw.com.sung.noel.scrollerlistview.implement;

import tw.com.sung.noel.scrollerlistview.bottomlistview.BottomListView;

/**
 * Created by noel on 2017/10/30.
 */

public interface OnHorizontalScrollListener {
    void onHorozontalScroll(@BottomListView.scrollDirection int direction, int position);
}
