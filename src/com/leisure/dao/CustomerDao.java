package com.leisure.dao;

import com.leisure.bean.CustomerBean;
import com.leisure.bean.PageBean;
import com.leisure.util.IDUtil;
import com.leisure.util.MyQueryRunner;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao是用于操作数据库的
 */
public class CustomerDao {
    private MyQueryRunner runner = new MyQueryRunner(); //MyQueryRunner初始化,私有化,全局变量
    private int flag;// 全局变量

    //添加
    public boolean addCustomer(CustomerBean bean) {//实体类CustomerBean作为参数,提供属性get方法
        String sql = "insert into t_customer values(?,?,?,?,?,?,?)"; //添加value值,?用来占格,所以不用key值了,直接对号入座
        try {
            flag = runner.update(
                    sql,
                    bean.getCid(),
                    bean.getCname(),
                    bean.getGender(),
                    bean.getBirthday(),
                    bean.getCellphone(),
                    bean.getEmail(),
                    bean.getDescription());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (flag > 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    //查找
    public List<CustomerBean> findAll() { //查询所有
        List<CustomerBean> customerBeans = new ArrayList<>(); //建个数组
        String sql = "select * from t_customer"; //执行sql语句
        try {
            customerBeans = runner.query(sql,
                    new BeanListHandler<>(CustomerBean.class));//查出来以集合的形式返回

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerBeans;
    }

    //删除
    public boolean delCustomer(String cid) {//删除,需要通过cid查询 所以写一个cid作为参数
        String sql = "delete from t_customer where cid = ?";

        try {
            flag = runner.update(sql, cid);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (flag > 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    //条件查询(高级查询)
    public List<CustomerBean> queryByCondition(CustomerBean customerBean) {//条件查询
        String cname = customerBean.getCname();//获得实体类CustomerBean的属性
        String gender = customerBean.getGender();
        String cellphone = customerBean.getCellphone();
        String email = customerBean.getEmail();

        List<String> params = new ArrayList<>(); //用于存放判断的条件
        StringBuffer sql = new StringBuffer("select *from t_customer where 1=1 "); //String类不可改变,而数据不一定是什么类型,
        //所以用StringBuffer,可以改变.1=1是用来解决拼接and的问题
        if (!IDUtil.isEmpty(cname)) { //如果不为空
            sql.append(" and cname like ? "); //like模糊查询 后加空格不能忘记 ,sql语句都要空格隔开!!
            params.add("%" + cname + "%");// %%中间,查询是否包含.是模糊查询特有的
        }
        if (!IDUtil.isEmpty(gender)) {
            sql.append(" and gender = ? ");
            params.add(gender);

        }
        if (!IDUtil.isEmpty(cellphone)) {
            sql.append(" and cellphone like ? ");
            params.add("%" + cellphone + "%");

        }
        if (!IDUtil.isEmpty(email)) {
            sql.append(" and email like ? ");
            params.add("%" + email + "%");
        }
        List<CustomerBean> customerBeenList = new ArrayList<>();
        try {
            customerBeenList = runner.query(sql.toString(),// StringBuffer类型转回Sting类型,sql语句才能执行
                    new BeanListHandler<>(CustomerBean.class),//这行固定写法:BeanListHandler是用来线程传输数据的
                    params.toArray()); //toArray转成数字
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerBeenList;
    }


    //修改文档


    //分页查询
    //pageCode代表要查哪一页
    //pageSize代表每一页显示多少条数据
    public PageBean<CustomerBean> findAll(int pageCode, int pageSize) {
        PageBean<CustomerBean> pageBean = new PageBean<>();//用于存放每一页的数据
        pageBean.setPc(pageCode);
        pageBean.setPs(pageSize);
        String sql = "select count(*) from t_customer";//获取所有的数量
        try {
            //查询所有数据的数量
            Long sum = runner.query(sql, new ScalarHandler<>());//返回值为Long类型
            pageBean.setTr(sum.intValue());//转成int类型并设定

            sql = "select * from t_customer limit ?,?"; //第一个?是起始位置索引 第二个?是数据条数
            int from =(pageCode -1) *pageSize; //起始位置索引
            int num =pageSize;
            Object[] params ={from,num};//此数组作用用于存放参数
            List<CustomerBean> customerBeans =runner.query(sql,
                    new BeanListHandler<>(CustomerBean.class),params);
            pageBean.setBeanList(customerBeans);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  pageBean;
    }

    //循环加假数据
    @Test //alt+回车选第一个
    public void addTest(){
        CustomerDao dao = new CustomerDao();
        for (int i = 1; i <= 300; i++) {
            CustomerBean c = new CustomerBean();
            c.setCid(IDUtil.randomId());
            c.setCname("cust_"+i);
            c.setBirthday("1975-08-21");
            c.setCellphone("1008611"+i);
            c.setGender(i % 2 == 0? "男":"女");
            c.setEmail("cust_"+i+"@lanou.com");
            c.setDescription("cust_"+i+"的描述信息");
            dao.addCustomer(c);

        }
    }
}
