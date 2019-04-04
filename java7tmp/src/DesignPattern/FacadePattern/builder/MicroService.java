package DesignPattern.FacadePattern.builder;

import java.util.Map;

import DesignPattern.FacadePattern.beans.ActionEntity;

/**
 * Created by terrencewei on 2018/03/12.
 */
public interface MicroService {

    /**
     * assemlbe action entity of previous micron service according to input param
     * 
     * @param pParam
     * @param pEntity
     * @return
     */
    public ActionEntity assemble(Map pParam, ActionEntity pEntity);

}
