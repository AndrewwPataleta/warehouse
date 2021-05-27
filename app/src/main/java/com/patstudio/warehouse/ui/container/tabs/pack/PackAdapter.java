package com.patstudio.warehouse.ui.container.tabs.pack;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.patstudio.warehouse.R;
import com.patstudio.warehouse.data.models.OrderModel;
import com.patstudio.warehouse.data.models.PickerOrderModel;
import com.patstudio.warehouse.databinding.ItemPackBinding;
import com.patstudio.warehouse.ui.custom.OrderStatusView;

import java.util.List;

public class PackAdapter extends RecyclerView.Adapter<PackAdapter.ViewHolder> {

    private List<PickerOrderModel> list;
    private PackViewModel packViewModel;

    public PackAdapter(List<PickerOrderModel> list, PackViewModel packViewModel) {
       this.list = list;
       this.packViewModel = packViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_pack, parent, false));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PickerOrderModel resourceModel = list.get(position);
        holder.binding.setModel(resourceModel);
        holder.binding.setViewModel(packViewModel);
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
        ItemPackBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

    }

}
