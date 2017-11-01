package tw.com.sung.noel.scrollerlistview.implement;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by noel on 2017/10/30.
 */

public interface OnItemClickListener {

    void onHeaderClick(AdapterView<?> adapterView, View view, int i, long l);
    void onItemClick(AdapterView<?> adapterView, View view, int i, long l);
}
