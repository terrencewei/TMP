package DesignPattern.FacadePattern.facade.impl;

import static DesignPattern.FacadePattern.constant.MicroServiceParam.KEY_ORDER_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import DesignPattern.FacadePattern.beans.ActionEntity;
import DesignPattern.FacadePattern.builder.MicroService;
import DesignPattern.FacadePattern.builder.impl.CreateSavedOrderService;

/**
 * Created by terrencewei on 2018/03/12.
 */
public class SaveOrderAction extends AbstractActionFacade {

    @Override
    public Map initServiceParam(ActionEntity pInput) {
        Object entity = pInput.getEntity();
        Map param = getMicroServiceParam();
        param.put(KEY_ORDER_NAME, (String) entity);
        return param;
    }



    @Override
    public List<MicroService> resolveServices(List<String> pServicePaths) {
        List<MicroService> builders = new ArrayList<>();
        builders.add(new CreateSavedOrderService());
        return builders;
    }
}
