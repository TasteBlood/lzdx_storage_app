package com.cloudcreativity.storage.entity;

import com.cloudcreativity.storage.base.BaseResult;

public class EnterRecord extends BaseResult {

    public static class Entity{
        private int id;
        private String unit;
        private float number;
        private String create_time;
        private String qrCodeUrl;
        private String adminName;
        private String enterPlace;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public float getNumber() {
            return number;
        }

        public void setNumber(float number) {
            this.number = number;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getQrCodeUrl() {
            return qrCodeUrl;
        }

        public void setQrCodeUrl(String qrCodeUrl) {
            this.qrCodeUrl = qrCodeUrl;
        }

        public String getAdminName() {
            return adminName;
        }

        public void setAdminName(String adminName) {
            this.adminName = adminName;
        }

        public String getEnterPlace() {
            return enterPlace;
        }

        public void setEnterPlace(String enterPlace) {
            this.enterPlace = enterPlace;
        }
    }
}
