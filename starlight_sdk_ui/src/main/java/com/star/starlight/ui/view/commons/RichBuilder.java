package com.star.starlight.ui.view.commons;

import android.content.Context;

import com.star.starlight.ui.view.entity.RecyclerItemEntity;
import com.star.starlight.ui.view.presenter.impl.RichPresenterImpl;

public class RichBuilder extends AdapterBuilder<RecyclerItemEntity, RichAdapter>{

    public RichBuilder(Context context) {
        super(context);
    }

    @Override
    public RichAdapter builder() {
        return new RichAdapter(this);
    }
}
