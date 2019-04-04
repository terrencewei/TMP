package Util;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by terrencewei on 2018/03/27.
 */
public class DyAdminRQLExcelParse3 {

    public static void main(String args[]) throws Exception {
        Map<String, Branch> branchMap = getBranchMap();
        List<Order> orderList = getOrderList();

        String outputFilePath = "/home/terrence/Downloads/final_output.xlsx";
        Workbook workbook = ExcelUtil.createWorkbook(outputFilePath);
        Sheet sheet1 = ExcelUtil.createSheet(workbook, "order");
        CellStyle style = workbook.createCellStyle();
        XSSFDataFormat format = (XSSFDataFormat) workbook.createDataFormat();
        style.setDataFormat(format.getFormat("TEXT"));

        List<String> branchTitles = getBranchTitleList();
        for (Order order : orderList) {
            Branch branch = branchMap.get(order.getDefaultBranchId());
            for (String branchTitle : branchTitles) {
                String method = branchTitle.toUpperCase().charAt(0) + branchTitle.substring(1, branchTitle.length());
                Object branchValue = branch.getClass().getMethod("get" + method).invoke(branch);
                order.getClass().getMethod("set" + method, String.class).invoke(order, branchValue);
            }
        }

        List<String> titles = new ArrayList<>();
        titles.addAll(getOrderTitleList());
        titles.addAll(branchTitles);
        for (int i = 0; i < orderList.size(); i++) {
            Row row = ExcelUtil.createRow(sheet1, i);
            Order order = orderList.get(i);
            for (int j = 0; j < titles.size(); j++) {
                String title = titles.get(j);
                String method = title.toUpperCase().charAt(0) + title.substring(1, title.length());
                Object value = order.getClass().getMethod("get" + method).invoke(order);
                if (value == null) {
                    value = "";
                }
                setCell(style, row, j, value + "");
            }
        }
        ExcelUtil.saveWorkbook(workbook, outputFilePath);
    }



    private static List<String> getBranchTitleList() throws Exception {
        OPCPackage pkg = OPCPackage.open(new File("/home/terrence/Downloads/branch_output.xlsx"));
        XSSFWorkbook excel = new XSSFWorkbook(pkg);
        Sheet sheet = excel.getSheet("Branch_BranchAddr");
        int columnTotal = 16;
        List<String> titles = new ArrayList<>();
        for (int currentColumn = 0; currentColumn < columnTotal; currentColumn++) {
            titles.add(getText(sheet.getRow(0), currentColumn));
        }
        return titles;
    }



    private static List<String> getOrderTitleList() throws Exception {
        OPCPackage pkg = OPCPackage.open(new File("/home/terrence/Downloads/order_output.xlsx"));
        XSSFWorkbook excel = new XSSFWorkbook(pkg);
        Sheet sheet = excel.getSheet("order");
        int columnTotal = 6;
        List<String> titles = new ArrayList<>();
        for (int currentColumn = 0; currentColumn < columnTotal; currentColumn++) {
            titles.add(getText(sheet.getRow(0), currentColumn));
        }
        return titles;
    }



    private static Map<String, Branch> getBranchMap() throws Exception {
        Map<String, Branch> branchMap = new HashMap<>();
        OPCPackage pkg = OPCPackage.open(new File("/home/terrence/Downloads/branch_output.xlsx"));
        XSSFWorkbook excel = new XSSFWorkbook(pkg);
        Sheet sheet = excel.getSheet("Branch_BranchAddr");
        int columnTotal = 16;
        Map<String, String> titleMap = new HashMap<>();
        for (int currentColumn = 0; currentColumn < columnTotal; currentColumn++) {
            titleMap.put(currentColumn + "", getText(sheet.getRow(0), currentColumn));
        }
        for (int currentRow = 1; currentRow <= sheet.getLastRowNum(); currentRow++) {
            Branch branch = new Branch();
            for (int currentColumn = 0; currentColumn < columnTotal; currentColumn++) {
                String value = getText(sheet.getRow(currentRow), currentColumn);
                String fieldName = titleMap.get(currentColumn + "");
                String fieldName2 = fieldName.toUpperCase().charAt(0) + fieldName.substring(1, fieldName.length());
                branch.getClass().getMethod("set" + fieldName2, String.class).invoke(branch, value);
            }
            if (branch.getId() != null) {
                branchMap.put(branch.getLegacyNumber(), branch);
            }
        }
        pkg.close();
        return branchMap;
    }



