package com.cloudcreativity.storage.entity;

import com.cloudcreativity.storage.base.BaseResult;
import com.cloudcreativity.storage.utils.StrUtils;

import java.util.List;

public class PriceGoods extends BaseResult {

    private List<Entity> info;

    public List<Entity> getInfo() {
        return info;
    }

    public void setInfo(List<Entity> info) {
        this.info = info;
    }

    public class Entity{
        private String address;
        private int id;
        private int needsGoodsId;
        private int price;
        private int providerId;
        private int surveyId;
        private String goodsName;
        private String shopName;
        private String specName;

        public String formatPrice(){
            return "¥"+ StrUtils.get2BitDecimal(this.price/100f);
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNeedsGoodsId() {
            return needsGoodsId;
        }

        public void setNeedsGoodsId(int needsGoodsId) {
            this.needsGoodsId = needsGoodsId;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getProviderId() {
            return providerId;
        }

        public void setProviderId(int providerId) {
            this.providerId = providerId;
        }

        public int getSurveyId() {
            return surveyId;
        }

        public void setSurveyId(int surveyId) {
            this.surveyId = surveyId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getSpecName() {
            return specName;
        }

        public void setSpecName(String specName) {
            this.specName = specName;
        }
    }
}
