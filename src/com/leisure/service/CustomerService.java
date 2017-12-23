package com.leisure.service;

import com.leisure.bean.CustomerBean;
import com.leisure.bean.PageBean;
import com.leisure.dao.CustomerDao;
import com.leisure.util.IDUtil;

import java.util.List;

/**
 *
 * 用于处理业务逻辑,成为一个中间变量
 */
public class CustomerService {
    private CustomerDao customerDao = new CustomerDao(); //中介
    public boolean addCustomer(CustomerBean bean){//CustomerBean bean 类和类的对象作为参数
        bean.setCid(IDUtil.randomId());//设定ID
        return customerDao.addCustomer(bean);//添加ID
    }

    public List<CustomerBean> findAll(){
        return customerDao.findAll();
    }
    public boolean delCustomer(String cid){
        return customerDao.delCustomer(cid);
    }
    public List<CustomerBean> queryByCondition(CustomerBean customer){
        return  customerDao.queryByCondition(customer);
    }
    public PageBean findAll(int pc,int ps){//分页查询
        return  customerDao.findAll(pc ,ps);
    }
}
