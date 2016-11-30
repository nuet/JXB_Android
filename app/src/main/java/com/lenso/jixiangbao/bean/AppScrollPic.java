package com.lenso.jixiangbao.bean;

import com.lenso.jixiangbao.api.ServerInterface;

/**
 * Created by king on 2016/6/3.
 */
public class AppScrollPic {
    private String id;
    private String pic;
    private String name;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = ServerInterface.SERVER + pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
