package com.example.choi.knocktalk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choi.knocktalk.AdapterItem.DrawerItem;
import com.example.choi.knocktalk.R;

import java.util.ArrayList;

/**
 * Created by choi on 17. 9. 18.
 */

public class DrawerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DrawerItem> drawerItems ;

    public DrawerAdapter(Context context, ArrayList<DrawerItem> drawerItems) {
        this.context = context;
        this.drawerItems = drawerItems;
    }

    @Override
    public int getCount() {
        return drawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return drawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drawer_item,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.imageView=(ImageView)convertView.findViewById(R.id.drawImg);
            viewHolder.textView=(TextView)convertView.findViewById(R.id.drawName);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.imageView.setImageResource(drawerItems.get(position).getResource());
        viewHolder.textView.setText(drawerItems.get(position).getName());
        return convertView;
    }

    static class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}



















