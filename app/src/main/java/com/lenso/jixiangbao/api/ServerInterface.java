package com.lenso.jixiangbao.api;

/**
 * Created by Chung on 2016/5/27.
 */
public class ServerInterface {

    public final static String SERVER = "http://app.pongyoo.com/";
    public final static String SERVER_DOMAIN = "http://app.pongyoo.com/app/";

    public final static String SERVER_ISPHONEREGISTER = SERVER_DOMAIN + "isphonereged.html";
    public final static String SERVER_GETPHONECODE = SERVER_DOMAIN + "getphonecode.html";
    public final static String SERVER_BANKLIST = SERVER_DOMAIN + "getbanklist.html";
    public final static String SERVER_LOGIN = SERVER_DOMAIN + "login.html";
    public final static String SERVER_LOGOUT = SERVER_DOMAIN + "logout.html";
    public final static String SERVER_REGISTER = SERVER_DOMAIN + "register.html";
    public final static String SERVER_REALNAME = SERVER_DOMAIN + "realname.html";
    public final static String SERVER_BINDCARD = SERVER_DOMAIN + "addbank.html";
    public final static String SERVER_USERINFO = SERVER_DOMAIN + "account.html";
    public final static String SERVER_USERSIGN = SERVER_DOMAIN + "usersign.html";

    public final static String SERVER_FORGETPSW = SERVER_DOMAIN + "getpwdverify.html";
    public final static String SERVER_SETPSW = SERVER_DOMAIN + "getpwd.html";

    public static final String ALL_LIST = SERVER_DOMAIN+"getallsystemlist.html";
    public static final String INVEST_LIST = SERVER_DOMAIN+"investlist.html";//债权列表
    public static final String RIGHT_LIST = SERVER_DOMAIN+"rightlist.html";//转让列表
    public static final String ALL_DATA = SERVER_DOMAIN+"getallsystemdata.html";

    public final static String LOAN_CONFIRM = SERVER_DOMAIN + "apply.html";

    public final static String SET_HEAD_PIC = SERVER_DOMAIN + "setavatar.html";
    public final static String GET_HEAD_PIC = SERVER + "imgurl.html";

    public final static String GET_SPLASH_PIC = SERVER + "themes/theme_default/app/images/startup.jpg";
    public final static String SHARE_PIC = SERVER + "themes/theme_default/app/images/share.jpg";
    public final static String SHARE_LINK = SERVER + "mobile/register.html";

    public final static String FINANCIAL_REPORTS = SERVER_DOMAIN + "getallsystemstatis.html";

    public final static String RECHARGE = SERVER + "app/newrecharge.html";//充值

    public final static String TENDER = SERVER_DOMAIN + "tender.html";//债权列表投标
    public final static String TENDER_RIGHT = SERVER_DOMAIN + "tenderright.html";//转让列表投标


    public final static String GET_USER_INFO = SERVER_DOMAIN + "getuserinfo.html";//用户信息


}
