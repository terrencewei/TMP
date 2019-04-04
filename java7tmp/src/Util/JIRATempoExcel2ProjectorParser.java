package Util;

import java.io.File;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import DTO.Project;
import DTO.Projector;
import DTO.Release;
import DTO.Ticket;

/**
 * Created by terrencewei on 2017/11/09.
 */
public class JIRATempoExcel2ProjectorParser {

    private static int ReleaseOrTicketTitleColOffset = 1;
    private static int WeeklyColOffset               = 5;
    private static int MonColOffset                  = 9;
    private static int TueColOffset                  = 10;
    private static int WedColOffset                  = 11;
    private static int ThuColOffset                  = 12;
    private static int FriColOffset                  = 13;



    public static void main(String args[]) throws Exception {
        // XSSFWorkbook, File
        OPCPackage pkg = OPCPackage.open(new File("/home/terrence/Desktop/Beacon工作.xlsx"));
        XSSFWorkbook excel = new XSSFWorkbook(pkg);
        Sheet sheet = excel.getSheet("JIRA-Tempo");

        // calc start row,col
        int rowStartNum = 0;
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                if ("Daily hours total:".equals(getText(row, j))
                        && "Weekly hours total:".equals(getText(sheet, i + 1, j))
                        && "Planned hours total:".equals(getText(sheet, i + 2, j))) {
                    rowStartNum = i + 2;
                    break;
                }
            }
        }
        // System.out.println("rowStartNum:" + rowStartNum);

        // calc end row,col
        int rowEndNum = rowStartNum;
        for (int i = rowStartNum; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                if ("Total".equals(getText(row, j)) && "Worked".equals(getText(sheet, i + 1, j))
                        && "Planned".equals(getText(sheet, i + 2, j)) && "Required".equals(getText(sheet, i + 3, j))) {
                    rowEndNum = i;
                    break;
                }
            }
        }
        // System.out.println("rowEndNum:" + rowEndNum);

        Projector projector = new Projector();
        // begin parse logging hours area
        String projectName = "BC";
        String ticketKeyPrefix = "BC-";
        Pattern p = Pattern.compile("^" + ticketKeyPrefix + "\\d*$");
        for (int currentRowNum = rowStartNum + 1; currentRowNum < rowEndNum; currentRowNum++) {
            Row currentRow = sheet.getRow(currentRowNum);
            for (int currentColNum = 0; currentColNum < currentRow.getLastCellNum(); currentColNum++) {
                String currentText = getText(currentRow, currentColNum);
                if (StringUtils.isBlank(currentText)) {
                    continue;
                }
                if (currentText.equals(projectName)) {
                    // this is a project and release row
                    Project project = projector.getProject(projectName);
                    Release release = project
                            .getRelease(getText(currentRow, currentColNum + ReleaseOrTicketTitleColOffset));

                    release.setWeeklyHours(getNum(currentRow, currentColNum + WeeklyColOffset));
                    release.setMonHours(getNum(currentRow, currentColNum + MonColOffset));
                    release.setTueHours(getNum(currentRow, currentColNum + TueColOffset));
                    release.setWedHours(getNum(currentRow, currentColNum + WedColOffset));
                    release.setThuHours(getNum(currentRow, currentColNum + ThuColOffset));
                    release.setFriHours(getNum(currentRow, currentColNum + FriColOffset));

                    projector.addProject(project).addRelease(release);

                } else if (p.matcher(currentText).matches()) {
                    // this is a ticket row
                    int r = currentRowNum;
                    Row projectRow = null;
                    String pjName = "";
                    while (!pjName.equals(projectName)) {
                        projectRow = sheet.getRow(--r);
                        pjName = getText(projectRow, currentColNum);
                    }
                    Project project = projector.getProject(pjName);
                    Release release = project
                            .getRelease(getText(projectRow, currentColNum + ReleaseOrTicketTitleColOffset));

                    Ticket ticket = release.getTicket(currentText);
                    ticket.setTitle(getText(currentRow, currentColNum + ReleaseOrTicketTitleColOffset));

                    ticket.setWeeklyHours(getNum(currentRow, currentColNum + WeeklyColOffset));
                    ticket.setMonHours(getNum(currentRow, currentColNum + MonColOffset));
                    ticket.setTueHours(getNum(currentRow, currentColNum + TueColOffset));
                    ticket.setWedHours(getNum(currentRow, currentColNum + WedColOffset));
                    ticket.setThuHours(getNum(currentRow, currentColNum + ThuColOffset));
                    ticket.setFriHours(getNum(currentRow, currentColNum + FriColOffset));

                    projector.addProject(project).addRelease(release).addTicket(ticket);
                }
            }
        }

        Projector monProj = new Projector();
        StringBuilder mon = new StringBuilder();
        StringBuilder tue = new StringBuilder();
        StringBuilder wed = new StringBuilder();
        StringBuilder thu = new StringBuilder();
        StringBuilder fri = new StringBuilder();

        for (Map.Entry<String, Project> projectEntry : projector.getProjects().entrySet()) {
            Project project = projectEntry.getValue();
            for (Map.Entry<String, Release> releaseEntry : project.getReleases().entrySet()) {
                Release release = releaseEntry.getValue();
                for (Map.Entry<String, Ticket> ticketEntry : release.getTickets().entrySet()) {
                    Ticket ticket = ticketEntry.getValue();

                    buildProjectorNotesPrefix(release.getMonHours() > 0d, mon, project, release);
                    buildProjectorNotesPrefix(release.getTueHours() > 0d, tue, project, release);
                    buildProjectorNotesPrefix(release.getWedHours() > 0d, wed, project, release);
                    buildProjectorNotesPrefix(release.getThuHours() > 0d, thu, project, release);
                    buildProjectorNotesPrefix(release.getFriHours() > 0d, fri, project, release);
                }
            }
        }

        for (Map.Entry<String, Project> projectEntry : projector.getProjects().entrySet()) {
            Project project = projectEntry.getValue();
            for (Map.Entry<String, Release> releaseEntry : project.getReleases().entrySet()) {
                Release release = releaseEntry.getValue();
                for (Map.Entry<String, Ticket> ticketEntry : release.getTickets().entrySet()) {
                    Ticket ticket = ticketEntry.getValue();

                    buildProjectorNotes(ticket.getMonHours() > 0d, mon, ticket);
                    buildProjectorNotes(ticket.getTueHours() > 0d, tue, ticket);
                    buildProjectorNotes(ticket.getWedHours() > 0d, wed, ticket);
                    buildProjectorNotes(ticket.getThuHours() > 0d, thu, ticket);
                    buildProjectorNotes(ticket.getFriHours() > 0d, fri, ticket);
                }
            }
        }

        System.out.println(printNotes(mon));
        System.out.println(printNotes(tue));
        System.out.println(printNotes(wed));
        System.out.println(printNotes(thu));
        System.out.println(printNotes(fri));

        pkg.close();
    }



    private static String printNotes(StringBuilder pNote) {
        String notes = pNote.toString();
        return notes.length() > 0 ? notes.substring(2) : notes;
    }



    private static void buildProjectorNotes(boolean hadWorkThisDay, StringBuilder pNote, Ticket pTicket) {
        if (hadWorkThisDay) {
            pNote.append(", ").append(pTicket.getKey()).append(" ").append(pTicket.getTitle());
        }
    }



    private static void buildProjectorNotesPrefix(boolean hadWorkThisRelease, StringBuilder pNote, Project pProject,
            Release pRelease) {
        if (hadWorkThisRelease && !pNote.toString().contains("release[" + pRelease.getName() + "]")) {
            if (!pNote.toString().contains("[" + convertProjectName(pProject.getName()) + "] Project")) {
                pNote.append(", Working on [").append(convertProjectName(pProject.getName())).append("] Project");
            }
            pNote.append(", release[").append(pRelease.getName()).append("]");
        }
    }



    private static String convertProjectName(String pName) {
        switch (pName) {
        case "BC":
            return "Beacon";
        }
        return null;
    }



    private static String getText(Row pRow, int cell) {
        return pRow.getCell(cell).toString();
    }



    private static String getText(Sheet pSheet, int row, int cell) {
        return pSheet.getRow(row).getCell(cell).toString();
    }



    private static double getNum(Row pRow, int cell) {
        try {
            return Double.valueOf(getText(pRow, cell));
        } catch (Exception pE) {
        }
        return 0;
    }



    private static double getNum(Sheet pSheet, int row, int cell) {
        try {
            return Double.valueOf(getText(pSheet, row, cell));
        } catch (Exception pE) {
        }
        return 0;
    }

}
