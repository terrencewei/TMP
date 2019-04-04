package DTO;

import DTO.BaseDTO;
import DTO.Release;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by terrencewei on 2017/11/09.
 */
public class Ticket extends BaseDTO {

    private String               key;
    private String               title;
    private double               weeklyHours;
    private double               monHours;
    private double               tueHours;
    private double               wedHours;
    private double               thuHours;
    private double               friHours;
    private Map<String, Release> parentRelease = new HashMap<>();



    public Ticket(String pKey) {
        key = pKey;
    }



    public Ticket(String pKey, String pTitle, int pHours) {
        key = pKey;
        title = pTitle;
        weeklyHours = pHours;
    }



    public void addParentRelease(Release pRelease) {
        parentRelease.put(pRelease.getName(), pRelease);
    }



    public String getKey() {
        return key;
    }



    public void setKey(String pKey) {
        key = pKey;
    }



    public String getTitle() {
        return title;
    }



    public void setTitle(String pTitle) {
        title = pTitle;
    }



    public double getWeeklyHours() {
        return weeklyHours;
    }



    public void setWeeklyHours(double pWeeklyHours) {
        weeklyHours = pWeeklyHours;
    }



    public double getMonHours() {
        return monHours;
    }



    public void setMonHours(double pMonHours) {
        monHours = pMonHours;
    }



    public double getTueHours() {
        return tueHours;
    }



    public void setTueHours(double pTueHours) {
        tueHours = pTueHours;
    }



    public double getWedHours() {
        return wedHours;
    }



    public void setWedHours(double pWedHours) {
        wedHours = pWedHours;
    }



    public double getThuHours() {
        return thuHours;
    }



    public void setThuHours(double pThuHours) {
        thuHours = pThuHours;
    }



    public double getFriHours() {
        return friHours;
    }



    public void setFriHours(double pFriHours) {
        friHours = pFriHours;
    }



    public Map<String, Release> getParentRelease() {
        return parentRelease;
    }



    public void setParentRelease(Map<String, Release> pParentRelease) {
        parentRelease = pParentRelease;
    }
}
