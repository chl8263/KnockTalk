package com.example.choi.knocktalk.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choi.knocktalk.AdapterItem.GridItem;
import com.example.choi.knocktalk.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by choi on 17. 8. 20.
 */

public class FirstGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GridItem> items;

    public FirstGridAdapter(Context context, ArrayList<GridItem> gridItems) {
        this.context = context;
        this.items = gridItems;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.first_grid_item, parent, false);

            holder = new ViewHolder();
            holder.gridimg = (ImageView) convertView.findViewById(R.id.gridImg);
            /*holder.gridimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(parent.getContext(),position+"show",Toast.LENGTH_SHORT).show();

                }
            });*/
            holder.text = (TextView) convertView.findViewById(R.id.gridText);
            holder.gridimgKing = (ImageView) convertView.findViewById(R.id.gridImgKing);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //Uri uri = Uri.parse(items.get(position).toString());
        //Picasso.with(convertView.getContext()).load(uri).into(holder.gridimg);
        //holder.gridimg.setImageResource(items.get(position).getGridimage());
        //holder.gridimg.setImageURI(Uri.parse(items.get(position).getGridimage()));
        Picasso.with(parent.getContext()).load(Uri.parse(items.get(position).getGridimage())).centerCrop().fit().into(holder.gridimg);
        holder.text.setText(items.get(position).getGridText());
        /*if(position==0){
            holder.gridimgKing.setVisibility(View.VISIBLE);
        }else{
            holder.gridimgKing.setVisibility(View.INVISIBLE);
        }*/
        return convertView;
    }

    static class ViewHolder {
        ImageView gridimg;
        TextView text;
        ImageView gridimgKing;
    }
}

