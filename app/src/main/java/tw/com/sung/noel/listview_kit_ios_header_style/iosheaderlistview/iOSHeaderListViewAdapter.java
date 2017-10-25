package tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview;


import android.support.annotation.IntDef;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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


    //是否 畫面要啟用ScrollLayout
    public static final int VIEW_TYPE_DEFAULT = 3333;
    public static final int VIEW_TYPE_SCROLL = 4444;

    @IntDef({VIEW_TYPE_SCROLL, VIEW_TYPE_DEFAULT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface viewType {

    }

    //預設為不使用ScrollLayout
    private
    @groupType
    int viewType = VIEW_TYPE_DEFAULT;

    private OniOSHeaderListViewItemTouchListener oniOSHeaderListViewItemTouchListener;

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position > 0 && !getHeaderAccordingTo(position - 1).equals(getHeaderAccordingTo(position))) {
            return HEADER;
        }
        return ITEM;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ArrayList data = getData();
        //若要重組
        if (getGroupType() == GROUP_TYPE_REGROUP) {
            setData(getReGroupData(data));
        }
        if (getItemViewType(position) == HEADER) {
            //遞補變成header的那一項
            if (data.get(position) != data.get(position + 1)) {
                data.add(position + 1, data.get(position));
                notifyDataSetChanged();
            }
            return getCustomHeaderView(position, view, viewGroup);
        }

        View itemView = getCustomItemView(position, view, viewGroup);
        if (getViewType() == VIEW_TYPE_SCROLL) {//如果啟動 Scroll Type,項目就裝上onTouchListener
            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return oniOSHeaderListViewItemTouchListener.oniOSHeaderListViewItemTouch(view,motionEvent);
                }
            });
        }
        return itemView;
    }

    //----------------------

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

    //----------------------

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

    //----------------------

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

    //----------------------

    /**
     * 設置資料
     */
    public abstract ArrayList getData();

    /**
     * 重新設置資料
     */
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
    public String getHeaderAccordingTo(int position) {
        return setHeaderAccordingTo(position);
    }

    /**
     * 取得所有標題種類
     */
    private ArrayList<String> getAllHeaderTypes() {
        ArrayList data = getData();
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

    //---------------------

    /**
     * 讓使用者決定 要不要 使用ScrollLayout ->VIEW_TYPE_DEFAULT
     */
    public
    @viewType
    int getViewType() {
        return viewType;
    }

    public void setViewType(@viewType int viewType) {
        this.viewType = viewType;
    }
    //----------------------
    /**
     * interface 將動作行為提出adapter 行為不要留在adapter..
     * */
    public void setOniOSHeaderListViewItemTouchListener(OniOSHeaderListViewItemTouchListener oniOSHeaderListViewItemTouchListener){
        this.oniOSHeaderListViewItemTouchListener = oniOSHeaderListViewItemTouchListener;
    }
    public interface OniOSHeaderListViewItemTouchListener{
        boolean oniOSHeaderListViewItemTouch(View view, MotionEvent motionEvent);
    }


}