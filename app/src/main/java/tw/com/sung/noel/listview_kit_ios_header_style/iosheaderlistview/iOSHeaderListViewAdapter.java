package tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview;


import android.support.annotation.IntDef;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;


/**
 * Created by noel on 2017/10/12.
 */

public abstract class iOSHeaderListViewAdapter extends BaseAdapter {

    //header 或者 item
    public static final int HEADER = 0;
    public static final int ITEM = 1;

    @IntDef({HEADER, ITEM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface dataType {

    }

    //是否 所有資料需要依照類別分類
    public static final int GROUP_TYPE_DEFAULT = 1111;
    public static final int GROUP_TYPE_REGROUP = 2222;

    @IntDef({GROUP_TYPE_DEFAULT, GROUP_TYPE_REGROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface groupType {

    }

    //預設為不協助分類
    private
    @groupType
    int groupType = GROUP_TYPE_DEFAULT;

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        //第0項所屬header不需要設置＠＠？
        if (position == 0 || (position > 0 && !getHeaderAccordingTo(position - 1).equals(getHeaderAccordingTo(position)))) {
            return HEADER;
        }
        return ITEM;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ArrayList data = getData();
        //若要重組
        if (groupType == GROUP_TYPE_REGROUP) {
            ArrayList reGroupData = new ArrayList();
            ArrayList<String> allTypes = getAllHeaderTypes();

            for (int i = 0; i < allTypes.size(); i++) {
                for (int j = 0; j < data.size(); j++) {
                    if (allTypes.get(i).equals(getHeaderAccordingTo(j))) {
                        reGroupData.add(data.get(j));
                    }
                }
            }
            setData(reGroupData);
        }

        if (getItemViewType(position) == HEADER) {
            //遞補變成header的那一項
            if (data.get(position) != data.get(position + 1)) {
                data.add(position + 1, data.get(position));
                notifyDataSetChanged();
            }
            return getCustomHeaderView(position, view, viewGroup);
        }
        return getCustomItemView(position, view, viewGroup);
    }

    //----------------------

    /**
     * 設置headerView
     */

    public abstract View setCustomHeaderView(int position, View convertView, ViewGroup viewGroup);

    /**
     * 取得headerView
     */
    private View getCustomHeaderView(int position, View convertView, ViewGroup viewGroup) {
        return setCustomHeaderView(position, convertView, viewGroup);
    }
    //----------------------

    /**
     * 設置一般itemView
     */
    public abstract View setCustomItemView(int position, View convertView, ViewGroup viewGroup);

    /**
     * 取得一般itemView
     */
    private View getCustomItemView(int position, View convertView, ViewGroup viewGroup) {
        return setCustomItemView(position, convertView, viewGroup);
    }
    //----------------------

    /**
     * 設置資料
     */
    public abstract ArrayList getData();

    public void setData(ArrayList newData) {
        getData().clear();
        getData().addAll(newData);
    }

    //----------------------

    /**
     * 設置標題依據
     */
    public abstract String setHeaderAccordingTo(int position);

    /**
     * 取得標題依據
     */
    private String getHeaderAccordingTo(int position) {
        return setHeaderAccordingTo(position);
    }

    /**
     * 取得所有標題種類
     */
    private ArrayList<String> getAllHeaderTypes() {
        ArrayList data = getData();
        ArrayList<String> allTypes = new ArrayList<>();
        String firstType = getHeaderAccordingTo(0);
        allTypes.add(firstType);

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.size(); j++) {
                if (!firstType.equals(getHeaderAccordingTo(j)) && !allTypes.contains(getHeaderAccordingTo(j))) {
                    allTypes.add(getHeaderAccordingTo(j));
                    firstType = getHeaderAccordingTo(j);
                }
            }
        }

        return allTypes;
    }

    //----------------------

    /**
     * 讓使用者決定 要不要 依照類別重組,預設為不協助重組 ->GROUP_TYPE_DEFAULT
     */
    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(@groupType int groupType) {
        this.groupType = groupType;
    }
}