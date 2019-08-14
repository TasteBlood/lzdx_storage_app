package com.cloudcreativity.storage.entity;

import com.cloudcreativity.storage.base.BaseResult;

import java.io.Serializable;
import java.util.List;

public class BuyOrder extends BaseResult implements Serializable{

    private Page info;

    public Page getInfo() {
        return info;
    }

    public void setInfo(Page info) {
        this.info = info;
    }

    public static class Page{
        private boolean isLastPage;
        private List<Entity> list;

        public boolean isLastPage() {
            return isLastPage;
        }

        public void setLastPage(boolean lastPage) {
            isLastPage = lastPage;
        }

        public List<Entity> getList() {
            return list;
        }

        public void setList(List<Entity> list) {
            this.list = list;
        }
    }

    public class Entity implements Serializable {
        private int id;
        private int wayId;
        private String name;
        private String oRemarks;
        private String createTime;
        private String institutionName;
        private String wayName;
        private String reason;
        private int state;
        private int institutionId;
        // =2 询价结束
        private int surveyState;
        // =1 支付完成 可以入库
        private int priceState;
        private int mainState;
        private String mainReason;
        private String price;
        // =2 餐饮采价，入库时需要调用其他接口
        private int wayState;
        private MethodDomain methodDomain;
        private List<ApprovalDomains> approvalDomains;

        public int getWayState() {
            return wayState;
        }

        public void setWayState(int wayState) {
            this.wayState = wayState;
        }

        public int getSurveyState() {
            return surveyState;
        }

        public void setSurveyState(int surveyState) {
            this.surveyState = surveyState;
        }

        public int getPriceState() {
            return priceState;
        }

        public void setPriceState(int priceState) {
            this.priceState = priceState;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getWayId() {
            return wayId;
        }

        public void setWayId(int wayId) {
            this.wayId = wayId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getoRemarks() {
            return oRemarks;
        }

        public void setoRemarks(String oRemarks) {
            this.oRemarks = oRemarks;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getInstitutionName() {
            return institutionName;
        }

        public void setInstitutionName(String institutionName) {
            this.institutionName = institutionName;
        }

        public String getWayName() {
            return wayName;
        }

        public void setWayName(String wayName) {
            this.wayName = wayName;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getInstitutionId() {
            return institutionId;
        }

        public void setInstitutionId(int institutionId) {
            this.institutionId = institutionId;
        }

        public int getMainState() {
            return mainState;
        }

        public void setMainState(int mainState) {
            this.mainState = mainState;
        }

        public String getMainReason() {
            return mainReason;
        }

        public void setMainReason(String mainReason) {
            this.mainReason = mainReason;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public MethodDomain getMethodDomain() {
            return methodDomain;
        }

        public void setMethodDomain(MethodDomain methodDomain) {
            this.methodDomain = methodDomain;
        }

        public List<ApprovalDomains> getApprovalDomains() {
            return approvalDomains;
        }

        public void setApprovalDomains(List<ApprovalDomains> approvalDomains) {
            this.approvalDomains = approvalDomains;
        }
    }

    // 采购单 采购方式
    private  class MethodDomain implements Serializable{
        private int id;
        private String name;
        private int institutionId;
        private int startPrice;
        private int endPrice;
        private String createTime;
        private String institutionName;
        private String personDomains;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getInstitutionId() {
            return institutionId;
        }

        public void setInstitutionId(int institutionId) {
            this.institutionId = institutionId;
        }

        public int getStartPrice() {
            return startPrice;
        }

        public void setStartPrice(int startPrice) {
            this.startPrice = startPrice;
        }

        public int getEndPrice() {
            return endPrice;
        }

        public void setEndPrice(int endPrice) {
            this.endPrice = endPrice;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getInstitutionName() {
            return institutionName;
        }

        public void setInstitutionName(String institutionName) {
            this.institutionName = institutionName;
        }

        public String getPersonDomains() {
            return personDomains;
        }

        public void setPersonDomains(String personDomains) {
            this.personDomains = personDomains;
        }
    }

    //采购单 采购审批人
    private class ApprovalDomains implements Serializable{
        private int id;
        private int accountId;
        private int orderId;
        private int state;
        private String reason;
        private String createTime;
        private String accountName;
        private String mobile;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
