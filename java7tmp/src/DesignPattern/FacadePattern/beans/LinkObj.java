package DesignPattern.FacadePattern.beans;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by terrencewei on 2018/03/12.
 */
public class LinkObj {
    private String mHref;
    private String mMethod;



    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }



    public String getHref() {
        return mHref;
    }



    public void setHref(String pHref) {
        mHref = pHref;
    }



    public String getMethod() {
        return mMethod;
    }



    public void setMethod(String pMethod) {
        mMethod = pMethod;
    }
}
