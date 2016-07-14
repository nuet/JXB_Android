package com.lenso.jixiangbao.bean;

import java.util.List;

/**
 * Created by Chung on 2016/7/14.
 */
public class RightList {
    private List<TransferList> rtList;
    private Page page;

    public List<TransferList> getRtList() {
        return rtList;
    }

    public void setRtList(List<TransferList> rtList) {
        this.rtList = rtList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public static class Page{
        private int pages;

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }
    }
}
