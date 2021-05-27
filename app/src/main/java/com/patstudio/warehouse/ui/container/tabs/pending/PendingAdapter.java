package com.patstudio.warehouse.ui.container.tabs.pending;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.patstudio.warehouse.R;
import com.patstudio.warehouse.data.models.OrderModel;
import com.patstudio.warehouse.data.models.PickerOrderModel;
import com.patstudio.warehouse.databinding.ItemPendingBinding;
import com.patstudio.warehouse.ui.custom.OrderStatusView;

import java.util.List;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.ViewHolder> {

    private List<PickerOrderModel> list;
    private PendingViewModel pendingViewModel;

    public PendingAdapter(List<PickerOrderModel> list, PendingViewModel pendingViewModel) {
       this.list = list;
       this.pendingViewModel = pendingViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_pending, parent, false));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PickerOrderModel resourceModel = list.get(position);
        holder.binding.setModel(resourceModel);
        holder.binding.setViewModel(pendingViewModel);
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
        ItemPendingBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

    }

}
