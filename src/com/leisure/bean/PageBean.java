package com.leisure.bean;

import java.util.List;

/**
 * 每页所存储的内容,分页查询的实体类
 */
public class PageBean<T> { //泛型 什么数据都可以
    //属性1 :pc pageCode 当前的页码
    //属性2 :tp totalPage 总页数
    //属性3 :tr totalRecord 数据的总数
    //属性4 :ps pageSize 每一页的数量
    private int pc, tp, tr, ps;
    //用集合存放每一页的数据
    private List<T> beanList;

    private String url;//要访问的地址

    public int getPc() {
        return pc;
    }

    public PageBean setPc(int pc) {
        this.pc = pc;
        return this;
    }

    public int getTp() {  //jsp中el表达式就使用了这个方法
        tp = tr / ps;//总页数=总数量/每一的数量
        return tr % ps == 0 ? tp : tp + 1;//如果有余数多显示一页
    }

    public PageBean setTp(int tp) {
        this.tp = tp;
        return this;
    }

    public int getTr() {
        return tr;
    }

    public PageBean setTr(int tr) {
        this.tr = tr;
        return this;
    }

    public int getPs() {
        return ps;
    }

    public PageBean setPs(int ps) {
        this.ps = ps;
        return this;
    }

    public List<T> getBeanList() {
        return beanList;
    }

    public PageBean setBeanList(List<T> beanList) {
        this.beanList = beanList;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public PageBean setUrl(String url) {
        this.url = url;
        return this;
    }
}
