package tw.com.sung.noel.listview_kit_ios_header_style.implement;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by noel on 2017/10/30.
 */

public interface OnScrollItemLongClickListener {
    void oniOSHeaderListViewHeaderLongClick(AdapterView<?> adapterView, View view, int i, long l);

    void oniOSHeaderListViewItemLongClick(AdapterView<?> adapterView, View view, int i, long l);

}
