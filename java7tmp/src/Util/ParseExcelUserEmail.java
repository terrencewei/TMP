package Util;

import java.io.File;
import java.util.*;
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
public class ParseExcelUserEmail {

    private static final Pattern mPattern = Pattern.compile(
            "^(([A-Za-z0-9]+_+)|([A-Za-z0-9]+\\-+)|([A-Za-z0-9]+\\.+)|([A-Za-z0-9]+\\++))*[A-Za-z0-9]+@((\\w+\\-+)|(\\w+\\.))*\\w{1,63}\\.[a-zA-Z]{2,6}$");



    public static void main(String args[]) throws Exception {
        // XSSFWorkbook, File
        OPCPackage pkg = OPCPackage.open(new File("/home/terrencewei/Downloads/t/email.xlsx"));
        XSSFWorkbook excel = new XSSFWorkbook(pkg);
        Set<Integer> invalidRows = new HashSet<>();
        List<String> invalidProfileIds = new ArrayList<>();
        List<String> invalidEmails = new ArrayList<>();
        List<String> invalidLogins = new ArrayList<>();

        Sheet sheet = excel.getSheet("email");
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row.getLastCellNum() != 1) {
                invalidRows.add(i);
                continue;
            }
            String text = getText(row, 0);
            if (text != "" && !mPattern.matcher(text.substring(1, text.length() - 1)).matches()) {
                invalidRows.add(i);
            }
        }

        sheet = excel.getSheet("login");
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row.getLastCellNum() != 1) {
                invalidRows.add(i);
                continue;
            }
            String text = getText(row, 0);
            if (text != "" && !mPattern.matcher(text.substring(1, text.length() - 1)).matches()) {
                invalidRows.add(i);
            }
        }

        sheet = excel.getSheet("id");
        for (Integer invalidRow : invalidRows) {
            Row row = sheet.getRow(invalidRow);
            String profileId = getText(row, 0);
            profileId = profileId.substring(1,profileId.length()-1);
            invalidProfileIds.add(profileId);
        }

        sheet = excel.getSheet("email");
        for (Integer invalidRow : invalidRows) {
            Row row = sheet.getRow(invalidRow);
            for(int i=0;i<row.getLastCellNum();i++) {

            }
        }

        sheet = excel.getSheet("login");
        for (Integer invalidRow : invalidRows) {
            Row row = sheet.getRow(invalidRow);
            String profileId = getText(row, 0);
            profileId = profileId.substring(1,profileId.length()-1);
            invalidProfileIds.add(profileId);
        }



//        System.out.println("Invalid Profile id:["+profileId+"]");

        pkg.close();
    }



    private static String getText(Row pRow, int cell) {
        try {
            return pRow.getCell(cell).toString();
        } catch (Exception pE) {
        }
        return "";
    }

}
