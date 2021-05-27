package com.patstudio.warehouse.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.patstudio.warehouse.databinding.ViewOrderStatusBinding;



public class OrderStatusView extends FrameLayout {

    ViewOrderStatusBinding binding;
    private Context context;

    private void init(Context context, int order_nr, int progress) {

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.binding = ViewOrderStatusBinding.inflate(inflater);
        this.binding.setOrderNr(order_nr);
        this.binding.setProgress(progress);
        addView(binding.getRoot());
    }

    public OrderStatusView(Context context, int order_nr, int progress) {
        super(context);
        init(context, order_nr, progress);
    }

    public OrderStatusView(Context context, AttributeSet attrs, int order_nr, int progress) {
        super(context, attrs);
        init(context, order_nr, progress);
    }

    public OrderStatusView(Context context, AttributeSet attrs, int defStyleAttr, int order_nr, int progress) {
        super(context, attrs, defStyleAttr);
        init(context, order_nr, progress);
    }

    public OrderStatusView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int order_nr, int progress) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, order_nr, progress);
    }



}

