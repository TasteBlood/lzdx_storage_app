package com.cloudcreativity.storage.entity;

import com.cloudcreativity.storage.base.BaseResult;
import com.cloudcreativity.storage.utils.StrUtils;

import java.util.List;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/8/9 14:36
 * e-mail: xxw0701@sina.com
 */
public class Goods extends BaseResult{

    private Info info;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public class Info{
        private List<Entity> resultlist;

        public List<Entity> getResultlist() {
            return resultlist;
        }

        public void setResultlist(List<Entity> resultlist) {
            this.resultlist = resultlist;
        }
    }

    public class Entity{
        private int id;
        private int categoryOneId;
        private int categoryTwoId;
        private String goodsName;
        private int unitId;
        private String createTime;
        private String unitName;
        private List<Specs> specsDomains;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCategoryOneId() {
            return categoryOneId;
        }

        public void setCategoryOneId(int categoryOneId) {
            this.categoryOneId = categoryOneId;
        }

        public int getCategoryTwoId() {
            return categoryTwoId;
        }

        public void setCategoryTwoId(int categoryTwoId) {
            this.categoryTwoId = categoryTwoId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public int getUnitId() {
            return unitId;
        }

        public void setUnitId(int unitId) {
            this.unitId = unitId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public List<Specs> getSpecsDomains() {
            return specsDomains;
        }

        public void setSpecsDomains(List<Specs> specsDomains) {
            this.specsDomains = specsDomains;
        }
    }

    public class Specs{
        private int id;
        private int goodsId;
        private String name;
        private String createTime;
        private String number;
        private int price;

        public String formatPrice(){
            return "￥"+ StrUtils.get2BitDecimal(price/100f);
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
