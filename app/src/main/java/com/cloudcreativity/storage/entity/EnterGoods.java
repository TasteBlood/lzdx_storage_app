package com.cloudcreativity.storage.entity;

import android.support.annotation.NonNull;

import com.cloudcreativity.storage.base.BaseResult;
import com.cloudcreativity.storage.utils.StrUtils;

import java.util.List;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/8/5 10:51
 * e-mail: xxw0701@sina.com
 */
public class EnterGoods extends BaseResult {

    private List<Entity> info;

    public List<Entity> getInfo() {
        return info;
    }

    public void setInfo(List<Entity> info) {
        this.info = info;
    }

    public class Entity implements Comparable<Entity>{
        private int id;
        private int needGoodsId;
        private int providerId;
        private int price;
        private String goodsName;
        private String createTime;
        private int orderId;
        private String address;
        private String shopName;
        private String accountId;
        private int state;
        private int goodsId;
        private float number;
        private int specsId;
        private int enterState;
        private int unitId;
        private String specsName;
        private String unitName;

        public String formatPrice(){
            return "￥"+ StrUtils.get2BitDecimal(this.price/100f);
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNeedGoodsId() {
            return needGoodsId;
        }

        public void setNeedGoodsId(int needGoodsId) {
            this.needGoodsId = needGoodsId;
        }

        public int getProviderId() {
            return providerId;
        }

        public void setProviderId(int providerId) {
            this.providerId = providerId;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
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

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public float getNumber() {
            return number;
        }

        public void setNumber(float number) {
            this.number = number;
        }

        public int getSpecsId() {
            return specsId;
        }

        public void setSpecsId(int specsId) {
            this.specsId = specsId;
        }

        public int getEnterState() {
            return enterState;
        }

        public void setEnterState(int enterState) {
            this.enterState = enterState;
        }

        public int getUnitId() {
            return unitId;
        }

        public void setUnitId(int unitId) {
            this.unitId = unitId;
        }

        public String getSpecsName() {
            return specsName;
        }

        public void setSpecsName(String specsName) {
            this.specsName = specsName;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        @Override
        public int compareTo(@NonNull Entity o) {
            return enterState - o.enterState;
        }
    }
}
