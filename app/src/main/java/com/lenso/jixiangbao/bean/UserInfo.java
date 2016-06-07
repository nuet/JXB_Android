package com.lenso.jixiangbao.bean;

/**
 * Created by Chung on 2016/6/7.
 */
public class UserInfo {
    private String status;
    private String rsmsg;
    private int unreadmsg;
    private int signed;
    private Detailuser detailuser;
    private Summary summary;

    public UserInfo() {
    }

    public UserInfo(int unreadmsg, int signed, UserInfo.Detailuser detailuser, UserInfo.Summary summary) {
        this.unreadmsg = unreadmsg;
        this.signed = signed;
        this.detailuser = detailuser;
        this.summary = summary;
    }

    public String getRsmsg() {
        return rsmsg;
    }

    public void setRsmsg(String rsmsg) {
        this.rsmsg = rsmsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUnreadmsg() {
        return unreadmsg;
    }

    public void setUnreadmsg(int unreadmsg) {
        this.unreadmsg = unreadmsg;
    }

    public int getSigned() {
        return signed;
    }

    public void setSigned(int signed) {
        this.signed = signed;
    }

    public UserInfo.Detailuser getDetailuser() {
        return detailuser;
    }

    public void setDetailuser(UserInfo.Detailuser detailuser) {
        this.detailuser = detailuser;
    }

    public UserInfo.Summary getSummary() {
        return summary;
    }

    public void setSummary(UserInfo.Summary summary) {
        this.summary = summary;
    }

    public static class Detailuser{
        private String username;
        private String typename;
        private String real_status;

        public Detailuser(String username, String typename, String real_status) {
            this.username = username;
            this.typename = typename;
            this.real_status = real_status;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public String getReal_status() {
            return real_status;
        }

        public void setReal_status(String real_status) {
            this.real_status = real_status;
        }
    }

    public static class Summary{
        private double accountUseMoney;
        private double collectTotal;
        private double collectInterest;

        public Summary(double accountUseMoney, double collectTotal, double collectInterest) {
            this.accountUseMoney = accountUseMoney;
            this.collectTotal = collectTotal;
            this.collectInterest = collectInterest;
        }

        public double getAccountUseMoney() {
            return accountUseMoney;
        }

        public void setAccountUseMoney(double accountUseMoney) {
            this.accountUseMoney = accountUseMoney;
        }

        public double getCollectTotal() {
            return collectTotal;
        }

        public void setCollectTotal(double collectTotal) {
            this.collectTotal = collectTotal;
        }

        public double getCollectInterest() {
            return collectInterest;
        }

        public void setCollectInterest(double collectInterest) {
            this.collectInterest = collectInterest;
        }
    }
}
