package com.timespace.notesapp.ui.calender.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timespace.notesapp.R;
import com.timespace.notesapp.base.AppBaseRecycleAdapter;
import com.timespace.notesapp.firebasemodel.DailyEvents;
import com.timespace.notesapp.ui.home.fragment.home.adapter.EventAdapter;

import java.util.ArrayList;
import java.util.List;

public class CalenderEventAdapter extends AppBaseRecycleAdapter {

    Context context;
    List<DailyEvents> list=new ArrayList<>();
    List<TextView> textViewList=new ArrayList<>();

    public CalenderEventAdapter(Context context, List<DailyEvents> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new CalenderEventAdapter.ViewHolder(inflateLayout(R.layout.event_item_list));
    }

    @Override
    public int getDataCount() {
        // return 10;

        return list == null ? 0 : list.size();
    }


    private class ViewHolder extends BaseViewHolder {
        TextView tvEventTime,tvEventDate;
        LinearLayout ll_main;

        public ViewHolder(View itemView) {
            super(itemView);
            tvEventTime = itemView.findViewById(R.id.tv_event_time);
            tvEventDate = itemView.findViewById(R.id.tv_event_name);
            ll_main = itemView.findViewById(R.id.ll_main);

        }

        @Override
        public void setData(int position) {

            DailyEvents description = list.get(position);
            tvEventTime.setText(description.getTime());
            textViewList.add(position,tvEventTime);
            tvEventDate.setText(description.getTitle());
            ll_main.setTag(position);
        }
    }
}
