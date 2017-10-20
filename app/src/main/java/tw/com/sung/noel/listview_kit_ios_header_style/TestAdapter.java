package tw.com.sung.noel.listview_kit_ios_header_style;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import tw.com.sung.noel.listview_kit_ios_header_style.iosheaderlistview.iOSHeaderListViewAdapter;

/**
 * Created by noel on 2017/10/12.
 */

public class TestAdapter extends iOSHeaderListViewAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<TestModel> dataList;


    public TestAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        dataList = new ArrayList<>();
    }

    public void updateData(ArrayList<TestModel> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View setCustomHeaderView(int position, View convertView, ViewGroup viewGroup) {
        ViewHeaderHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_title, null);
            holder = new ViewHeaderHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (ViewHeaderHolder) convertView.getTag();

        holder.textView.setText(dataList.get(position).getTag());
        return convertView;
    }

    @Override
    public View setCustomItemView(int position, View convertView, ViewGroup viewGroup) {
        ViewItemHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_item, null);
            holder = new ViewItemHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (ViewItemHolder) convertView.getTag();

        holder.textView2.setText(dataList.get(position).getName());
        return convertView;
    }

    @Override
    public ArrayList getData() {
        return dataList;
    }

    @Override
    public String setHeaderAccordingTo(int position) {
        return dataList.get(position).getTag();
    }


    static class ViewHeaderHolder {
        @BindView(R.id.textView)
        TextView textView;

        ViewHeaderHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewItemHolder {
        @BindView(R.id.textView2)
        TextView textView2;

        ViewItemHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
