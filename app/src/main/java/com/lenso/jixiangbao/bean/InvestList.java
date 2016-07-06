package com.lenso.jixiangbao.bean;

import java.util.List;

/**
 * Created by king on 2016/6/3.
 */
public class InvestList {
    private List<ChoiceList> borrowList;
    private P p;

    public List<ChoiceList> getBorrowList() {
        return borrowList;
    }

    public void setBorrowList(List<ChoiceList> borrowList) {
        this.borrowList = borrowList;
    }

    public P getP() {
        return p;
    }

    public void setP(P p) {
        this.p = p;
    }

    public static class P{
        private int pages;

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }
    }
}
