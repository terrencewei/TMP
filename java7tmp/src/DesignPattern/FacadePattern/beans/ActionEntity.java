package DesignPattern.FacadePattern.beans;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by terrencewei on 2018/03/12.
 */
public class ActionEntity<T> {

    private T mEntity;



    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }



    public ActionEntity(T pEntity) {
        mEntity = pEntity;
    }



    public T getEntity() {
        return mEntity;
    }



    public void setEntity(T pEntity) {
        mEntity = pEntity;
    }
}
