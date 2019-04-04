package DesignPattern.FacadePattern.facade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DesignPattern.FacadePattern.beans.ActionEntity;
import DesignPattern.FacadePattern.builder.MicroService;
import DesignPattern.FacadePattern.facade.ActionFacade;

/**
 * Created by terrencewei on 2018/03/12.
 */
public abstract class AbstractActionFacade implements ActionFacade {

    private List<String> mMicroServicePaths;
    private Map          mMicroServiceParam = new HashMap();



    /**
     * call each action executor to execute resource
     *
     * @return
     */
    @Override
    public ActionEntity execute(ActionEntity pInput) {
        ActionEntity entity = null;
        Map param = initServiceParam(pInput);
        List<MicroService> services = resolveServices(getMicroServicePaths());
        for (MicroService service : services) {
            entity = service.assemble(param, entity);
        }
        return entity;
    }



    /**
     * initialize data param used by services of this action via input action entity
     *
     */
    protected abstract Map initServiceParam(ActionEntity pInput);



    /**
     *
     * resolve services used by this action
     *
     * @return
     */
    protected abstract List<MicroService> resolveServices(List<String> pServicePaths);



    public List<String> getMicroServicePaths() {
        return mMicroServicePaths;
    }



    public void setMicroServicePaths(List<String> pMicroServicePaths) {
        mMicroServicePaths = pMicroServicePaths;
    }



    public Map getMicroServiceParam() {
        return mMicroServiceParam;
    }



    public void setMicroServiceParam(Map pMicroServiceParam) {
        mMicroServiceParam = pMicroServiceParam;
    }

}
