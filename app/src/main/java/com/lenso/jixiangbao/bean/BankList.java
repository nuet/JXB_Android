package com.lenso.jixiangbao.bean;

import java.util.List;

/**
 * Created by Chung on 2016/6/7.
 */
public class BankList {
    private String status;
    private String rsmsg;
    private List<Bank> banklist;

    public BankList(String status, String rsmsg, List<Bank> banklist) {
        this.status = status;
        this.rsmsg = rsmsg;
        this.banklist = banklist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRsmsg() {
        return rsmsg;
    }

    public void setRsmsg(String rsmsg) {
        this.rsmsg = rsmsg;
    }

    public List<Bank> getBanklist() {
        return banklist;
    }

    public void setBanklist(List<Bank> banklist) {
        this.banklist = banklist;
    }

    public static class Bank{
        private String id;
        private String name;
        private String value;

        public Bank(String id, String name, String value) {
            this.id = id;
            this.name = name;
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