    public static List<Order> getOrderList() throws Exception {
        List<Order> orderList = new ArrayList<>();
        OPCPackage pkg = OPCPackage.open(new File("/home/terrence/Downloads/order_output.xlsx"));
        XSSFWorkbook excel = new XSSFWorkbook(pkg);
        Sheet sheet = excel.getSheet("order");
        int columnTotal = 6;
        Map<String, String> titleMap = new HashMap<>();
        for (int currentColumn = 0; currentColumn < columnTotal; currentColumn++) {
            titleMap.put(currentColumn + "", getText(sheet.getRow(0), currentColumn));
        }
        for (int currentRow = 1; currentRow <= sheet.getLastRowNum(); currentRow++) {
            Order order = new Order();
            for (int currentColumn = 0; currentColumn < columnTotal; currentColumn++) {
                String value = getText(sheet.getRow(currentRow), currentColumn);
                String fieldName = titleMap.get(currentColumn + "");
                String fieldName2 = fieldName.toUpperCase().charAt(0) + fieldName.substring(1, fieldName.length());
                order.getClass().getMethod("set" + fieldName2, String.class).invoke(order, value);
            }
            if (order.getAtgId() != null) {
                orderList.add(order);
            }
        }
        pkg.close();
        return orderList;
    }



    private static void setCell(CellStyle pStyle, Row pRow, int column, String cellText) {
        Cell cell = ExcelUtil.createCell(pRow, column);
        cell.setCellStyle(pStyle);
        cell.setCellValue(cellText);
    }



    private static String getText(int rowNm, Sheet sheet, int i, int rqlPropertyRowCount, int cell) {
        Row row = sheet.getRow((i * rqlPropertyRowCount) - 1 + rowNm);
        return getText(row, cell);
    }



    private static String getText(Row row, int cell) {
        String text = "";
        try {
            Cell cell1 = row.getCell(cell);
            switch (cell1.getCellType()) {
            case XSSFCell.CELL_TYPE_STRING:
                text = cell1.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                text = cell1.getCellFormula();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                DecimalFormat df = new DecimalFormat("0");
                text = df.format(cell1.getNumericCellValue());
                break;
            default:
                text = cell1.toString();
            }
        } catch (Exception pE) {
        }
        if (text != null && text.length() > 0) {
            text = text.replaceAll("●", " ");
            if (text.endsWith(".0")) {
                text = text.substring(0, text.length() - 2);
            }
            if (text.equals("null")) {
                text = "";
            }
            return text;
        }
        return null;
    }



    public static class Branch {
        private String id;
        private String descriptor;
        private String description;
        private String email;
        private String legacyNumber;
        private String displayName;
        private String phone;
        private String region;
        private String address;
        private String descriptor2;
        private String city;
        private String state;
        private String address2;
        private String address1;
        private String postCode;
        private String address3;



        public String getId() {
            return id;
        }



        public void setId(String pId) {
            id = pId;
        }



        public String getDescriptor() {
            return descriptor;
        }



        public void setDescriptor(String pDescriptor) {
            descriptor = pDescriptor;
        }



        public String getCity() {
            return city;
        }



        public void setCity(String pCity) {
            city = pCity;
        }



        public String getState() {
            return state;
        }



        public void setState(String pState) {
            state = pState;
        }



        public String getAddress2() {
            return address2;
        }



        public void setAddress2(String pAddress2) {
            address2 = pAddress2;
        }



        public String getAddress1() {
            return address1;
        }



        public void setAddress1(String pAddress1) {
            address1 = pAddress1;
        }



        public String getPostCode() {
            return postCode;
        }



        public void setPostCode(String pPostCode) {
            postCode = pPostCode;
        }



        public String getAddress3() {
            return address3;
        }



        public void setAddress3(String pAddress3) {
            address3 = pAddress3;
        }



        public String getDescription() {
            return description;
        }



        public void setDescription(String pDescription) {
            description = pDescription;
        }



        public String getEmail() {
            return email;
        }



        public void setEmail(String pEmail) {
            email = pEmail;
        }



        public String getLegacyNumber() {
            return legacyNumber;
        }



