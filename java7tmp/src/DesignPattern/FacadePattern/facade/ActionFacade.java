package DesignPattern.FacadePattern.facade;

import DesignPattern.FacadePattern.beans.ActionEntity;

/**
 * Created by terrencewei on 2018/03/12.
 */
public interface ActionFacade {

    /**
     * execute and get action result resource by each services of this action
     *
     * @return
     */
    ActionEntity execute(ActionEntity pInput);

}
