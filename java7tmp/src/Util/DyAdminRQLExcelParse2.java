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
public class DyAdminRQLExcelParse2 {

    public static void main(String args[]) throws Exception {
        // XSSFWorkbook, File
        OPCPackage pkg = OPCPackage.open(new File("/home/terrence/Downloads/INPUT2.xlsx"));
        XSSFWorkbook excel = new XSSFWorkbook(pkg);
        Sheet sheet = excel.getSheet("Sheet1");

        List<Shipping> shippings = new ArrayList<>();
        int rqlPropertyRowCount = 25;
        Map<String, String> propNames = new HashMap<String, String>();
        for (int i = 0; i < rqlPropertyRowCount; i++) {
            propNames.put(i + "", getText(sheet.getRow(i), 0));
        }
        int itemArrayCount = sheet.getLastRowNum() / rqlPropertyRowCount;
        for (int i = 0; i < itemArrayCount + 1; i++) {
            for (int k = 1; k <= 11; k++) {
                Shipping shipping = new Shipping();
                for (int m = 0; m < rqlPropertyRowCount; m++) {
                    String value = getText(m + 1, sheet, i, rqlPropertyRowCount, k);
                    String prop = propNames.get(m + "");
                    String prop2 = prop.toUpperCase().charAt(0) + prop.substring(1, prop.length());
                    shipping.getClass().getMethod("set" + prop2, String.class).invoke(shipping, value);
                }
                //                String setId = getText(1, sheet, i, rqlPropertyRowCount, k);
                //                if (setId != null) {
                //                    shipping.setId(setId);
                //                }
                //                String setDescriptor = getText(2, sheet, i, rqlPropertyRowCount, k);
                //                if (setId != null) {
                //                    shipping.setDescriptor(setDescriptor);
                //                }
                //                String setPostalCode = getText(3, sheet, i, rqlPropertyRowCount, k);
                //                if (setPostalCode != null) {
                //                    shipping.setPostalCode(setPostalCode);
                //                }
                //                String setDescription = getText(4, sheet, i, rqlPropertyRowCount, k);
                //                if (setId != null) {
                //                    shipping.setDescription(setDescription);
                //                }
                //                String setDescription = getText(4, sheet, i, rqlPropertyRowCount, k);
                //                if (setId != null) {
                //                    shipping.setCity(setDescription);
                //                }
                // add to shippings
                if (shipping.getId() != null) {
                    shippings.add(shipping);
                }
            }
        }

        pkg.close();

        //        for (Shipping order : shippings) {
        //            System.out.println(order);
        //        }
        //        System.out.println("total order count:" + shippings.size());

        String outputFilePath = "/home/terrence/Downloads/order2.xlsx";
        Workbook workbook = ExcelUtil.createWorkbook(outputFilePath);
        Sheet sheet1 = ExcelUtil.createSheet(workbook, "shipping");
        CellStyle style = workbook.createCellStyle();
        XSSFDataFormat format = (XSSFDataFormat) workbook.createDataFormat();
        style.setDataFormat(format.getFormat("TEXT"));

        Row title = ExcelUtil.createRow(sheet1, 0);
        for (int i = 0; i < rqlPropertyRowCount; i++) {
            setCell(style, title, i, propNames.get(i + ""));
        }
        for (int i = 1; i < shippings.size() + 1; i++) {
            Row row = ExcelUtil.createRow(sheet1, i);
            Shipping shipping = shippings.get(i - 1);
            for (int m = 0; m < rqlPropertyRowCount; m++) {
                String prop = propNames.get(m + "");
                String prop2 = prop.toUpperCase().charAt(0) + prop.substring(1, prop.length());
                Object value = shipping.getClass().getMethod("get" + prop2).invoke(shipping);
                if (value == null) {
                    value = "";
                }
                setCell(style, row, m, value + "");
            }
        }
        ExcelUtil.saveWorkbook(workbook, outputFilePath);
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



    public static class Shipping {
        private String id;
        private String descriptor;
        private String postalCode;
        private String description;
        private String city;
        private String deliveryOption;
        private String branchName;
        private String phoneNumber;
        private String order;
        private String address2;
        private String address1;
        private String pickupTime;
        private String shippingMethod;
        private String version;
        private String shippingGroupClassType;
        private String pickupDate;
        private String stateAddress;
        private String priceInfo;
        private String type;
        private String state;
        private String addressId;
        private String country;
        private String lastName;
        private String firstName;
        private String nickName;



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



        public String getPostalCode() {
            return postalCode;
        }



        public void setPostalCode(String pPostalCode) {
            postalCode = pPostalCode;
        }



        public String getDescription() {
            return description;
        }



        public void setDescription(String pDescription) {
            description = pDescription;
        }



        public String getCity() {
            return city;
        }



        public void setCity(String pCity) {
            city = pCity;
        }



        public String getDeliveryOption() {
            return deliveryOption;
        }



        public void setDeliveryOption(String pDeliveryOption) {
            deliveryOption = pDeliveryOption;
        }



        public String getBranchName() {
            return branchName;
        }



        public void setBranchName(String pBranchName) {
            branchName = pBranchName;
        }



        public String getPhoneNumber() {
            return phoneNumber;
        }



        public void setPhoneNumber(String pPhoneNumber) {
            phoneNumber = pPhoneNumber;
        }



        public String getOrder() {
            return order;
        }



        public void setOrder(String pOrder) {
            order = pOrder;
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



        public String getPickupTime() {
            return pickupTime;
        }



        public void setPickupTime(String pPickupTime) {
            pickupTime = pPickupTime;
        }



        public String getShippingMethod() {
            return shippingMethod;
        }



        public void setShippingMethod(String pShippingMethod) {
            shippingMethod = pShippingMethod;
        }



        public String getVersion() {
            return version;
        }



        public void setVersion(String pVersion) {
            version = pVersion;
        }



        public String getShippingGroupClassType() {
            return shippingGroupClassType;
        }



        public void setShippingGroupClassType(String pShippingGroupClassType) {
            shippingGroupClassType = pShippingGroupClassType;
        }



        public String getPickupDate() {
            return pickupDate;
        }



        public void setPickupDate(String pPickupDate) {
            pickupDate = pPickupDate;
        }



        public String getStateAddress() {
            return stateAddress;
        }



        public void setStateAddress(String pStateAddress) {
            stateAddress = pStateAddress;
        }



        public String getPriceInfo() {
            return priceInfo;
        }



        public void setPriceInfo(String pPriceInfo) {
            priceInfo = pPriceInfo;
        }



        public String getType() {
            return type;
        }



        public void setType(String pType) {
            type = pType;
        }



        public String getState() {
            return state;
        }



        public void setState(String pState) {
            state = pState;
        }



        public String getAddressId() {
            return addressId;
        }



        public void setAddressId(String pAddressId) {
            addressId = pAddressId;
        }



        public String getCountry() {
            return country;
        }



        public void setCountry(String pCountry) {
            country = pCountry;
        }



        public String getLastName() {
            return lastName;
        }



        public void setLastName(String pLastName) {
            lastName = pLastName;
        }



        public String getFirstName() {
            return firstName;
        }



        public void setFirstName(String pFirstName) {
            firstName = pFirstName;
        }



        public String getNickName() {
            return nickName;
        }



        public void setNickName(String pNickName) {
            nickName = pNickName;
        }



        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }

    }

}

