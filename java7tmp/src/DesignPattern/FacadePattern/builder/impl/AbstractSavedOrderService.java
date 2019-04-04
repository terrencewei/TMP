package DesignPattern.FacadePattern.builder.impl;

import java.util.List;
import java.util.Map;

import DesignPattern.FacadePattern.beans.ActionEntity;
import DesignPattern.FacadePattern.beans.Link;
import DesignPattern.FacadePattern.beans.Resource;
import DesignPattern.FacadePattern.beans.ResourceState;
import DesignPattern.FacadePattern.builder.MicroService;

/**
 * Created by terrencewei on 2018/03/12.
 */
public abstract class AbstractSavedOrderService implements MicroService {

    @Override
    public ActionEntity assemble(Map pParam, ActionEntity pEntity) {
        if (pEntity == null) {
            pEntity = new ActionEntity<Resource>(new Resource());
        }
        Resource resource = (Resource) pEntity.getEntity();
        resource.setResourceState(buildResourceState(pParam, resource));
        resource.setLinks(buildLinks(pParam, resource));
        resource.setEmbeddedResource(buildEmbeddedResource(pParam, resource));
        return pEntity;
    }



    protected abstract ResourceState buildResourceState(Map pParam, Resource pResource);



    protected abstract List<Link> buildLinks(Map pParam, Resource pResource);



    protected abstract Resource buildEmbeddedResource(Map pParam, Resource pResource);

}
