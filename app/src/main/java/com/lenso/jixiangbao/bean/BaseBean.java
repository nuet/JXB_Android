package com.lenso.jixiangbao.bean;

import java.util.List;

/**
 * Created by king on 2016/5/10.
 */
public class BaseBean {
    private List<AppScrollPic> appScrollPic;
    private List<ChoiceList> xinList;
    private List<ChoiceList> sanList;
    private InvestList investList;
    private RightList rightList;
    private String statistic_display;
    private String new_experience_apr;
    private String new_experience_valid_time;
    private int versionCode;
    private String android_url;
    private String share_desc;
    private String notice_txt;
    private String notice_url;
    private PlatformFinancialReport platformFinancialReport;

    public String getNotice_txt() {
        return notice_txt;
    }

    public void setNotice_txt(String notice_txt) {
        this.notice_txt = notice_txt;
    }

    public String getNotice_url() {
        return notice_url;
    }

    public void setNotice_url(String notice_url) {
        this.notice_url = notice_url;
    }

    public String getShare_desc() {
        return share_desc;
    }

    public void setShare_desc(String share_desc) {
        this.share_desc = share_desc;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getAndroid_url() {
        return android_url;
    }

    public void setAndroid_url(String android_url) {
        this.android_url = android_url;
    }

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

    public InvestList getInvestList() {
        return investList;
    }

    public void setInvestList(InvestList investList) {
        this.investList = investList;
    }

    public RightList getRightList() {
        return rightList;
    }

    public void setRightList(RightList rightList) {
        this.rightList = rightList;
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

    public static class PlatformFinancialReport{
        private String recharge_total;
        private String cash_total;
        private String tender_total;
        private String repay_total;
        private String new_user_total;
        private String interest_total;

        public PlatformFinancialReport(String recharge_total, String cash_total, String tender_total, String repay_total, String new_user_total, String interest_total) {
            this.recharge_total = recharge_total;
            this.cash_total = cash_total;
            this.tender_total = tender_total;
            this.repay_total = repay_total;
            this.new_user_total = new_user_total;
            this.interest_total = interest_total;
        }

        public String getRecharge_total() {
            return recharge_total;
        }

        public void setRecharge_total(String recharge_total) {
            this.recharge_total = recharge_total;
        }

        public String getCash_total() {
            return cash_total;
        }

        public void setCash_total(String cash_total) {
            this.cash_total = cash_total;
        }

        public String getTender_total() {
            return tender_total;
        }

        public void setTender_total(String tender_total) {
            this.tender_total = tender_total;
        }

        public String getRepay_total() {
            return repay_total;
        }

        public void setRepay_total(String repay_total) {
            this.repay_total = repay_total;
        }

        public String getNew_user_total() {
            return new_user_total;
        }

        public void setNew_user_total(String new_user_total) {
            this.new_user_total = new_user_total;
        }

        public String getInterest_total() {
            return interest_total;
        }

        public void setInterest_total(String interest_total) {
            this.interest_total = interest_total;
        }
    }

    public PlatformFinancialReport getPlatformFinancialReport() {
        return platformFinancialReport;
    }

    public void setPlatformFinancialReport(PlatformFinancialReport platformFinancialReport) {
        this.platformFinancialReport = platformFinancialReport;
    }
}
