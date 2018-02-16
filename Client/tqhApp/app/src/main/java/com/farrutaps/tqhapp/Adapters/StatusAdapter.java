package com.farrutaps.tqhapp.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.farrutaps.tqhapp.R;
import com.farrutaps.tqhapp.model.Options;
import com.farrutaps.tqhapp.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatusAdapter extends BaseAdapter {

    private Context context;
    private List<Options> options;
    private User userWZ, userFK;
    private LayoutInflater layoutInflater = null;

    public StatusAdapter(Context context, Options[] options, List<User> users) {
        this.context = context;
        this.options = new ArrayList<>(Arrays.asList(options));
        this.userWZ = users.get(0);
        this.userFK = users.get(1);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final StatusHolder statusHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_status, null);
            statusHolder = new StatusHolder(convertView);
            convertView.setTag(statusHolder);
        } else
            statusHolder = (StatusHolder) convertView.getTag();

        // Set item data
        statusHolder.tvStatus.setText(getItemName(position));

        boolean wzOn, fkOn;
        wzOn = userWZ.getStatus().getOnFromOption(getItem(position));
        fkOn = userFK.getStatus().getOnFromOption(getItem(position));
        setLedColor(statusHolder.ledWZ, wzOn, convertView);
        setLedColor(statusHolder.ledFK, fkOn, convertView);

        // Return view
        return convertView;
    }

    private void setLedColor(ImageView led, boolean on, View view)
    {
        int drawable;
        if(on)
            drawable = R.drawable.led_on;
        else
            drawable = R.drawable.led_off;
        led.setBackground(view.getResources().getDrawable(drawable));
    }
}

class StatusHolder {

    public ImageView ledWZ;
    public ImageView ledFK;
    public TextView tvStatus;

    public StatusHolder(View base) {
        this.ledWZ = (ImageView) base.findViewById(R.id.led_wz);
        this.ledFK = (ImageView) base.findViewById(R.id.led_fk);
        this.tvStatus = (TextView) base.findViewById(R.id.tv_status_item);
    }
}



