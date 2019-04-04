package DTO;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by terrencewei on 2017/06/29.
 */
public class A {
    private String aa;
    private int    bb;



    public A() {
    }



    public A(String pAa, int pBb) {
        aa = pAa;
        bb = pBb;
    }



    public String getAa() {
        return aa;
    }



    public void setAa(String pAa) {
        aa = pAa;
    }



    public int getBb() {
        return bb;
    }



    public void setBb(int pBb) {
        bb = pBb;
    }
}
