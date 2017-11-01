package tw.com.sung.noel.scrollerlistview.implement;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by noel on 2017/10/30.
 */

public interface OnItemLongClickListener {
    void onHeaderLongClick(AdapterView<?> adapterView, View view, int i, long l);

    void onItemLongClick(AdapterView<?> adapterView, View view, int i, long l);

}
