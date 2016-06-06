package com.lenso.jixiangbao.bean;

import java.util.List;

/**
 * Created by king on 2016/5/10.
 */
public class BaseBean {
    private List<AppScrollPic> appScrollPic;
    private List<ChoiceList> xinList;
    private List<ChoiceList> sanList;
    private List<ChoiceList> borrowList;
    private String statistic_display;
    private String new_experience_apr;
    private String new_experience_valid_time;

    public String getStatistic_display() {
        return statistic_display;
    }

    public void setStatistic_display(String statistic_display) {
        this.statistic_display = statistic_display;
    }

    public String getNew_experience_apr() {
        return new_experience_apr;
    }

    public void setNew_experience_apr(String new_experience_apr) {
        this.new_experience_apr = new_experience_apr;
    }

    public String getNew_experience_valid_time() {
        return new_experience_valid_time;
    }

    public void setNew_experience_valid_time(String new_experience_valid_time) {
        this.new_experience_valid_time = new_experience_valid_time;
    }

    public List<ChoiceList> getBorrowList() {
        return borrowList;
    }

    public void setBorrowList(List<ChoiceList> borrowList) {
        this.borrowList = borrowList;
    }

    public List<AppScrollPic> getAppScrollPic() {
        return appScrollPic;
    }

    public void setAppScrollPic(List<AppScrollPic> appScrollPic) {
        this.appScrollPic = appScrollPic;
    }

    public List<ChoiceList> getXinList() {
        return xinList;
    }

    public void setXinList(List<ChoiceList> xinList) {
        this.xinList = xinList;
    }

    public List<ChoiceList> getSanList() {
        return sanList;
    }

    public void setSanList(List<ChoiceList> sanList) {
        this.sanList = sanList;
    }
}
