package com.cloudcreativity.storage.entity;

import android.text.TextUtils;

import com.cloudcreativity.storage.utils.MoneyUtils;
import com.cloudcreativity.storage.utils.StrUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/12/7 12:51
 * e-mail: xxw0701@sina.com
 */
public class Approve {

    private Page info;

    public Page getInfo() {
        return info;
    }

    public void setInfo(Page info) {
        this.info = info;
    }

    public static class Page{
        private List<Entity> records;

        private int totalpage;

        public List<Entity> getRecords() {
            return records;
        }

        public void setRecords(List<Entity> records) {
            this.records = records;
        }

        public int getTotalpage() {
            return totalpage;
        }

        public void setTotalpage(int totalpage) {
            this.totalpage = totalpage;
        }
    }

    public static class Entity{
        private int id;
        private int orderNumber;
        private int orderId;
        private String url;
        private String orderNum;
        private int state5;
        private int account5;
        private String name5;
        private String reason5;
        private int state4;
        private int account4;
        private String name4;
        private String reason4;
        private int state3;
        private int account3;
        private String name3;
        private String reason3;
        private int state2;
        private int account2;
        private String name2;
        private String reason2;
        private int state1;
        private int account1;
        private String name1;
        private String reason1;
        private String recordDate;
        private String institutionName;
        private String payCompany;
        private int applyMoney;
        private String payJson;
        private String actualMoneyNum;
        private String applyMoneyNum;

        public String getActualMoneyNum() {
            return actualMoneyNum;
        }

        public void setActualMoneyNum(String actualMoneyNum) {
            this.actualMoneyNum = actualMoneyNum;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPayJson() {
            return payJson;
        }

        public void setPayJson(String payJson) {
            this.payJson = payJson;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(int orderNumber) {
            this.orderNumber = orderNumber;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public int getState5() {
            return state5;
        }

        public void setState5(int state5) {
            this.state5 = state5;
        }

        public String getName5() {
            return name5;
        }

        public void setName5(String name5) {
            this.name5 = name5;
        }

        public String getReason5() {
            return reason5;
        }

        public void setReason5(String reason5) {
            this.reason5 = reason5;
        }

        public int getState4() {
            return state4;
        }

        public void setState4(int state4) {
            this.state4 = state4;
        }

        public String getName4() {
            return name4;
        }

        public void setName4(String name4) {
            this.name4 = name4;
        }

        public String getReason4() {
            return reason4;
        }

        public void setReason4(String reason4) {
            this.reason4 = reason4;
        }

        public int getState3() {
            return state3;
        }

        public void setState3(int state3) {
            this.state3 = state3;
        }

        public String getName3() {
            return name3;
        }

        public void setName3(String name3) {
            this.name3 = name3;
        }

        public String getReason3() {
            return reason3;
        }

        public void setReason3(String reason3) {
            this.reason3 = reason3;
        }

        public int getState2() {
            return state2;
        }

        public void setState2(int state2) {
            this.state2 = state2;
        }

        public String getName2() {
            return name2;
        }

        public void setName2(String name2) {
            this.name2 = name2;
        }

        public String getReason2() {
            return reason2;
        }

        public void setReason2(String reason2) {
            this.reason2 = reason2;
        }

        public int getState1() {
            return state1;
        }

        public void setState1(int state1) {
            this.state1 = state1;
        }

        public String getName1() {
            return name1;
        }

        public void setName1(String name1) {
            this.name1 = name1;
        }

        public String getReason1() {
            return reason1;
        }

        public void setReason1(String reason1) {
            this.reason1 = reason1;
        }

        public String getRecordDate() {
            return recordDate;
        }

        public void setRecordDate(String recordDate) {
            this.recordDate = recordDate;
        }

        public String getInstitutionName() {
            return institutionName;
        }

        public void setInstitutionName(String institutionName) {
            this.institutionName = institutionName;
        }

        public String getPayCompany() {
            return payCompany;
        }

        public void setPayCompany(String payCompany) {
            this.payCompany = payCompany;
        }

        public int getApplyMoney() {
            return applyMoney;
        }

        public void setApplyMoney(int applyMoney) {
            this.applyMoney = applyMoney;
        }

        public int getAccount5() {
            return account5;
        }

        public void setAccount5(int account5) {
            this.account5 = account5;
        }

        public int getAccount4() {
            return account4;
        }

        public void setAccount4(int account4) {
            this.account4 = account4;
        }

        public int getAccount3() {
            return account3;
        }

        public void setAccount3(int account3) {
            this.account3 = account3;
        }

        public int getAccount2() {
            return account2;
        }

        public void setAccount2(int account2) {
            this.account2 = account2;
        }

        public int getAccount1() {
            return account1;
        }

        public void setAccount1(int account1) {
            this.account1 = account1;
        }

        public String formatPrice(){
            return "￥"+StrUtils.get2BitDecimal(this.applyMoney/100f);
        }
        public String formatBig(){
            return MoneyUtils.toChinese(String.valueOf(StrUtils.get2BitDecimal(this.applyMoney/100f)));
        }

        public String formatActualPrice(){
            return TextUtils.isEmpty(this.actualMoneyNum)?"":"￥"+StrUtils.get2BitDecimal(Integer.parseInt(this.actualMoneyNum)/100f);
        }
        public String formatActualBig(){
            return TextUtils.isEmpty(this.actualMoneyNum)?"":MoneyUtils.toChinese(String.valueOf(StrUtils.get2BitDecimal(Integer.parseInt(this.actualMoneyNum)/100f)));
        }

        public String formatApplyPrice(){
            return TextUtils.isEmpty(this.applyMoneyNum)?"":"￥"+StrUtils.get2BitDecimal(Integer.parseInt(this.applyMoneyNum)/100f);
        }
        public String formatApplyBig(){
            return TextUtils.isEmpty(this.applyMoneyNum)?"":MoneyUtils.toChinese(String.valueOf(StrUtils.get2BitDecimal(Integer.parseInt(this.applyMoneyNum)/100f)));
        }

        public String getApproveAttach(){
            try {
                JSONObject object = new JSONObject(this.payJson);
                return object.getString("content");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        public String formatAttachPriceBig(){
            try {
                JSONObject object = new JSONObject(this.payJson);
                return MoneyUtils.toChinese(String.valueOf(StrUtils.get2BitDecimal(object.getInt("money")/100f)));
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        public String formatAttachPrice(){
            try {
                JSONObject object = new JSONObject(this.payJson);
                return "￥"+StrUtils.get2BitDecimal(object.getInt("money")/100f);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        public String getComName(){
            try{
                JSONObject object = new JSONObject(this.payCompany);
                return object.getString("name");
            }catch (JSONException e){
                return null;
            }
        }

        public String getComCard(){
            try{
                JSONObject object = new JSONObject(this.payCompany);
                return object.getString("cardNum");
            }catch (JSONException e){
                return null;
            }
        }

        public String getComBankName(){
            try{
                JSONObject object = new JSONObject(this.payCompany);
                return object.getString("bankName");
            }catch (JSONException e){
                return null;
            }
        }
        public String getComMoney(){
            try {
                JSONObject object = new JSONObject(this.payCompany);
                return MoneyUtils.toChinese(String.valueOf(StrUtils.get2BitDecimal(object.getInt("money")/100f)));
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
