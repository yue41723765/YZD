package com.android.yzd.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/4/5.
 */

public abstract class AbsAdapter<T> extends BaseAdapter {
    private Context context;
    private int resId;
    private List<T> list;

    public AbsAdapter(Context context, int resId) {
        this.context = context;
        this.resId = resId;
    }

    public void addListToAdapter(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(resId,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
            bandViewHolder(viewHolder,list.get(position),position);
        return convertView;
    }

    protected abstract void bandViewHolder(ViewHolder viewHolder,T t,int position);

    public class ViewHolder {
        private View view;
        private Map<Integer, View> map = new HashMap<Integer, View>();

        public ViewHolder(View view) {
            this.view = view;
        }

        public View getView(int id) {
            if (map.containsKey(id)) {
                return map.get(id);
            } else {
                View mView = view.findViewById(id);
                map.put(id, mView);
                return mView;
            }
        }

        public ViewHolder bandTextView(int id, String text) {
            TextView textView = (TextView) getView(id);
            textView.setText(text);
            return this;
        }

        public ViewHolder bandImageView(int id, Bitmap bitmap) {
            ImageView imageView = (ImageView) getView(id);
            imageView.setImageBitmap(bitmap);
            return this;
        }
    }
}
