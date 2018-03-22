package com.jerryweijin.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Jerry on 3/20/18.
 */

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ListItem> mData;
    private HashSet<ListItem> mSelectedItem;
    private Context mContext;
    private ItemAdapterListener mListener;
    private boolean isSelectMode = false;

    public ItemAdapter(Context context, ArrayList<ListItem> data, ItemAdapterListener listener) {
        mContext = context;
        mData = data;
        mListener = listener;
        mSelectedItem = new HashSet<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType ==0) {
            //header
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            //item
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_row, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItem currentItem = mData.get(position);
        switch (holder.getItemViewType()) {
            case 0:
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                headerViewHolder.mTextView.setText(currentItem.getItemText());
                if (isSelectMode) {
                    headerViewHolder.mCheckBox.setVisibility(View.VISIBLE);
                    headerViewHolder.mCheckBox.setChecked(currentItem.isChecked());
                } else {
                    headerViewHolder.mCheckBox.setVisibility(View.GONE);
                }
                break;
            case 1:
                ItemViewHolder itemViewholder = (ItemViewHolder) holder;
                itemViewholder.mTextView.setText(currentItem.getItemText());
                if (isSelectMode) {
                    itemViewholder.mCheckBox.setVisibility(View.VISIBLE);
                    itemViewholder.mCheckBox.setChecked(currentItem.isChecked());
                } else {
                    itemViewholder.mCheckBox.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getItemType();
    }

    public void ChangeSelectMode(boolean selectMode) {
        isSelectMode = selectMode;
        notifyDataSetChanged();
    }

    public boolean isSelectMode() {
        return isSelectMode;
    }

    private boolean isAllItemChecked(int section) {
        boolean isAllCheck = true;
        for (ListItem item : mData) {
            if (item.getSection() == section) {
                if (!item.isChecked() && item.getItemType()==1) {
                    isAllCheck = false;
                    break;
                }
            }
        }
        return isAllCheck;
    }

    public int getSelectedItemCount() {
        return mSelectedItem.size();
    }

    public void clearCheckState() {
        for (ListItem item : mData) {
            item.setChecked(false);
        }
        mSelectedItem.clear();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private CheckBox mCheckBox;
        private ImageView mPreviewImageView;
        private TextView mTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.itemCheckBox);
            mPreviewImageView = itemView.findViewById(R.id.previewImageView);
            mTextView = itemView.findViewById(R.id.itemTextView);

            mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox checkBox = (CheckBox) view;
                    ListItem currentItem = mData.get(getAdapterPosition());
                    currentItem.setChecked(checkBox.isChecked());
                    if (checkBox.isChecked()) {
                        //Add it to mSelectedItem
                        mSelectedItem.add(currentItem);
                    } else {
                        //Remove it from mSelectedItem.
                        mSelectedItem.remove(currentItem);
                    }
                    ListItem headerItem = null;
                    for (ListItem item : mData) {
                        if (item.getSection() == currentItem.getSection()) {
                            headerItem = item;
                            break;
                        }
                    }
                    if (isAllItemChecked(currentItem.getSection())) {
                        //Check the header checkbox
                        headerItem.setChecked(true);
                    } else {
                        //Uncheck the header checkbox
                        headerItem.setChecked(false);
                    }
                    mListener.onItemClicked(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private CheckBox mCheckBox;
        private TextView mTextView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.headerCheckBox);
            mTextView = itemView.findViewById(R.id.headerTextView);

            mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox checkBox = (CheckBox) view;
                    ListItem currentItem = mData.get(getAdapterPosition());
                    currentItem.setChecked(checkBox.isChecked());
                    for (ListItem item : mData) {
                        if (item.getSection() == currentItem.getSection() && item.getItemType() == 1) {
                            item.setChecked(checkBox.isChecked());
                            if (checkBox.isChecked()) {
                                mSelectedItem.add(item);
                            } else {
                                mSelectedItem.remove(item);
                            }
                        }
                    }
                    mListener.onItemClicked(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

    public interface ItemAdapterListener {
        void onItemClicked(int position);
    }
}
