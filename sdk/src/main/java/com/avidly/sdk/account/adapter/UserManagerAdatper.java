package com.avidly.sdk.account.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avidly.sdk.account.data.adapter.UserOperationData;
import com.sdk.avidly.account.R;

import java.util.List;

public class UserManagerAdatper extends BaseAdapter<UserManagerAdatper.ViewHolder> {

    List<UserOperationData> dataList;

    public UserManagerAdatper(Context context) {
        super(context);
    }

    public void setDataList(List<UserOperationData> list) {
        if (dataList != list) {
            dataList = list;
            notifyDataSetChanged();
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.avidly_item_user_manager_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (dataList != null && dataList.size() > position) {
            holder.bindItemViewData(dataList.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;

        ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        void bindItemViewData(final UserOperationData data, int position) {
            itemView.setTag(data);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClick != null) {
                        Object tag = view.getTag();
                        UserOperationData datatag = data;
                        if (tag instanceof UserOperationData) {
                            datatag = (UserOperationData) tag;
                        }
                        onItemClick.onItemClick(datatag, datatag.type);
                    }
                }
            });

            boolean show = data.isgrid ? position % 2 == 0 : false;
            itemView.findViewById(R.id.avidly_guest_bind_item_right_line).setVisibility(show ? View.VISIBLE : View.GONE);

            show = position == 0 || data.isgrid && position == 1;
            itemView.findViewById(R.id.avidly_guest_bind_item_top_line).setVisibility(show ? View.VISIBLE : View.GONE);

            //show = !data.isgrid || data.isgrid && (position < 2);
            //itemView.findViewById(R.id.avidly_guest_bind_item_bottom_line).setVisibility(show ? View.VISIBLE : View.GONE);


            TextView textView = itemView.findViewById(R.id.avidly_user_manager_item_text);
            textView.setText(data.text);

            ImageView imageView = itemView.findViewById(R.id.avidly_user_manager_item_icon);
            imageView.setImageResource(data.iconid);

        }
    }
}
