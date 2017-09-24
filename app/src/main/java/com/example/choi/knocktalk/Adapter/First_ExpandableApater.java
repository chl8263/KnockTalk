package com.example.choi.knocktalk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choi.knocktalk.First_Dialog.First_NOT_DIalog;
import com.example.choi.knocktalk.First_Dialog.First_OK_Dialog;
import com.example.choi.knocktalk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by choi on 17. 9. 22.
 */

public class First_ExpandableApater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;
    private final int EXISTENCE=3;
    private final int NONEXISTENCE=4;
    private List<Item> data;
    private Context context;
    public First_ExpandableApater(List<Item> data,Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {    //ViewHolder 를 생성해주는것 viewtype 이 하나 이상이라면 하나 이상이 생성된다
        View view = null;
       /* float dp = parent.getContext().getResources().getDisplayMetrics().density;  //px와 dp간의 비율,코드상에서 화면 UI 나 크기를 정할 필요가 있다면 단말별로 DPI를 맞게 설정해준다
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);*/
        switch (viewType) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.expandable_header, parent, false);
                LIstHeaderViewHolder header = new LIstHeaderViewHolder(view);
                return header;
            case CHILD:
                LayoutInflater inflater_child = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater_child.inflate(R.layout.epandable_child, parent, false);
                ListChildViewHolder child = new ListChildViewHolder(view);
               /* TextView itemTextView = new TextView(parent.getContext());
                itemTextView.setPadding(subItemPaddingLeft, subItemPaddingTopAndBottom, 0, subItemPaddingTopAndBottom);
                itemTextView.setTextColor(0x88000000);
                itemTextView.setLayoutParams(
                        new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));*/
                return child;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {   //viewHolder 가 생성이 되었다면 그다음 부터는 이것만 불린다
        final Item item = data.get(position);
        switch (item.type) {
            case HEADER:
                final LIstHeaderViewHolder itemController = (LIstHeaderViewHolder) holder;
                itemController.redderalItem = item;
                itemController.header_title.setText(item.text);
                if (item.invisibleChildren == null) {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                } else {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                }
                itemController.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.redderalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                        } else {
                            int pos = data.indexOf(itemController.redderalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                            item.invisibleChildren = null;
                        }
                    }
                });
                break;
            case CHILD:
                final ListChildViewHolder itemChild_Controller = (ListChildViewHolder) holder;
                itemChild_Controller.textView.setText(data.get(position).text);
                if (data.get(position).i==NONEXISTENCE){
                    itemChild_Controller.imageView.setImageResource(R.drawable.first_not);
                }else if(data.get(position).i==EXISTENCE){
                    itemChild_Controller.imageView.setImageResource(R.drawable.first_ok);
                }
                itemChild_Controller.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (data.get(position).i==NONEXISTENCE) {
                            Log.e("asd", (String) itemChild_Controller.textView.getText()+"not");
                            Intent intent =new Intent(context.getApplicationContext(), First_NOT_DIalog.class);
                            intent.putExtra("name",(String) itemChild_Controller.textView.getText());
                            view.getContext().startActivity(intent);
                        }
                        else if (data.get(position).i==EXISTENCE) {
                            Log.e("asd", (String) itemChild_Controller.textView.getText()+"OK");
                            Intent intent =new Intent(context.getApplicationContext(), First_OK_Dialog.class);
                            intent.putExtra("name",(String) itemChild_Controller.textView.getText());
                            view.getContext().startActivity(intent);
                           /*Intent intent = new Intent(context.getApplicationContext(), DownLoad_Progress.class);
                            view.getContext().startActivity(intent);*/
                        }
                        //Log.e("asd", (String) itemChild_Controller.textView.getText()); //child 의 text 가져온다
                    }
                });
                /*TextView itemTextView = (TextView) holder.itemView;
                itemTextView.setText(data.get(position).text);*/
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ListChildViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ImageView imageView;

        public ListChildViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.child_name);
            imageView = (ImageView) itemView.findViewById(R.id.child_btn);
        }
    }

    public static class LIstHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public ImageView btn_expand_toggle;
        public Item redderalItem;

        public LIstHeaderViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.header_Title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);

        }
    }

    public static class Item {
        public int type;
        public String text;
        public int i;
        public List<Item> invisibleChildren;

        public Item() {

        }

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }
        public Item(int type, String text,int i) {
            this.type = type;
            this.text = text;
            this.i=i;
        }
    }
}
