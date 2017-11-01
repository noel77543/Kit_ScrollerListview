package tw.com.sung.noel.scrollerlistview.scrollerlistview;


import android.support.annotation.IntDef;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import static tw.com.sung.noel.scrollerlistview.scrollerlistview.ScrollerListView.HEADER;
import static tw.com.sung.noel.scrollerlistview.scrollerlistview.ScrollerListView.ITEM;


/**
 * Created by noel on 2017/10/12.
 */

public abstract class ScrollerListViewAdapter extends BaseAdapter {


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
        //第0筆的類別不需要Header 因為已經有最上方置頂的HEADER存在
        if (position > 0 && !getHeaderAccordingTo(position - 1).equals(getHeaderAccordingTo(position))) {
            return HEADER;
        }
        return ITEM;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ArrayList data = setData();
        //若要依照類別重組
        if (getGroupType() == GROUP_TYPE_REGROUP) {
            reSetData(getReGroupData(data));
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

    //---------------------- todo private method

    /**
     * 取得所有標題種類
     */
    private ArrayList<String> getAllHeaderTypes() {
        ArrayList data = setData();
        ArrayList<String> allTypes = new ArrayList<>();
        String firstType = getHeaderAccordingTo(0);//直接先放入第一個項目所屬tag
        allTypes.add(firstType);

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.size(); j++) {
                //firstType與各項目逐次比較若不相等 且 allTypes中沒有該項目 則加入allTypes
                if (!firstType.equals(getHeaderAccordingTo(j)) && !allTypes.contains(getHeaderAccordingTo(j))) {
                    allTypes.add(getHeaderAccordingTo(j));
                    firstType = getHeaderAccordingTo(j);
                }
            }
        }
        return allTypes;
    }

    //---------------------- todo private method

    /**
     * 進行自動分類重組
     */
    private ArrayList getReGroupData(ArrayList data) {
        ArrayList reGroupData = new ArrayList();
        ArrayList<String> allTypes = getAllHeaderTypes();

        for (int i = 0; i < allTypes.size(); i++) {
            for (int j = 0; j < data.size(); j++) {
                if (allTypes.get(i).equals(getHeaderAccordingTo(j))) {
                    reGroupData.add(data.get(j));
                }
            }
        }
        return reGroupData;
    }

    //---------------------- todo public method

    /**
     * 讓使用者決定 要不要 依照類別重組,預設為不協助重組 ->GROUP_TYPE_DEFAULT
     */
    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(@groupType int groupType) {
        this.groupType = groupType;
    }


    //---------------------- todo public method for abstract

    /**
     * 設置headerView
     */

    public abstract View setCustomHeaderView(int position, View convertView, ViewGroup viewGroup);

    /**
     * 取得headerView
     */
    public View getCustomHeaderView(int position, View convertView, ViewGroup viewGroup) {
        return setCustomHeaderView(position, convertView, viewGroup);
    }

    //---------------------- todo public method for abstract

    /**
     * 設置一般itemView
     */
    public abstract View setCustomItemView(int position, View convertView, ViewGroup viewGroup);

    /**
     * 取得一般itemView
     */
    public View getCustomItemView(int position, View convertView, ViewGroup viewGroup) {
        return setCustomItemView(position, convertView, viewGroup);
    }

    //---------------------- todo public method for abstract

    /**
     * 設置資料
     */
    public abstract ArrayList setData();

    /**
     * 重新設置資料
     */
    public void reSetData(ArrayList newData) {
        setData().clear();
        setData().addAll(newData);
    }

    //---------------------- todo public method for abstract

    /**
     * 設置標題依據
     */
    public abstract String setHeaderAccordingTo(int position);

    /**
     * 取得標題依據
     */
    public String getHeaderAccordingTo(int position) {
        return setHeaderAccordingTo(position);
    }
}