package com.star.starlight.ui.view.commons;

import androidx.databinding.ViewDataBinding;

import com.star.starlight.ui.BR;
import com.star.starlight.ui.view.entity.RecyclerItemEntity;
import com.star.starlight.ui.view.entity.RichItemResouce;
import com.star.starlight.ui.view.presenter.IRichPresenter;
import com.star.starlight.ui.view.presenter.impl.RichPresenterImpl;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public class RichAdapter extends RichBaseAdapter<RecyclerItemEntity>{

    private RichBuilder richBuilder;

    public RichAdapter(@NotNull RichBuilder builder) {
        super(builder.getContext(),builder.getDataList());
        this.richBuilder = builder;
        addLayout(builder);
    }

    @Override
    protected void bindItem(Map map) {
        super.bindItem(map);
    }

    /**
     * 添加用户自定义的布局文件
     * @param builder
     */
    private void addLayout(RichBuilder builder){
        Map<Integer,RichItemResouce> m = builder.getItemResouceMap();
        if(!m.isEmpty()){
            Set<Map.Entry<Integer, RichItemResouce>> entrySet = m.entrySet();
            for(Map.Entry<Integer,RichItemResouce> e : entrySet){
                bindMap.put(e.getKey(),e.getValue().getBrId());
            }
        }
    }

    @Override
    public void setBean(ViewDataBinding dataBinding, int varid, int position) throws IllegalAccessException {
        super.setBean(dataBinding, varid, position);
        if(dataBinding != null){
            if(!richBuilder.getBeanMap().isEmpty()){
                Set<Map.Entry<Integer, Object>> entrySet = richBuilder.getBeanMap().entrySet();
                for(Map.Entry<Integer, Object> e : entrySet){
                    dataBinding.setVariable(e.getKey(),e.getValue());
                }
            }
        }
    }

    @Override
    protected void onEditListener(RecyclerItemEntity entity) {
        if(richBuilder.getOnEditListener() != null){
            richBuilder.getOnEditListener().invoke(entity);
        }
    }

    @Override
    protected void onItemClickListener(RecyclerItemEntity entity) {
        if(richBuilder.getOnItemClickListener() != null){
            richBuilder.getOnItemClickListener().invoke(entity);
        }
    }

    @Override
    protected void onItemSelectListener(RecyclerItemEntity entity, int postion) {
        if(richBuilder.getOnItemSelectListener() != null){
            richBuilder.getOnItemSelectListener().invoke(entity,postion);
        }
    }

    @Override
    protected void onItemMenuClickListener(RecyclerItemEntity entity) {
        if(richBuilder.getOnItemMenuClickListener() != null){
            richBuilder.getOnItemMenuClickListener().invoke(entity);
        }
    }
}
