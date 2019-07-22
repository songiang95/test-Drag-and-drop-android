package com.example.dragdroprecyclerview.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dragdroprecyclerview.Adapter;

public class SimpleItemTouchHelper extends ItemTouchHelper.Callback {

    private Adapter mAdapter;
    private int mFromPos;
    private int mToPos;

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    public SimpleItemTouchHelper(Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        if (mAdapter != null) {
            Log.d("abba", "target position: " + target.getAdapterPosition());
            mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            mFromPos = viewHolder.getAdapterPosition();
            mToPos = target.getAdapterPosition();
            return true;
        }
        return false;
    }


    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (mFromPos != mToPos
                && viewHolder instanceof Adapter.FileViewHolder
                && mAdapter.getFolder(mToPos) instanceof Folder) {
            mAdapter.onItemMoved(mFromPos, mToPos);
            Log.d("abba", "clearView:");
        }
    }
}
