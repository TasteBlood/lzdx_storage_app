package com.cloudcreativity.storage.entity;

import com.cloudcreativity.storage.base.BaseResult;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/8/19 14:19
 * e-mail: xxw0701@sina.com
 */
public class UserEntity extends BaseResult {

    private Entity info;

    public Entity getInfo() {
        return info;
    }

    public void setInfo(Entity info) {
        this.info = info;
    }

    public class Entity {
        private int id;
        private String accountName;
        private String accountPwd;
        private int storeId;
        private int state;
        private String createTime;
        private String salt;
        private int accountId;
        private String personName;
        private String token;
        private int institutionId;
        private String institutionName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getAccountPwd() {
            return accountPwd;
        }

        public void setAccountPwd(String accountPwd) {
            this.accountPwd = accountPwd;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
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

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getInstitutionId() {
            return institutionId;
        }

        public void setInstitutionId(int institutionId) {
            this.institutionId = institutionId;
        }

        public String getInstitutionName() {
            return institutionName;
        }

        public void setInstitutionName(String institutionName) {
            this.institutionName = institutionName;
        }
    }
}