        public void setLegacyNumber(String pLegacyNumber) {
            legacyNumber = pLegacyNumber;
        }



        public String getDisplayName() {
            return displayName;
        }



        public void setDisplayName(String pDisplayName) {
            displayName = pDisplayName;
        }



        public String getPhone() {
            return phone;
        }



        public void setPhone(String pPhone) {
            phone = pPhone;
        }



        public String getRegion() {
            return region;
        }



        public void setRegion(String pRegion) {
            region = pRegion;
        }



        public String getAddress() {
            return address;
        }



        public void setAddress(String pAddress) {
            address = pAddress;
        }



        public String getDescriptor2() {
            return descriptor2;
        }



        public void setDescriptor2(String pDescriptor2) {
            descriptor2 = pDescriptor2;
        }



        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }

    }

    public static class Order {
        private String atgId;
        private String mincronId;
        private String jobName;
        private String jobNumber;
        private String defaultBranchId;
        private String shippingGroups;

        private String id;
        private String descriptor;
        private String description;
        private String email;
        private String legacyNumber;
        private String displayName;
        private String phone;
        private String region;
        private String address;
        private String descriptor2;
        private String city;
        private String state;
        private String address2;
        private String address1;
        private String postCode;
        private String address3;



        public String getAtgId() {
            return atgId;
        }



        public void setAtgId(String pAtgId) {
            atgId = pAtgId;
        }



        public String getMincronId() {
            return mincronId;
        }



        public void setMincronId(String pMincronId) {
            mincronId = pMincronId;
        }



        public String getJobName() {
            return jobName;
        }



        public void setJobName(String pJobName) {
            jobName = pJobName;
        }



        public String getJobNumber() {
            return jobNumber;
        }



        public void setJobNumber(String pJobNumber) {
            jobNumber = pJobNumber;
        }



        public String getDefaultBranchId() {
            return defaultBranchId;
        }



        public void setDefaultBranchId(String pDefaultBranchId) {
            defaultBranchId = pDefaultBranchId;
        }



        public String getShippingGroups() {
            return shippingGroups;
        }



        public void setShippingGroups(String pShippingGroups) {
            shippingGroups = pShippingGroups;
        }



        public String getId() {
            return id;
        }



        public void setId(String pId) {
            id = pId;
        }



        public String getDescriptor() {
            return descriptor;
        }



        public void setDescriptor(String pDescriptor) {
            descriptor = pDescriptor;
        }



        public String getDescription() {
            return description;
        }



        public void setDescription(String pDescription) {
            description = pDescription;
        }



        public String getEmail() {
            return email;
        }



        public void setEmail(String pEmail) {
            email = pEmail;
        }



        public String getLegacyNumber() {
            return legacyNumber;
        }



        public void setLegacyNumber(String pLegacyNumber) {
            legacyNumber = pLegacyNumber;
        }



        public String getDisplayName() {
            return displayName;
        }



        public void setDisplayName(String pDisplayName) {
            displayName = pDisplayName;
        }



        public String getPhone() {
            return phone;
        }



        public void setPhone(String pPhone) {
            phone = pPhone;
        }



        public String getRegion() {
            return region;
        }



        public void setRegion(String pRegion) {
            region = pRegion;
        }



        public String getAddress() {
            return address;
        }



        public void setAddress(String pAddress) {
            address = pAddress;
        }



        public String getDescriptor2() {
            return descriptor2;
        }



        public void setDescriptor2(String pDescriptor2) {
            descriptor2 = pDescriptor2;
        }



        public String getCity() {
            return city;
        }



        public void setCity(String pCity) {
            city = pCity;
        }



        public String getState() {
            return state;
        }



        public void setState(String pState) {
            state = pState;
        }



        public String getAddress2() {
            return address2;
        }



        public void setAddress2(String pAddress2) {
            address2 = pAddress2;
        }



        public String getAddress1() {
            return address1;
        }



        public void setAddress1(String pAddress1) {
            address1 = pAddress1;
        }



        public String getPostCode() {
            return postCode;
        }



        public void setPostCode(String pPostCode) {
            postCode = pPostCode;
        }



        public String getAddress3() {
            return address3;
        }



        public void setAddress3(String pAddress3) {
            address3 = pAddress3;
        }



        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }

    }

}

