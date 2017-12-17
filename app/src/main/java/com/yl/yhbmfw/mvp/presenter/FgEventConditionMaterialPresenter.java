package com.yl.yhbmfw.mvp.presenter;


import android.os.Bundle;

import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.bean.EventTypeItem;
import com.yl.yhbmfw.mvp.contract.FgEventConditionMaterialContract;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Yang Shihao
 */
public class FgEventConditionMaterialPresenter extends FgEventConditionMaterialContract.Presenter {

    public FgEventConditionMaterialPresenter(FgEventConditionMaterialContract.View view) {
        super(view);
    }

    @Override
    public void getEventMaterial(Bundle bundle) {
        if(bundle == null){
            return;
        }
       EventTypeItem typeItem = (EventTypeItem) bundle.getSerializable(Constant.KEY_BEAN);
        if(typeItem== null){
            return;
        }

        List<EventTypeItem.EventMaterial> materialList = typeItem.getMaterial();
        if(materialList== null || materialList.size() == 0){
            return;
        }
        List<EventTypeItem.EventMaterial> list = new ArrayList<>();
        for(EventTypeItem.EventMaterial material: materialList){
            if(material.getType().equals("card") || material.getType().equals("number"))
            list.add(material);
        }
        if(list== null || list.size() == 0){
            return;
        }
        mDataList.clear();
        setDataList(list);
    }
}
