package DesignPattern.FacadePattern.beans;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by terrencewei on 2018/03/12.
 */
public class ResourceState {
    private String mMessageCode;
    private String mMessage;



    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }



    public String getMessageCode() {
        return mMessageCode;
    }



    public void setMessageCode(String pMessageCode) {
        mMessageCode = pMessageCode;
    }



    public String getMessage() {
        return mMessage;
    }



    public void setMessage(String pMessage) {
        mMessage = pMessage;
    }
}
