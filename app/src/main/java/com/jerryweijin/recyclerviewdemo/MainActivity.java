package com.jerryweijin.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ItemAdapter.ItemAdapterListener{

    private ArrayList<ListItem> mData = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;
    private ActionMode mActionMode;
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.action_mode_menu, menu);
            mode.setTitle("Select Videos");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.share:
                    Toast.makeText(MainActivity.this,
                            mAdapter.getSelectedItemCount() + " items will be shared",
                            Toast.LENGTH_LONG).show();
                    break;
                case R.id.delete:
                    Toast.makeText(MainActivity.this,
                            mAdapter.getSelectedItemCount() + " items will be deleted",
                            Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.ChangeSelectMode(false);
            mAdapter.clearCheckState();
            mActionMode = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mData.add(new ListItem(0, "March 21, 2018", 0));
        for (int i = 0; i < 4; i++) {
            mData.add(new ListItem(0, i + " This is a very very very very long line", 1));
        }
        mData.add(new ListItem(1, "March 20, 2018", 0));
        for (int i = 0; i < 5; i++) {
            mData.add(new ListItem(1, i + " This is a very very very very long line", 1));
        }

        mAdapter = new ItemAdapter(this, mData, this);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSelect:
                if (mActionMode == null) {
                    mActionMode = startSupportActionMode(mActionModeCallback);
                    mAdapter.ChangeSelectMode(true);
                }
                break;
            default:
                break;

        }
        return true;
    }

    @Override
    public void onItemClicked(int position) {
        int count = mAdapter.getSelectedItemCount();
        if (count == 0) {
            mActionMode.setTitle("Select Videos");
        } else {
            mActionMode.setTitle(count + " Selected");
        }
    }
}
