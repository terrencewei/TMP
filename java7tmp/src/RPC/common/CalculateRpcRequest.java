package RPC.common;

import java.io.Serializable;

/**
 * Created by terrence on 2018/12/04.
 */
public class CalculateRpcRequest implements Serializable {
    private String mMethod;
    private int    mB;
    private int    mA;



    public CalculateRpcRequest(String pMethod, int pB, int pA) {
        mMethod = pMethod;
        mB = pB;
        mA = pA;
    }



    @Override
    public String toString() {
        //return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        return String.format("CalculateRpcRequest (mA=%s, mB=%s, mMethod=%s)", this.mA, this.mB, this.mMethod);
    }



    public String getMethod() {
        return mMethod;
    }



    public void setMethod(String pMethod) {
        mMethod = pMethod;
    }



    public int getB() {
        return mB;
    }



    public void setB(int pB) {
        mB = pB;
    }



    public int getA() {
        return mA;
    }



    public void setA(int pA) {
        mA = pA;
    }
}