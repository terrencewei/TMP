package DesignPattern.FacadePattern.beans;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by terrencewei on 2018/03/12.
 */
public class Link {
    private LinkObj mSelf;



    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }



    public LinkObj getSelf() {
        return mSelf;
    }



    public void setSelf(LinkObj pSelf) {
        mSelf = pSelf;
    }
}
