package com.suvariyaraj.algorithms.GridView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.suvariyaraj.algorithms.R;

import java.util.ArrayList;

/**
 * Created by GOODBOY-PC on 23/05/16.
 */
public class GridViewAdapter extends BaseAdapter {
    ArrayList<GridViewItem> list;
    Context context;

    public GridViewAdapter(Context contexts, ArrayList<Integer> image_ids, ArrayList<String> titles) {
        this.context = contexts;
        list = new ArrayList<GridViewItem>();
        for(int i=0;i<image_ids.size ();i++) {
            GridViewItem gridViewItem = new GridViewItem(image_ids.get(i), titles.get(i));
            list.add(gridViewItem);
        }
    }

    @Override
    public int getCount() {
        return list.size();
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
        if (convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.single_grid_view_item, parent, false);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.gridviewitemimage);
        TextView tv = (TextView) convertView.findViewById(R.id.gridviewitemtext);
        GridViewItem temp = list.get(position);
        image.setImageResource(temp.getImage_id());
        tv.setText(temp.getTitle());
        return convertView;
    }
}
