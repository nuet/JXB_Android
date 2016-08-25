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
    private Account account;

    public UserInfo() {
    }

    public UserInfo(int unreadmsg, int signed, UserInfo.Detailuser detailuser, UserInfo.Summary summary, UserInfo.Account account) {
        this.unreadmsg = unreadmsg;
        this.signed = signed;
        this.detailuser = detailuser;
        this.summary = summary;
        this.account = account;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public static class Detailuser{
        private String phone;
        private String realname;
        private String username;
        private String typename;
        private String real_status;
        private String paypassword;

        public String getPaypassword() {
            return paypassword;
        }

        public void setPaypassword(String paypassword) {
            this.paypassword = paypassword;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Detailuser(String username, String typename, String real_status) {
            this.username = username;
            this.typename = typename;
            this.real_status = real_status;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
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
        private double collectTotal;
        private double collectInterest;
        private double total_income;

        public Summary(double collectTotal, double collectInterest, double total_income) {
            this.collectTotal = collectTotal;
            this.collectInterest = collectInterest;
            this.total_income = total_income;
        }

        public double getTotal_income() {
            return total_income;
        }

        public void setTotal_income(double total_income) {
            this.total_income = total_income;
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

    public static class Account{
        private String user_id;
        private double total;
        private double use_money;
        private String bankaccount;

        public Account(String user_id, double total, double use_money){
            this.user_id = user_id;
            this.total = total;
            this.use_money = use_money;
        }

        public String getBankaccount() {
            return bankaccount;
        }

        public void setBankaccount(String bankaccount) {
            this.bankaccount = bankaccount;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public double getUse_money() {
            return use_money;
        }

        public void setUse_money(double use_money) {
            this.use_money = use_money;
        }
    }
}
