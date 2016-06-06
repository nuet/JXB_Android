package com.lenso.jixiangbao.imodel;

import com.lenso.jixiangbao.api.ServerInterface;
import com.lenso.jixiangbao.http.VolleyHttp;

/**
 * Created by king on 2016/6/3.
 */
public class BaseModel implements IBaseModel {
    public void load1(){
        VolleyHttp.getInstance().getJson(ServerInterface.ALL_LIST, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {

            }
        });
    }
}
