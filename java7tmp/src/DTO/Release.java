package DTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by terrencewei on 2017/11/09.
 */
public class Release extends BaseDTO {
    private String               name;
    private double               weeklyHours;
    private double               monHours;
    private double               tueHours;
    private double               wedHours;
    private double               thuHours;
    private double               friHours;
    private Map<String, Ticket>  mTickets      = new HashMap<>();
    private Map<String, Project> parentProject = new HashMap<>();



    public Release(String pName) {
        name = pName;
    }



    public Release(String pName, Ticket pTicket) {
        mTickets.put(pTicket.getKey(), pTicket);
        name = pName;
    }



    public Ticket getTicket(String pTicket) {
        Ticket ticket = mTickets.get(pTicket);
        return ticket != null ? ticket : new Ticket(pTicket);
    }



    public Ticket addTicket(Ticket pTicket) {
        mTickets.put(pTicket.getKey(), pTicket);
        pTicket.addParentRelease(this);
        return pTicket;
    }



    public void addParentProject(Project pProject) {
        parentProject.put(pProject.getName(), pProject);
    }



    public String getName() {
        return name;
    }



    public void setName(String pName) {
        name = pName;
    }



    public Map<String, Ticket> getTickets() {
        return mTickets;
    }



    public void setTickets(Map<String, Ticket> pTickets) {
        mTickets = pTickets;
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



    public Map<String, Project> getParentProject() {
        return parentProject;
    }



    public void setParentProject(Map<String, Project> pParentProject) {
        parentProject = pParentProject;
    }

}
