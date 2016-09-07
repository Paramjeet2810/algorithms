package com.suvariyaraj.algorithms.GridView;

/**
 * Created by GOODBOY-PC on 23/05/16.
 */
public class GridViewItem {

    int image_id;
    String title;

    public GridViewItem(int image_id, String title) {
        this.image_id = image_id;
        this.title = title;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
