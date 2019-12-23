package com.cloudcreativity.storage.entity;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/12/7 16:01
 * e-mail: xxw0701@sina.com
 */
public class ApproveWrapper {

    private Wrapper info;

    public Wrapper getInfo() {
        return info;
    }

    public void setInfo(Wrapper info) {
        this.info = info;
    }

    public class State{
        private int account_id1;
        private int account_id2;
        private int account_id3;
        private int account_id4;
        private int account_id5;
        private int state1;
        private int state2;
        private int state3;
        private int state4;
        private int state5;
        private String name1;
        private String name2;
        private String name3;
        private String name4;
        private String name5;
        private String reason2;
        private String reason3;
        private String reason4;
        private String reason5;

        public int getAccount_id1() {
            return account_id1;
        }

        public void setAccount_id1(int account_id1) {
            this.account_id1 = account_id1;
        }

        public int getAccount_id2() {
            return account_id2;
        }

        public void setAccount_id2(int account_id2) {
            this.account_id2 = account_id2;
        }

        public int getAccount_id3() {
            return account_id3;
        }

        public void setAccount_id3(int account_id3) {
            this.account_id3 = account_id3;
        }

        public int getAccount_id4() {
            return account_id4;
        }

        public void setAccount_id4(int account_id4) {
            this.account_id4 = account_id4;
        }

        public int getAccount_id5() {
            return account_id5;
        }

        public void setAccount_id5(int account_id5) {
            this.account_id5 = account_id5;
        }

        public int getState1() {
            return state1;
        }

        public void setState1(int state1) {
            this.state1 = state1;
        }

        public int getState2() {
            return state2;
        }

        public void setState2(int state2) {
            this.state2 = state2;
        }

        public int getState3() {
            return state3;
        }

        public void setState3(int state3) {
            this.state3 = state3;
        }

        public int getState4() {
            return state4;
        }

        public void setState4(int state4) {
            this.state4 = state4;
        }

        public int getState5() {
            return state5;
        }

        public void setState5(int state5) {
            this.state5 = state5;
        }

        public String getName1() {
            return name1;
        }

        public void setName1(String name1) {
            this.name1 = name1;
        }

        public String getName2() {
            return name2;
        }

        public void setName2(String name2) {
            this.name2 = name2;
        }

        public String getName3() {
            return name3;
        }

        public void setName3(String name3) {
            this.name3 = name3;
        }

        public String getName4() {
            return name4;
        }

        public void setName4(String name4) {
            this.name4 = name4;
        }

        public String getName5() {
            return name5;
        }

        public void setName5(String name5) {
            this.name5 = name5;
        }

        public String getReason2() {
            return reason2;
        }

        public void setReason2(String reason2) {
            this.reason2 = reason2;
        }

        public String getReason3() {
            return reason3;
        }

        public void setReason3(String reason3) {
            this.reason3 = reason3;
        }

        public String getReason4() {
            return reason4;
        }

        public void setReason4(String reason4) {
            this.reason4 = reason4;
        }

        public String getReason5() {
            return reason5;
        }

        public void setReason5(String reason5) {
            this.reason5 = reason5;
        }
    }

    public class Wrapper{
        private Approve.Entity orderAttach;
        private State funds;

        public Approve.Entity getOrderAttach() {
            return orderAttach;
        }

        public void setOrderAttach(Approve.Entity orderAttach) {
            this.orderAttach = orderAttach;
        }

        public State getFunds() {
            return funds;
        }

        public void setFunds(State funds) {
            this.funds = funds;
        }
    }
}
