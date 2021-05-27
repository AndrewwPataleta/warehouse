package com.patstudio.warehouse.ui.container.tabs.pick;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.patstudio.warehouse.R;
import com.patstudio.warehouse.data.models.OrderModel;
import com.patstudio.warehouse.data.models.PickerOrderModel;
import com.patstudio.warehouse.databinding.ItemPickBinding;
import com.patstudio.warehouse.ui.custom.OrderStatusView;

import java.util.List;

public class PickAdapter extends RecyclerView.Adapter<PickAdapter.ViewHolder> {

    private List<PickerOrderModel> list;
    private PickViewModel pickViewModel;

    public PickAdapter(List<PickerOrderModel> list, PickViewModel pickViewModel) {
       this.list = list;
        this.pickViewModel = pickViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_pick, parent, false));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PickerOrderModel resourceModel = list.get(position);
        holder.binding.setModel(resourceModel);
        holder.binding.setViewModel(pickViewModel);
        holder.binding.orderStatusContainer.removeAllViews();
        for (OrderModel orderModel:resourceModel.getOrders()) {
            holder.binding.orderStatusContainer.addView(new OrderStatusView(holder.itemView.getContext(), orderModel.getOrder_nr(), orderModel.getProgress()));
        }
     }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemPickBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

    }

}
