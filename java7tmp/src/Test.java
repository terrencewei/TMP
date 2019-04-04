import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class Test {

    private static String mSubmitOrderLogInputDir         = "/home/terrence/Downloads/t4_api_order_log";
    private static int    mLoadSubmitOrderLog2DBBatchSize = 1000;

    private static final Gson                               GSON                = new GsonBuilder().create();
    private static       Map<String, SubmitOrderLogRequest> SubmitOrderLogCache = new HashMap<>(1024, 1);



    public static void main(String[] aa) throws Exception {
        loadSubmitOrderLog2DB();
    }



    private static boolean loadSubmitOrderLog2DB() {
        String inputDirPath = getSubmitOrderLogInputDir();
        if (StringUtils.isBlank(inputDirPath)) {
            vlogError("loadSubmitOrderLog2DB() is empty.");
            return false;
        }
        File inputDir = new File(inputDirPath);
        if (inputDir == null || !inputDir.isDirectory()) {
            vlogError("loadSubmitOrderLog2DB() inputDirPath:{0} is not a directory", inputDirPath);
            return false;
        }
        String[] filePaths = inputDir.list();
        for (String filePath : filePaths) {
            String fileFullPath = inputDirPath + "/" + filePath;
            File file = new File(fileFullPath);
            if (!file.isFile()) {
                vlogError("loadSubmitOrderLog2DB() fileFullPath:{0} is not a file, ignore it", fileFullPath);
                continue;
            }
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (line.contains("[submitOrder] Request(")) {
                        if (!parseRequest(line)) {
                            continue;
                        }
                    } else if (line.contains("[submitOrder] Response(")) {
                        if (!parseResponse(line)) {
                            continue;
                        }
                        if (SubmitOrderLogCache.size() >= getLoadSubmitOrderLog2DBBatchSize()) {
                            commit2DB();
                        }
                    }
                }
            } catch (Exception e) {
                vlogError(e, "loadSubmitOrderLog2DB() error occurs");
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    vlogError(e, "loadSubmitOrderLog2DB() error occurs when close buffer reader");
                }
            }
        }
        commit2DB();
        return true;
    }


    private static boolean parseRequest(String pLine) {
        String requestOrderJson = getSubUtilSimple(pLine, "Parameters\\=\\[order\\=", ";\\]");
        if (StringUtils.isBlank(requestOrderJson)) {
            vlogWarning("loadSubmitOrderLog2DB() requestOrderJson from line:{0} is null, ignore it", pLine);
            return false;
        }
        try {
            SubmitOrderLogRequest requestOrder = GSON.fromJson(requestOrderJson, SubmitOrderLogRequest.class);
            if (requestOrder == null) {
                vlogWarning("loadSubmitOrderLog2DB() parse requestOrder is null, ignore requestOrderJson:{0}",
                        requestOrderJson);
                return false;
            }
            // check apiSiteId if its from hover
            requestOrder.setHoverItemAdded(
                    StringUtils.isNotBlank(requestOrder.getApiSiteId()) && requestOrder.getApiSiteId().equals("HVR"));
            // parse profile id
            requestOrder.setProfileId(getSubUtilSimple(pLine, "DYN_USER_ID\\=", ";"));
            requestOrder.setSubmitDate(getSubUtilSimple(pLine, "", " \\[submitOrder\\]"));
            String requestUniqueId = getSubUtilSimple(pLine, "Request\\(", "\\)");
            requestOrder.setOrderId("fromLog_" + requestUniqueId);
            SubmitOrderLogCache.put(requestUniqueId, requestOrder);
            return true;
        } catch (JsonSyntaxException e) {
            vlogError(e,
                    "loadSubmitOrderLog2DB() cannot convert requestOrderJson:{0} to SubmitOrderLogRequest, ignore it",
                    requestOrderJson);

        }
        return false;
    }



    private static boolean parseResponse(String pLine) {
        String requestUniqueId = getSubUtilSimple(pLine, "Response\\(", "\\)");
        SubmitOrderLogRequest requestOrder = SubmitOrderLogCache.get(requestUniqueId);
        if (requestOrder == null) {
            // if no request order, ignore this response order
            vlogWarning("loadSubmitOrderLog2DB() requestOrder is null, ignore this resposne order line:{0}", pLine);
            return false;
        }
        String responseOrderJson = getSubUtilSimple(pLine, "Result\\=\\[", "\\]");
        if (StringUtils.isBlank(responseOrderJson)) {
            vlogWarning("loadSubmitOrderLog2DB() responseOrderJson from line:{0} is null, ignore it", pLine);
            SubmitOrderLogCache.remove(requestUniqueId);
            return false;
        }
        try {
            SubmitOrderLogResponse responseOrder = GSON.fromJson(responseOrderJson, SubmitOrderLogResponse.class);
            if (responseOrder == null) {
                vlogWarning("loadSubmitOrderLog2DB() responseOrder is null, ignore responseOrderJson:{0}",
                        responseOrderJson);
                SubmitOrderLogCache.remove(requestUniqueId);
                return false;
            }
            if (StringUtils.isBlank(responseOrder.getOrderId())) {
                vlogWarning("loadSubmitOrderLog2DB() order id in responseOrder is null, ignore responseOrder:{0}",
                        responseOrder);
                SubmitOrderLogCache.remove(requestUniqueId);
                return false;
            }
            requestOrder.setSubmitOrderLogResponse(responseOrder);
            return true;
        } catch (JsonSyntaxException e) {
            vlogError(e, "loadSubmitOrderLog2DB() cannot convert orderStr:{0} to SubmitOrderLogRequest, ignore it",
                    responseOrderJson);
        }
        SubmitOrderLogCache.remove(requestUniqueId);
        return false;
    }




    private static void commit2DB() {
        synchronized (SubmitOrderLogCache) {
            if (SubmitOrderLogCache.isEmpty()) {
                System.out.println("NOTHING TO COMMIT");
                return;
            }
            try {
                System.out.println("BEGIN COMMIT TO DB");
                for (Map.Entry<String, SubmitOrderLogRequest> entry : SubmitOrderLogCache.entrySet()) {
                    System.out.println(
                            "Commit request to DB! request unique id:" + entry.getKey() + ", requestOrder:" + entry
                                    .getValue());
                }
            } catch (Exception e) {
                // log SubmitOrderLogCache
                System.out.println("commit2DB() error occurs! SubmitOrderLogCache:" + SubmitOrderLogCache);
            }
            SubmitOrderLogCache.clear();
        }
    }



    public static int getLoadSubmitOrderLog2DBBatchSize() {
        return mLoadSubmitOrderLog2DBBatchSize;
    }



    public static void setLoadSubmitOrderLog2DBBatchSize(int pMLoadSubmitOrderLog2DBBatchSize) {
        mLoadSubmitOrderLog2DBBatchSize = pMLoadSubmitOrderLog2DBBatchSize;
    }



    private static void vlogError(Exception pE, String pS, Object... args) {
        System.out.println(MessageFormat.format(pS, args));
    }



    private static void vlogWarning(String pS, Object... args) {
        System.out.println(MessageFormat.format(pS, args));
    }



    public static class SubmitOrderLogRequest {

        private String  orderId;
        private String  accountId;
        private JobInfo job;
        private String  purchaseOrderNo;
        private String  orderStatusCode;
        List<OrderItem> lineItems;
        private OrderShippingInfo      shipping;
        private String                 sellingBranch;
        private String                 specialInstruction;
        private String                 checkForAvailability;
        private String                 pickupDate;
        private String                 apiSiteId;
        private String                 pickupTime;
        private String                 scheduled;
        private String                 orderTotal;
        // belows are not from request body
        private boolean                hoverItemAdded;
        private SubmitOrderLogResponse submitOrderLogResponse;
        private String                 profileId;
        private String                 submitDate;



        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE).toString();
        }



        public String getSubmitDate() {
            return submitDate;
        }



        public void setSubmitDate(String pSubmitDate) {
            submitDate = pSubmitDate;
        }



        public String getProfileId() {
            return profileId;
        }



        public void setProfileId(String pProfileId) {
            profileId = pProfileId;
        }



        public boolean isHoverItemAdded() {
            return hoverItemAdded;
        }



        public void setHoverItemAdded(boolean pHoverItemAdded) {
            hoverItemAdded = pHoverItemAdded;
        }



        public static class JobInfo {
            private String jobName;
            private String jobNumber;



            @Override
            public String toString() {
                return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE).toString();
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
        }

        public static class OrderItem {

            private String itemNumber;
            private String quantity;
            private String unitOfMeasure;
            private String description;
            private String itemUnitPrice;
            private String itemSubTotal;



            @Override
            public String toString() {
                return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE).toString();
            }



            public String getItemNumber() {
                return itemNumber;
            }



            public void setItemNumber(String pItemNumber) {
                itemNumber = pItemNumber;
            }



            public String getQuantity() {
                return quantity;
            }



            public void setQuantity(String pQuantity) {
                quantity = pQuantity;
            }



            public String getUnitOfMeasure() {
                return unitOfMeasure;
            }



            public void setUnitOfMeasure(String pUnitOfMeasure) {
                unitOfMeasure = pUnitOfMeasure;
            }



            public String getDescription() {
                return description;
            }



            public void setDescription(String pDescription) {
                description = pDescription;
            }



            public String getItemUnitPrice() {
                return itemUnitPrice;
            }



            public void setItemUnitPrice(String pItemUnitPrice) {
                itemUnitPrice = pItemUnitPrice;
            }



            public String getItemSubTotal() {
                return itemSubTotal;
            }



            public void setItemSubTotal(String pItemSubTotal) {
                itemSubTotal = pItemSubTotal;
            }
        }

        public static class OrderShippingInfo {
            private String shippingMethod;
            private String shippingBranch;

            private OrderAddress address;



            @Override
            public String toString() {
                return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE).toString();
            }



            public String getShippingMethod() {
                return shippingMethod;
            }



            public void setShippingMethod(String pShippingMethod) {
                shippingMethod = pShippingMethod;
            }



            public String getShippingBranch() {
                return shippingBranch;
            }



            public void setShippingBranch(String pShippingBranch) {
                shippingBranch = pShippingBranch;
            }



            public OrderAddress getAddress() {
                return address;
            }



            public void setAddress(OrderAddress pAddress) {
                address = pAddress;
            }
        }

        public static class OrderAddress {
            private String address1;
            private String address2;
            private String address3;
            private String city;
            private String postalCode;
            private String state;



            @Override
            public String toString() {
                return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE).toString();
            }



            public String getAddress1() {
                return address1;
            }



            public void setAddress1(String pAddress1) {
                address1 = pAddress1;
            }



            public String getAddress2() {
                return address2;
            }



            public void setAddress2(String pAddress2) {
                address2 = pAddress2;
            }



            public String getCity() {
                return city;
            }



            public void setCity(String pCity) {
                city = pCity;
            }



            public String getPostalCode() {
                return postalCode;
            }



            public void setPostalCode(String pPostalCode) {
                postalCode = pPostalCode;
            }



            public String getState() {
                return state;
            }



            public void setState(String pState) {
                state = pState;
            }



            public String getAddress3() {
                return address3;
            }



            public void setAddress3(String pAddress3) {
                address3 = pAddress3;
            }
        }



        public String getOrderId() {
            return orderId;
        }



        public void setOrderId(String pOrderId) {
            orderId = pOrderId;
        }



        public String getAccountId() {
            return accountId;
        }



        public void setAccountId(String pAccountId) {
            accountId = pAccountId;
        }



        public JobInfo getJob() {
            return job;
        }



        public void setJob(JobInfo pJob) {
            job = pJob;
        }



        public String getPurchaseOrderNo() {
            return purchaseOrderNo;
        }



        public void setPurchaseOrderNo(String pPurchaseOrderNo) {
            purchaseOrderNo = pPurchaseOrderNo;
        }



        public String getOrderStatusCode() {
            return orderStatusCode;
        }



        public void setOrderStatusCode(String pOrderStatusCode) {
            orderStatusCode = pOrderStatusCode;
        }



        public List<OrderItem> getLineItems() {
            return lineItems;
        }



        public void setLineItems(List<OrderItem> pLineItems) {
            lineItems = pLineItems;
        }



        public OrderShippingInfo getShipping() {
            return shipping;
        }



        public void setShipping(OrderShippingInfo pShipping) {
            shipping = pShipping;
        }



        public String getSellingBranch() {
            return sellingBranch;
        }



        public void setSellingBranch(String pSellingBranch) {
            sellingBranch = pSellingBranch;
        }



        public String getSpecialInstruction() {
            return specialInstruction;
        }



        public void setSpecialInstruction(String pSpecialInstruction) {
            specialInstruction = pSpecialInstruction;
        }



        public String getCheckForAvailability() {
            return checkForAvailability;
        }



        public void setCheckForAvailability(String pCheckForAvailability) {
            checkForAvailability = pCheckForAvailability;
        }



        public String getPickupDate() {
            return pickupDate;
        }



        public void setPickupDate(String pPickupDate) {
            pickupDate = pPickupDate;
        }



        public String getApiSiteId() {
            return apiSiteId;
        }



        public void setApiSiteId(String pApiSiteId) {
            apiSiteId = pApiSiteId;
        }



        public String getPickupTime() {
            return pickupTime;
        }



        public void setPickupTime(String pPickupTime) {
            pickupTime = pPickupTime;
        }



        public String getScheduled() {
            return scheduled;
        }



        public void setScheduled(String pScheduled) {
            scheduled = pScheduled;
        }



        public String getOrderTotal() {
            return orderTotal;
        }



        public void setOrderTotal(String pOrderTotal) {
            orderTotal = pOrderTotal;
        }



        public SubmitOrderLogResponse getSubmitOrderLogResponse() {
            return submitOrderLogResponse;
        }



        public void setSubmitOrderLogResponse(SubmitOrderLogResponse pSubmitOrderLogResponse) {
            submitOrderLogResponse = pSubmitOrderLogResponse;
        }
    }

    public static class SubmitOrderLogResponse {
        private String code;
        private String message;
        private String messageCode;
        private String orderId;



        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE).toString();
        }



        public String getCode() {
            return code;
        }



        public void setCode(String pCode) {
            code = pCode;
        }



        public String getMessage() {
            return message;
        }



        public void setMessage(String pMessage) {
            message = pMessage;
        }



        public String getMessageCode() {
            return messageCode;
        }



        public void setMessageCode(String pMessageCode) {
            messageCode = pMessageCode;
        }



        public String getOrderId() {
            return orderId;
        }



        public void setOrderId(String pOrderId) {
            orderId = pOrderId;
        }
    }



    public static String getSubUtilSimple(String soap, String start, String end) {
        Pattern pattern = Pattern.compile(start + "(.*?)" + end);
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }



    private static void vlogDebug(String pS, Object... args) {
        System.out.println(MessageFormat.format(pS, args));
    }



    private static void vlogError(Exception pE, String pS) {
        System.out.println(pS);
    }



    private static void vlogError(String pS) {
        System.out.println(pS);
    }



    private static void vlogError(String pS, Object... args) {
        System.out.println(MessageFormat.format(pS, args));
    }



    public static class StringUtils {
        public static boolean isBlank(String pStr) {
            return pStr == null || pStr.length() == 0 || pStr.trim().length() == 0;
        }



        public static boolean isNotBlank(String pStr) {
            return !isBlank(pStr);
        }
    }



    public static String getSubmitOrderLogInputDir() {
        return mSubmitOrderLogInputDir;
    }



    public static void setSubmitOrderLogInputDir(String pSubmitOrderLogInputDir) {
        mSubmitOrderLogInputDir = pSubmitOrderLogInputDir;
    }
}