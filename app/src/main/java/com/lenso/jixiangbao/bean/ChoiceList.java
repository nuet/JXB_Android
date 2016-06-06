package com.lenso.jixiangbao.bean;

/**
 * Created by king on 2016/6/3.
 */
public class ChoiceList {
    private String name;
    private String account;
    private String account_yes;
    private String apr;
    private String time_limit;
    private String time_limit_day;
    private String isday;
    private String id;
    public ChoiceList(){

    }

    public ChoiceList(String name, String account, String account_yes, String apr, String time_limit, String time_limit_day, String isday, String id) {
        this.name = name;
        this.account = account;
        this.account_yes = account_yes;
        this.apr = apr;
        this.time_limit = time_limit;
        this.time_limit_day = time_limit_day;
        this.isday = isday;
        this.id = id;
    }

    public String getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(String time_limit) {
        this.time_limit = time_limit;
    }

    public String getTime_limit_day() {
        return time_limit_day;
    }

    public void setTime_limit_day(String time_limit_day) {
        this.time_limit_day = time_limit_day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount_yes() {
        return account_yes;
    }

    public void setAccount_yes(String account_yes) {
        this.account_yes = account_yes;
    }

    public String getApr() {
        return apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getIsday() {
        return isday;
    }

    public void setIsday(String isday) {
        this.isday = isday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
