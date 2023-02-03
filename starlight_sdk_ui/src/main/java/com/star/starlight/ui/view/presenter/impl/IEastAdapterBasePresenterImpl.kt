package com.star.starlight.ui.view.presenter.impl

import com.star.starlight.ui.view.entity.adapter.IEastEntity
import com.star.starlight.ui.view.presenter.IAdapterBasePresenter

/**
 * 列表item事件触发器实现类
 * create by Eastevil at 2022/8/19 14:51
 * @author Eastevil
 * @version 1.0.0
 * @since 1.0.0
 */
abstract class IEastAdapterBasePresenterImpl<T : IEastEntity> : IAdapterBasePresenter<T> {

    override fun onEditListener(entity: T) {

    }

    override fun onItemClickListener(entity: T, position: Int) {

    }

    override fun onItemMenuClickListener(entity: T) {

    }

    override fun onItemSelectListener(entity: T) {

    }

}
