package com.farrutaps.tqhapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.farrutaps.tqhapp.R;
import com.farrutaps.tqhapp.model.Options;
import com.farrutaps.tqhapp.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sònia Batllori on 16/02/2018.
 */
public class MyStatusAdapter extends BaseAdapter {

    private Context context;
    private List<Options> options;
    private User master;
    private LayoutInflater layoutInflater = null;

    public MyStatusAdapter(Context context, Options[] options, User user) {
        this.context = context;
        this.options = new ArrayList<>(Arrays.asList(options));
        this.master = user;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public Options getItem(int position) {
        return options.get(position);
    }

    @Override
    public long getItemId(int position) {
        return options.get(position).ordinal();
    }

    public String getItemName(int position) {
        return options.get(position).getTitle();
    }

    public boolean isStatusOn(int position) {
        return master.getStatus().getOnFromOption(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MyStatusHolder myStatusHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_my_status, null);
            myStatusHolder = new MyStatusHolder(convertView);
            convertView.setTag(myStatusHolder);
        } else
            myStatusHolder = (MyStatusHolder) convertView.getTag();

        // Set item data
        myStatusHolder.ctvMyStatus.setText(getItemName(position));
        myStatusHolder.ctvMyStatus.setChecked(isStatusOn(position));

        myStatusHolder.ctvMyStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStatusHolder.ctvMyStatus.setChecked(!myStatusHolder.ctvMyStatus.isChecked());
            }
        });

        // Return view
        return convertView;
    }


}

class MyStatusHolder {

    public CheckedTextView ctvMyStatus;

    public MyStatusHolder(View base) {
        this.ctvMyStatus = (CheckedTextView) base.findViewById(R.id.ctv_my_status_item);
    }
}
