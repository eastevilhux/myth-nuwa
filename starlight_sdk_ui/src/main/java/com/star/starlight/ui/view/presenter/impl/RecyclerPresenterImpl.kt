package com.star.starlight.ui.view.presenter.impl

import com.star.starlight.ui.view.entity.IRecyclerItem
import com.star.starlight.ui.view.presenter.IRecyclerPresenter

open class RecyclerPresenterImpl<I : IRecyclerItem> : IRecyclerPresenter<I>{
    /**
     * 列表Item点击事件监听
     * create by Eastevil at 2022/5/10 15:46
     * @author Eastevil
     * @param entity
     *      点击的item对象
     * @return
     *      void
     */
    override fun onItemClickListener(entity: I, position: Int) {

    }

    /**
     * 列表Item选择事件监听
     * create by Eastevil at 2022/5/10 15:46
     * @author Eastevil
     * @param entity
     *      选择的item对象
     * @return
     *      void
     */
    override fun onItemSelectListener(entity: I) {

    }

    /**
     * 列表Item菜单按钮点击事件监听
     * create by Eastevil at 2022/5/10 15:46
     * @author Eastevil
     * @param entity
     *      点击的Item对象
     * @return
     *      void
     */
    override fun onItemMenuClickListener(entity: I) {

    }

    /**
     * 列表Item编辑事件监听
     * create by Eastevil at 2022/5/10 15:46
     * @author Eastevil
     * @param entity
     *      需要编辑的item对象
     * @return
     *      void
     */
    override fun onEditListener(entity: I) {

    }
}
