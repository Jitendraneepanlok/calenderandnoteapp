package com.timespace.notesapp.ui.meeting.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.timespace.notesapp.R;
import com.timespace.notesapp.base.AppBaseRecycleAdapter;
import com.timespace.notesapp.firebasemodel.AlertModel;

import java.util.List;

public class Alert_adapter extends AppBaseRecycleAdapter {

    Context context;
    List<AlertModel> list;

    public Alert_adapter(Context context, List<AlertModel> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new Alert_adapter.ViewHolder(inflateLayout(R.layout.alert_item));
    }

    @Override
    public int getDataCount() {
        // return 10;

        return list == null ? 0 : list.size();
    }

    private class ViewHolder extends BaseViewHolder {
        TextView tvAlertTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAlertTime = itemView.findViewById(R.id.tv_alert_time);
        }

        @Override
        public void setData(int position) {

            AlertModel alertModel = list.get(position);
            tvAlertTime.setText(alertModel.getAlertTime());
        }

//        @Override
//        public void onClick(View v) {
//            performItemClick((Integer) v.getTag(), v);
//        }

    }
}