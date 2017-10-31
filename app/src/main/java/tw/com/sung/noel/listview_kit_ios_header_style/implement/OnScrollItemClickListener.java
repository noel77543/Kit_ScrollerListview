package tw.com.sung.noel.listview_kit_ios_header_style.implement;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by noel on 2017/10/30.
 */

public interface OnScrollItemClickListener {

    void oniOSHeaderListViewHeaderClick(AdapterView<?> adapterView, View view, int i, long l);
    void oniOSHeaderListViewItemClick(AdapterView<?> adapterView, View view, int i, long l);
}
