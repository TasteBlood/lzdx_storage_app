package com.cloudcreativity.storage.entity;

import com.cloudcreativity.storage.base.BaseResult;

import java.util.List;

public class BuyGoods extends BaseResult {

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

    public class Entity{
        private int id;
        private int goodsId;
        private int orderId;
        private String price;
        private String nRemarks;
        private int number;
        private int state;
        private String createTime;
        private int specsId;
        private String goodsName;
        private String specsName;
        private String unitName;

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getnRemarks() {
            return nRemarks;
        }

        public void setnRemarks(String nRemarks) {
            this.nRemarks = nRemarks;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getSpecsId() {
            return specsId;
        }

        public void setSpecsId(int specsId) {
            this.specsId = specsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getSpecsName() {
            return specsName;
        }

        public void setSpecsName(String specsName) {
            this.specsName = specsName;
        }
    }
}
