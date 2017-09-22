package com.android.yzd.ui.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.android.yzd.R;
import com.android.yzd.been.ClassifyToolsEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/18.
 */

public class ShoppingCartAdapter extends AbsAdapter<ClassifyToolsEntity> {
    private Map<Integer,Boolean> map=new HashMap<Integer, Boolean>();

    public ShoppingCartAdapter(Context context) {
        super(context,R.layout.item_shopping_cart);
        map.clear();
    }


    @Override
    protected void bandViewHolder(ViewHolder viewHolder, ClassifyToolsEntity classifyToolsEntity, int position) {
        viewHolder.bandTextView(R.id.text,classifyToolsEntity.getType_name());
        boolean check;
        if(!map.containsKey(position)){
            map.put(position,false);
        }
        check= map.get(position);

        CheckBox checkbox = (CheckBox) viewHolder.getView(R.id.checkbox);
        checkbox.setTag(position);

        checkbox.setChecked(check);

        checkbox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int position = (int) compoundButton.getTag();
                map.put(position,b);
            }
        });

    }
}
