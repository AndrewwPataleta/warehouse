package com.patstudio.warehouse.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.patstudio.warehouse.databinding.ViewCartonParametersBinding;
import com.patstudio.warehouse.databinding.ViewQuantityControllerBinding;


public class CartonParametersView extends FrameLayout {

    ViewCartonParametersBinding binding;
    private Context context;

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.binding = ViewCartonParametersBinding.inflate(inflater);
        addView(binding.getRoot());
    }

    public CartonParametersView(Context context) {
        super(context);
        init(context);
    }

    public CartonParametersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CartonParametersView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CartonParametersView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }



}

