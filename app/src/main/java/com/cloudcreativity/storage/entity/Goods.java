package com.cloudcreativity.storage.entity;

import com.cloudcreativity.storage.base.BaseResult;

import java.util.List;

public class Goods extends BaseResult{

    private Page info;

    public Page getInfo() {
        return info;
    }

    public void setInfo(Page info) {
        this.info = info;
    }

    public class Page{
        private List<Entity> records;

        public List<Entity> getRecords() {
            return records;
        }

        public void setRecords(List<Entity> records) {
            this.records = records;
        }
    }

    public static class Entity{
        private String name;
        private String unit;
        private int number;
        private String providerName;
        private int warnNum;
        private float money;
        private String area;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getProviderName() {
            return providerName;
        }

        public void setProviderName(String providerName) {
            this.providerName = providerName;
        }

        public int getWarnNum() {
            return warnNum;
        }

        public void setWarnNum(int warnNum) {
            this.warnNum = warnNum;
        }

        public float getMoney() {
            return money;
        }

        public void setMoney(float money) {
            this.money = money;
        }
    }
}
