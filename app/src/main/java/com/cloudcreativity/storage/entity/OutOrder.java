package com.cloudcreativity.storage.entity;

import com.cloudcreativity.storage.base.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/8/7 18:01
 * e-mail: xxw0701@sina.com
 */
public class OutOrder extends BaseResult {

    private List<Entity> info;

    public List<Entity> getInfo() {
        return info;
    }

    public void setInfo(List<Entity> info) {
        this.info = info;
    }

    public class Entity implements Serializable {
        private int accountId;
        private List<ApplyAccountDomain> applyAccountDomain;
        private List<AccountDomain> accountDomain;
        private List<OutAccount> storeAccountDomains;
        private String createTime;
        private int applyAccountId;
        private String storeName;
        private String remark;
        private int id;
        private int state;
        private int storeId;

        public List<OutAccount> getStoreAccountDomains() {
            return storeAccountDomains;
        }

        public void setStoreAccountDomains(List<OutAccount> storeAccountDomains) {
            this.storeAccountDomains = storeAccountDomains;
        }

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public List<ApplyAccountDomain> getApplyAccountDomain() {
            return applyAccountDomain;
        }

        public void setApplyAccountDomain(List<ApplyAccountDomain> applyAccountDomain) {
            this.applyAccountDomain = applyAccountDomain;
        }

        public List<AccountDomain> getAccountDomain() {
            return accountDomain;
        }

        public void setAccountDomain(List<AccountDomain> accountDomain) {
            this.accountDomain = accountDomain;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getApplyAccountId() {
            return applyAccountId;
        }

        public void setApplyAccountId(int applyAccountId) {
            this.applyAccountId = applyAccountId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }
    }

    public class ApplyAccountDomain implements Serializable{

        private int institutionId;
        private String institutionName;
        private String name;
        private int id;
        public void setInstitutionId(int institutionId) {
            this.institutionId = institutionId;
        }
        public int getInstitutionId() {
            return institutionId;
        }

        public void setInstitutionName(String institutionName) {
            this.institutionName = institutionName;
        }
        public String getInstitutionName() {
            return institutionName;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

    }
    public class AccountDomain implements Serializable{

        private int institutionId;
        private String institutionName;
        private String name;
        private int id;
        public void setInstitutionId(int institutionId) {
            this.institutionId = institutionId;
        }
        public int getInstitutionId() {
            return institutionId;
        }

        public void setInstitutionName(String institutionName) {
            this.institutionName = institutionName;
        }
        public String getInstitutionName() {
            return institutionName;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

    }

    public class OutGoods{
        private int id;
        private int goodsId;
        private int specsId;
        private int applyId;
        private int number;
        private int state;
        private String createTime;
        private String goodsName;
        private String specsName;
        private String unitName;
        private int articleId;
        private int price;

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getArticleId() {
            return articleId;
        }

        public void setArticleId(int articleId) {
            this.articleId = articleId;
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

        public int getSpecsId() {
            return specsId;
        }

        public void setSpecsId(int specsId) {
            this.specsId = specsId;
        }

        public int getApplyId() {
            return applyId;
        }

        public void setApplyId(int applyId) {
            this.applyId = applyId;
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

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }
    }

    public class OutAccount implements Serializable{
        private int accountId;
        private String accountName;

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }
    }
}
