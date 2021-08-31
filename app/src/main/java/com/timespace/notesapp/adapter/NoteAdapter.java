package com.timespace.notesapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.timespace.notesapp.R;
import com.timespace.notesapp.base.AppBaseRecycleAdapter;
import com.timespace.notesapp.firebasemodel.Notes;

import java.util.List;


public class NoteAdapter extends AppBaseRecycleAdapter {

    Context context;
    List<Notes> list;

    public NoteAdapter(Context context, List<Notes> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_notes));
    }

    @Override
    public int getDataCount() {
        // return 10;

        return list == null ? 0 : list.size();
    }

    private class ViewHolder extends BaseViewHolder {
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }

        @Override
        public void setData(int position) {

            Notes description = list.get(position);
            tvTitle.setText(description.getTitle());

            tvTitle.setTag(position);
        }


    }
}
