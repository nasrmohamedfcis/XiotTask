package com.example.nasrmohamed.xiottask.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nasrmohamed.xiottask.R;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Item> myItem;


    public ListViewAdapter(Context context, List<Item> myItem) {
        this.context = context;
        this.myItem = myItem;
    }

    @Override
    public int getCount() {
        return myItem.size();
    }

    @Override
    public Object getItem(int position) {
        return myItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.listview_item,null);
        TextView topic = v.findViewById(R.id.txt_topic);
        TextView message = v.findViewById(R.id.txt_message);
        TextView qos = v.findViewById(R.id.txt_qos);

        //Setting the textviews
        message.setText(myItem.get(position).getMessage());
        topic.setText(myItem.get(position).getTopic());
        qos.setText(myItem.get(position).getQos());

        return v;
    }
}
