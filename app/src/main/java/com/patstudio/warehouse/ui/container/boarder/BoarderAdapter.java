package com.patstudio.warehouse.ui.container.boarder;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.patstudio.warehouse.R;
import com.patstudio.warehouse.data.models.ItemModel;
import com.patstudio.warehouse.data.models.OrderModel;
import com.patstudio.warehouse.data.models.PickerOrderModel;
import com.patstudio.warehouse.databinding.ItemBoarderBinding;
import com.patstudio.warehouse.databinding.ItemPickBinding;
import com.patstudio.warehouse.ui.container.tabs.pick.PickViewModel;
import com.patstudio.warehouse.ui.custom.OrderStatusView;

import java.util.List;

public class BoarderAdapter extends RecyclerView.Adapter<BoarderAdapter.ViewHolder> {

    private List<ItemModel> list;
    private BoarderViewModel viewModel;

    public BoarderAdapter(List<ItemModel> list, BoarderViewModel boarderViewModel) {
       this.list = list;
       this.viewModel = boarderViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_boarder, parent, false));
    }

    @SuppressLint({"CheckResult", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemModel itemModel = list.get(position);
        holder.binding.setModel(itemModel);
        holder.binding.setViewModel(viewModel);
        if (itemModel.getQuantity_picked() == 0) {
            holder.binding.pickedContainer.setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.background_red_with_stroke));
        } else if (itemModel.getQuantity_picked() > 0 && itemModel.getQuantity_picked() < itemModel.getQuantity() ) {
            holder.binding.pickedContainer.setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.background_orange_with_stroke));
        } else if (itemModel.getQuantity() == itemModel.getQuantity_picked()) {
            holder.binding.pickedContainer.setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.background_green_with_stroke));
        }
     }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemBoarderBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

    }

}
