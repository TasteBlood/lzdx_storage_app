package com.cloudcreativity.storage.entity;

import com.cloudcreativity.storage.base.BaseResult;

import java.io.Serializable;
import java.util.List;

public class Provider extends BaseResult {

    private Page info;

    public Page getInfo() {
        return info;
    }

    public void setInfo(Page info) {
        this.info = info;
    }

    public class Page{

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
        private String realName;
        private String phone;
        private String address;
        private String shopName;
        private String pRemarks;
        private int level;
        private int state;
        private int categoryId;
        private int providerId;
        private String businessNumber;
        private String roleId;
        private String createTime;
        private String adminName;
        private String englishName;
        private String companyPhone;
        private String email;
        private String goodsCategory;
        private String idCard;
        private String bankCard;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getpRemarks() {
            return pRemarks;
        }

        public void setpRemarks(String pRemarks) {
            this.pRemarks = pRemarks;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public int getProviderId() {
            return providerId;
        }

        public void setProviderId(int providerId) {
            this.providerId = providerId;
        }

        public String getBusinessNumber() {
            return businessNumber;
        }

        public void setBusinessNumber(String businessNumber) {
            this.businessNumber = businessNumber;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getAdminName() {
            return adminName;
        }

        public void setAdminName(String adminName) {
            this.adminName = adminName;
        }

        public String getEnglishName() {
            return englishName;
        }

        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }

        public String getCompanyPhone() {
            return companyPhone;
        }

        public void setCompanyPhone(String companyPhone) {
            this.companyPhone = companyPhone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGoodsCategory() {
            return goodsCategory;
        }

        public void setGoodsCategory(String goodsCategory) {
            this.goodsCategory = goodsCategory;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getBankCard() {
            return bankCard;
        }

        public void setBankCard(String bankCard) {
            this.bankCard = bankCard;
        }
    }
}
