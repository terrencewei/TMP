package DesignPattern.FacadePattern;

import DesignPattern.FacadePattern.beans.ActionEntity;
import DesignPattern.FacadePattern.facade.impl.ApproveOrderAction;
import DesignPattern.FacadePattern.facade.impl.SaveOrderAction;

/**
 * Created by terrencewei on 2018/03/12.
 */
public class TestSavedOrderRestService {
    public static void main(String[] args) {
        ActionEntity in = null;
        ActionEntity out = null;

        in = new ActionEntity<String>("Order001");
        System.out.println("Begin save order:" + in);
        out = new SaveOrderAction().execute(in);
        System.out.println("End save order:" + out);

        System.out.println("--------------------------");

        in = new ActionEntity<String>("Order002");
        System.out.println("Begin approve order:" + in);
        out = new ApproveOrderAction().execute(in);
        System.out.println("End approve order:" + out);
    }
}
