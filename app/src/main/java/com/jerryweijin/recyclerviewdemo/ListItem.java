package com.jerryweijin.recyclerviewdemo;

/**
 * Created by Jerry on 3/21/18.
 */

public class ListItem {
    int mCount; //heander > 0, item = 0
    String mItemText;
    int mItemType; //header = 0; item = 1
    int mSection;
    boolean mIsChecked;

    public ListItem (int section, String itemText, int itemType) {
        mSection = section;
        mItemText = itemText;
        mItemType = itemType;
        mIsChecked = false;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    public String getItemText() {
        return mItemText;
    }

    public void setItemText(String itemText) {
        this.mItemText = itemText;
    }

    public int getItemType() {
        return mItemType;
    }

    public void setItemType(int itemType) {
        this.mItemType = itemType;
    }

    public int getSection() {
        return mSection;
    }

    public void setSection(int section) {
        mSection = section;
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
    }
}
