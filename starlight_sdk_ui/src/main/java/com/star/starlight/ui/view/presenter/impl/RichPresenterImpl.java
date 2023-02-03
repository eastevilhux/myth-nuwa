package com.star.starlight.ui.view.presenter.impl;

import com.star.starlight.ui.view.entity.IRecyclerItem;
import com.star.starlight.ui.view.presenter.IRichPresenter;

public abstract class RichPresenterImpl<I extends IRecyclerItem> implements IRichPresenter<I> {

    @Override
    public void onItemClickListener(I entity, int position) {

    }

    @Override
    public void onItemSelectListener(I entity) {

    }

    @Override
    public void onItemMenuClickListener(I entity) {

    }

    @Override
    public void onEditListener(I entity) {

    }
}
