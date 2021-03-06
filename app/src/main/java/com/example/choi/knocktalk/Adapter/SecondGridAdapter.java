package com.example.choi.knocktalk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choi.knocktalk.AdapterItem.SecondCarditem;
import com.example.choi.knocktalk.R;

import java.util.ArrayList;

/**
 * Created by choi on 17. 9. 17.
 */

public class SecondGridAdapter extends RecyclerView.Adapter<SecondGridAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<SecondCarditem> griditems;
    private ViewHolder holder;
    private final String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/KNOCK_TALK";

    public SecondGridAdapter(Context context, ArrayList<SecondCarditem> griditems) {
        this.context = context;
        this.griditems = griditems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //새로운 view 생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.second_grid_item, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) { //getView 부분을 담당하는 메소드
        holder.imageView.setImageBitmap(griditems.get(position).getBitmap());
        holder.textView.setText(griditems.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return griditems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.Second_grid_img);
            imageView.setOnClickListener(this);
            textView = (TextView) view.findViewById(R.id.Second_grid_name);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.Second_grid_img:
                    int position = getAdapterPosition();
                    /*Intent intent = new Intent(view.getContext(), FingerPrintDialog.class);*/
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse(sdPath + "/" + griditems.get(position).getName() + ".avi");
                    intent.setDataAndType(uri, "video/*");
                    view.getContext().startActivity(intent);
                    break;
            }
        }
    }
}

















