package com.example.choi.knocktalk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choi.knocktalk.AdapterItem.SecondGriditem;
import com.example.choi.knocktalk.R;

import java.util.ArrayList;

/**
 * Created by choi on 17. 9. 17.
 */

public class SecondGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SecondGriditem> griditems;

    public SecondGridAdapter(Context context, ArrayList<SecondGriditem> griditems) {
        this.context = context;
        this.griditems = griditems;
    }

    @Override
    public int getCount() {
        return griditems.size();
    }

    @Override
    public Object getItem(int position) {
        return griditems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertview == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview = inflater.inflate(R.layout.second_grid_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imgview = (ImageView) convertview.findViewById(R.id.Second_grid_img);
            viewHolder.textView = (TextView) convertview.findViewById(R.id.Second_grid_name);

            convertview.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertview.getTag();
        }
        viewHolder.imgview.setImageBitmap(griditems.get(position).getBitmap());
        viewHolder.textView.setText(griditems.get(position).getName());

        return convertview;
    }


    static class ViewHolder {
        ImageView imgview;
        TextView textView;

    }
}
















