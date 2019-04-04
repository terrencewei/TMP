package DesignPattern.FacadePattern.builder.impl;

import static DesignPattern.FacadePattern.constant.MicroServiceParam.KEY_ORDER_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import DesignPattern.FacadePattern.beans.Link;
import DesignPattern.FacadePattern.beans.Resource;
import DesignPattern.FacadePattern.beans.ResourceState;

/**
 * Created by terrencewei on 2018/03/12.
 */
public class EmailNotificationService extends AbstractSavedOrderService {
    @Override
    protected ResourceState buildResourceState(Map pParam, Resource pResource) {
        System.out.println("EmailNotificationService buildResourceState for:" + pParam.get(KEY_ORDER_NAME));
        return new ResourceState();
    }



    @Override
    protected List<Link> buildLinks(Map pParam, Resource pResource) {
        System.out.println("EmailNotificationService buildLinks for:" + pParam.get(KEY_ORDER_NAME));
        return new ArrayList<Link>();
    }



    @Override
    protected Resource buildEmbeddedResource(Map pParam, Resource pResource) {
        System.out.println("EmailNotificationService buildEmbeddedResource for:" + pParam.get(KEY_ORDER_NAME));
        return new Resource();
    }
}
