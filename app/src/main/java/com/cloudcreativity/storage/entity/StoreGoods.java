package com.cloudcreativity.storage.entity;

import com.cloudcreativity.storage.utils.StrUtils;

import java.io.Serializable;
import java.util.List;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/8/6 10:07
 * e-mail: xxw0701@sina.com
 */
public class StoreGoods{

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
        private String goodsName;
        private String pinYin;
        private String cateId;
        private int unitId;
        private int storeId;
        private String minWarn;
        private int state;
        private float stock;
        private String createTime;
        private String updateTime;
        private int stateGoods;
        private int providerId;
        private String startTime;
        private String endTime;
        private int price;
        private String position;
        private int specsId;
        private String gRemarks;
        private String barCode;
        private String storeName;
        private String cateName;
        private String unitName;
        private String providerName;
        private int goodsId;
        private String specsName;
        private String enterStoreDomains;
        private String outStoreDomains;
        private float number;

        public float getNumber() {
            return number;
        }

        public void setNumber(float number) {
            this.number = number;
        }

        public String formatPrice(){
            return "￥"+ StrUtils.get2BitDecimal(this.price/100f);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getPinYin() {
            return pinYin;
        }

        public void setPinYin(String pinYin) {
            this.pinYin = pinYin;
        }

        public String getCateId() {
            return cateId;
        }

        public void setCateId(String cateId) {
            this.cateId = cateId;
        }

        public int getUnitId() {
            return unitId;
        }

        public void setUnitId(int unitId) {
            this.unitId = unitId;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public String getMinWarn() {
            return minWarn;
        }

        public void setMinWarn(String minWarn) {
            this.minWarn = minWarn;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public float getStock() {
            return stock;
        }

        public void setStock(float stock) {
            this.stock = stock;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getStateGoods() {
            return stateGoods;
        }

        public void setStateGoods(int stateGoods) {
            this.stateGoods = stateGoods;
        }

        public int getProviderId() {
            return providerId;
        }

        public void setProviderId(int providerId) {
            this.providerId = providerId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public int getSpecsId() {
            return specsId;
        }

        public void setSpecsId(int specsId) {
            this.specsId = specsId;
        }

        public String getgRemarks() {
            return gRemarks;
        }

        public void setgRemarks(String gRemarks) {
            this.gRemarks = gRemarks;
        }

        public String getBarCode() {
            return barCode;
        }

        public void setBarCode(String barCode) {
            this.barCode = barCode;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getCateName() {
            return cateName;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getProviderName() {
            return providerName;
        }

        public void setProviderName(String providerName) {
            this.providerName = providerName;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getSpecsName() {
            return specsName;
        }

        public void setSpecsName(String specsName) {
            this.specsName = specsName;
        }

        public String getEnterStoreDomains() {
            return enterStoreDomains;
        }

        public void setEnterStoreDomains(String enterStoreDomains) {
            this.enterStoreDomains = enterStoreDomains;
        }

        public String getOutStoreDomains() {
            return outStoreDomains;
        }

        public void setOutStoreDomains(String outStoreDomains) {
            this.outStoreDomains = outStoreDomains;
        }
    }
}
