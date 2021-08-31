package com.timespace.notesapp.viewmodel;

import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.timespace.notesapp.R;
import com.timespace.notesapp.listener.ItemClickListener;

public class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView title;

    private ItemClickListener itemClickListener;

    public NotesViewHolder(View itemView) {
        super(itemView);
        title=(TextView) itemView.findViewById(R.id.title);

        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
