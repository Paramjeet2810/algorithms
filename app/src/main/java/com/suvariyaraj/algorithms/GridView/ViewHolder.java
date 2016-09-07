package com.suvariyaraj.algorithms.GridView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.suvariyaraj.algorithms.R;

/**
 * Created by GOODBOY-PC on 24/05/16.
 */
public class ViewHolder{
    public ImageView image;
    public TextView tv;

    public ViewHolder(View v) {
        image = (ImageView) v.findViewById(R.id.gridviewitemimage);
        tv = (TextView) v.findViewById(R.id.gridviewitemtext);
    }
}