package Util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Created by terrencewei on 2017/11/09.
 */
public class HoverUserExcel2BCCUserImportExcel {

    public static void main(String args[]) throws Exception {
        // read
        OPCPackage pkg = OPCPackage.open(new File("/home/terrencewei/Downloads/t/new_user_account.xlsx"));
        XSSFWorkbook excel = new XSSFWorkbook(pkg);
        Sheet sheet = excel.getSheet("Sheet1");

        List<User> users = new ArrayList<User>();
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            String legacy_account_id = getText(row, 4);
            if (legacy_account_id != null && legacy_account_id.length() > 0) {
                users.add(
                        new User(getText(row, 0), getText(row, 1), getText(row, 2), getText(row, 3), getText(row, 4)));
            }
        }

        pkg.close();

        // write
        HSSFWorkbook output = new HSSFWorkbook();
        Sheet outputSheet = output.createSheet("Sheet1");

        outputSheet.createRow(0).createCell(0).setCellValue("/atg/userprofiling/ProfileAdapterRepository:user");

        Row title = outputSheet.createRow(1);
        title.createCell(0).setCellValue("parentOrganization");
        title.createCell(1).setCellValue("email");
        title.createCell(2).setCellValue("firstName");
        title.createCell(3).setCellValue("lastName");
        title.createCell(4).setCellValue("locked");
        title.createCell(5).setCellValue("login");
        title.createCell(6).setCellValue("accounts");
        title.createCell(7).setCellValue("role");

        int contentRowIdx = 2;
        for (int i = contentRowIdx; i < users.size() + contentRowIdx; i++) {
            Row content = outputSheet.createRow(i);
            User user = users.get(i - contentRowIdx);
            content.createCell(0).setCellValue("hover-org-id");
            content.createCell(1).setCellValue(user.getEmail());
            content.createCell(2).setCellValue(user.getFirstname());
            content.createCell(3).setCellValue(user.getLastname());
            content.createCell(4).setCellValue("false");
            content.createCell(5).setCellValue(user.getEmail());
            // TODO: get account repo id by legacy account id
            content.createCell(6).setCellValue(user.getLegacyAccountId());
            content.createCell(7).setCellValue("site-user-role-id");
        }

        // test
        CellStyle style = output.createCellStyle();
        style = output.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Cell cell = output.createSheet("test").createRow(0).createCell(0);
        cell.setCellValue("test");
        cell.setCellStyle(style);
        // test
        try {
            FileOutputStream out = new FileOutputStream("/home/terrencewei/Downloads/t/testOutput.xls");
            output.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private static String getText(Row pRow, int cell) {
        Cell cell1 = pRow.getCell(cell);
        if (cell1 != null) {
            String str = cell1.toString();
            if (str.endsWith(".0")) {
                str = str.substring(0, str.length() - 2);
            }
            return str;
        }
        return null;
    }

    private static class User {
        private String firstname;
        private String lastname;
        private String email;
        private String account;
        private String legacyAccountId;



        public User(String pFirstname, String pLastname, String pEmail, String pAccount, String pLegacyAccountId) {
            firstname = pFirstname;
            lastname = pLastname;
            email = pEmail;
            account = pAccount;
            legacyAccountId = pLegacyAccountId;
        }



        public String getFirstname() {
            return firstname;
        }



        public void setFirstname(String pFirstname) {
            firstname = pFirstname;
        }



        public String getLastname() {
            return lastname;
        }



        public void setLastname(String pLastname) {
            lastname = pLastname;
        }



        public String getEmail() {
            return email;
        }



        public void setEmail(String pEmail) {
            email = pEmail;
        }



        public String getAccount() {
            return account;
        }



        public void setAccount(String pAccount) {
            account = pAccount;
        }



        public String getLegacyAccountId() {
            return legacyAccountId;
        }



        public void setLegacyAccountId(String pLegacyAccountId) {
            legacyAccountId = pLegacyAccountId;
        }
    }

}
