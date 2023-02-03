package com.star.starlight.ui.view.commons;

import android.content.Context;
import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;

import com.star.starlight.ui.R;
import com.star.starlight.ui.BR;
import com.star.starlight.ui.databinding.RecyclerViewitemLineHorizontalBinding;
import com.star.starlight.ui.view.entity.IRecyclerItem;
import com.star.starlight.ui.view.entity.richadapter.RichLineHorizontal;
import com.star.starlight.ui.view.presenter.IRecyclerPresenter;
import com.star.starlight.ui.view.presenter.IRichPresenter;
import com.starlight.dot.framework.commons.EastAdapter;
import com.starlight.dot.framework.utils.SLog;

import java.util.List;
import java.util.Map;

public abstract class RichBaseAdapter<I extends IRecyclerItem> extends EastAdapter<I> {
    private static final String TAG = "UI_RichBaseAdapter==>";

    public RichBaseAdapter(Context context){
        super(context);
    }

    public RichBaseAdapter(Context context, List<I> dataList){
        super(context,dataList);
    }

    @Override
    protected int getViewItemType(int position){
        List<I> dataList = getDataList();
        int showType = dataList.get(position).recyclerItemType();
        SLog.INSTANCE.longD(TAG,"showType=>"+showType);
        switch (showType){
            case RecyclerItemType.ItemType.TYPE_LINE_VERTICLE:
                return R.layout.recycler_viewitem_line_vertical;
            case RecyclerItemType.ItemType.TYPE_LINE_HORIZONTAL:
                return R.layout.recycler_viewitem_line_horizontal;
            case RecyclerItemType.ItemType.TYPE_CUSTOM:
                return dataList.get(position).customLayoutId();
            default:
        }
        throw new IllegalArgumentException("unknow item view type");
    }

    @Override
    protected void bindItem(Map map) {
        if(map != null){
            map.put(R.layout.recycler_viewitem_line_horizontal,BR.lineDate);
            map.put(R.layout.recycler_viewitem_line_vertical,BR.lineDate);
        }
    }

    @Override
    public void setBean(ViewDataBinding dataBinding, int varid, int position) throws IllegalAccessException {
        super.setBean(dataBinding, varid, position);
        dataBinding.setVariable(BR.presenter,presenter);
    }

    protected abstract void onItemClickListener(I entity);

    protected abstract void onItemSelectListener(I entity,int postion);

    protected abstract void onItemMenuClickListener(I entity);

    protected abstract void onEditListener(I entity);

    @Override
    public void setView(ViewDataBinding dataBinding, int viewType, int position) {
        super.setView(dataBinding, viewType, position);
        I e = getDataList().get(position);
        if (dataBinding instanceof RecyclerViewitemLineHorizontalBinding &&
               e instanceof RichLineHorizontal && viewType == R.layout.recycler_viewitem_line_horizontal) {
            setLineHorizontalTheme(((RecyclerViewitemLineHorizontalBinding)dataBinding),((RichLineHorizontal)e));
        }
    }

    /**
     * 设置横向占位的分割线布局样式
     * create by Eastevil at 2022/11/10 15:35
     * @author Eastevil
     * @param dataBinding
     *      布局DataBinding
     * @param richLineHorizontal
     *      分割线类型数据
     * @return
     *      void
     */
    public void setLineHorizontalTheme(RecyclerViewitemLineHorizontalBinding dataBinding,
                                        RichLineHorizontal richLineHorizontal){
        int resId = richLineHorizontal.getLineRes();
        if(resId != 0){
            try {
                dataBinding.getRoot().setBackgroundResource(resId);
                ViewGroup.LayoutParams p = dataBinding.getRoot().getLayoutParams();
                if(p != null){
                    p.height = richLineHorizontal.getLineHeight();
                }else{
                    p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            richLineHorizontal.getLineHeight());
                }
                dataBinding.getRoot().setLayoutParams(p);
            }catch (Exception e){
                SLog.INSTANCE.longW(TAG,"setLineHorizontalTheme set res error");
            }
        }
    }

    private IRichPresenter<I> presenter = new IRichPresenter<I>() {
        @Override
        public void onItemClickListener(I entity, int position) {
            RichBaseAdapter.this.onItemSelectListener(entity,position);
        }

        @Override
        public void onItemSelectListener(I entity) {
            RichBaseAdapter.this.onItemClickListener(entity);
        }

        @Override
        public void onItemMenuClickListener(I entity) {
            RichBaseAdapter.this.onItemMenuClickListener(entity);
        }

        @Override
        public void onEditListener(I entity) {
            RichBaseAdapter.this.onEditListener(entity);
        }
    };
}
