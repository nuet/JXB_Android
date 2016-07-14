package com.lenso.jixiangbao.bean;

/**
 * Created by Chung on 2016/7/14.
 */
public class TransferList {
    private String name;
    private String value;
    private String price;
    private String saled;
    private String apr;
    private String time_limit;
    private String id;

    public TransferList() {
    }

    public TransferList(String name, String value, String price, String saled, String apr, String time_limit, String id) {
        this.name = name;
        this.value = value;
        this.price = price;
        this.saled = saled;
        this.apr = apr;
        this.time_limit = time_limit;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSaled() {
        return saled;
    }

    public void setSaled(String saled) {
        this.saled = saled;
    }

    public String getApr() {
        return apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(String time_limit) {
        this.time_limit = time_limit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
