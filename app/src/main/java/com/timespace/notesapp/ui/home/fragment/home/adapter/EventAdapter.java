package com.timespace.notesapp.ui.home.fragment.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.timespace.notesapp.R;
import com.timespace.notesapp.base.AppBaseRecycleAdapter;
import com.timespace.notesapp.firebasemodel.DailyEvents;
import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends AppBaseRecycleAdapter {

    Context context;
    List<DailyEvents> list=new ArrayList<>();
    List<TextView> textViewList=new ArrayList<>();
    private AdaptorAction action;

    public EventAdapter(Context context, List<DailyEvents> list,AdaptorAction action) {
        this.context = context;
        this.list = list;
        this.action=action;

    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.event_item_list));
    }

    @Override
    public int getDataCount() {
        // return 10;

        return list == null ? 0 : list.size();
    }
    public interface AdaptorAction{
    void OnClickListener( List<TextView> value);
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
            action.OnClickListener(textViewList);
            tvEventDate.setText(description.getTitle());
            ll_main.setTag(position);
        }
    }
}