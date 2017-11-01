package tw.com.sung.noel.scrollerlistview;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


import tw.com.sung.noel.scrollerlistview.scrollerlistview.ScrollerListViewAdapter;


/**
 * Created by noel on 2017/10/12.
 */

public class TestAdapter extends ScrollerListViewAdapter {

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
            TextView textView = (TextView) convertView.findViewById(R.id.textView);
            holder = new ViewHeaderHolder(textView);
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
            TextView textView = (TextView) convertView.findViewById(R.id.textView2);
            holder = new ViewItemHolder(textView);
            convertView.setTag(holder);
        } else
            holder = (ViewItemHolder) convertView.getTag();

        holder.textView2.setText(dataList.get(position).getName());
        return convertView;
    }

    @Override
    public ArrayList setData() {
        return dataList;
    }

    @Override
    public String setHeaderAccordingTo(int position) {
        return dataList.get(position).getTag();
    }


    static class ViewHeaderHolder {
        TextView textView;

        public ViewHeaderHolder(TextView textView) {
            this.textView = textView;
        }
    }

    static class ViewItemHolder {
        TextView textView2;

        public ViewItemHolder(TextView textView2) {
            this.textView2 = textView2;
        }
    }
}
