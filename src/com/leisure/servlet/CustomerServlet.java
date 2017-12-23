package com.leisure.servlet;

import com.leisure.bean.CustomerBean;
import com.leisure.bean.PageBean;
import com.leisure.service.CustomerService;
import com.leisure.util.BeanUtil;
import com.leisure.util.IDUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 参数为主导,需要什么参数用什么,不清楚的用超类,或者泛型.返回值类型,类与类的关联,先用String贯穿,不合适再改
 */
@WebServlet(name = "CustomerServlet", urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    private CustomerService service = new CustomerService();//全局变量,初始化

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf8");
        response.setCharacterEncoding("utf8");
        request.setCharacterEncoding("utf8"); //更改编码格式
        String method = request.getParameter("method");
        switch (method) {//4个功能
            case "add": //添加用户,引号的值要作为value写在jsp文件的标签中
                addCustomer(request, response); //方法+参数,cmd+回车
                break;
            case "query": //高级查询
               findAll(request, response);
                break;
            case "del": //删除用户
                delCustomer(request, response);
                break;
            case "queryByCondition": //条件查询
                queryByCondition(request, response);
                break;

        }

    }

    private String getUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();//获取项目名
        String servletPath = request.getServletPath();//获取servlet路径
        String queryString = request.getQueryString();//获取参数内容 就是后面的东西

        if (queryString.contains("&pc=")) { //如果包含
            //找到最后一个"&pc="出现的位置
            int index = queryString.lastIndexOf("&pc="); //截取数字
            //截取参数内容
            /**
             *  例子  :/customer?method=finAll&pc=1
             *  queryString方法获取的是 method=finAll
             *  &pc=1这一段内容是在时刻变化的 所以我们不想要
             *  先索引&pc=1的位置 然后截取前半段
             *  最终的结果就是method=finAll
             *  想要完整的url地址
             *  项目名+servlet地址+?+method=findAll;
             */

            queryString = queryString.substring(0, index);


        }
        return contextPath + servletPath + "?" + queryString;
    }

    //添加用户
    //方法提交:在add.jsp 67行加 <input type="hidden" name="method" value="add">
    private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //用户存放前端所提供的信息
        CustomerBean customerBean = new CustomerBean();//实体类
        BeanUtil.requestToBean(request, customerBean); //工具类调方法,填入对象做参数  ?
        boolean flag = service.addCustomer(customerBean);//逻辑处理CustomerService类调方法,添加实体类CustomerBean的参数是否实现
        if (flag) {
            request.setAttribute("msg", "添加数据成功");//实现

        } else {
            request.setAttribute("msg", "添加数据失败");//失败
        }
        request.getRequestDispatcher("/msg.jsp")
                .forward(request, response);
    }
    //高级查询  未分页之前,取出所有的数据方法
    //方法提交:在top.jsp 25行 给a标签 一个 value='/customer?method=query'
  private void findAll(HttpServletRequest request, HttpServletResponse response) {
//        List<CustomerBean> beanList = service.findAll();
//        request.setAttribute("customers", beanList);
//        try {
//            request.getRequestDispatcher("/list.jsp").forward(request, response);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//

      /**
       * 分页查询
       *
       */
      //获取页码数
      int pc =getPc(request);
      int ps =10; //每页显示的数据条数
      PageBean<CustomerBean> pageBean = service.findAll(pc,ps);//用于存放从数据库取出的数据

      pageBean.setUrl(getUrl(request));
      request.setAttribute("pageBean",pageBean);
      try {
          request.getRequestDispatcher("/list.jsp").forward(request,response);
      } catch (ServletException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
    }


    //删除数据
    // list.jsp中foreach循环遍历显示等和删除 加个 value='/customer?method=del&&cid=${customer.cid}
    private void delCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        boolean flag = service.delCustomer(cid);
        if (flag) {
            request.setAttribute("msg", "删除成功");
        } else {
            request.setAttribute("msg", "删除失败");
        }
        request.getRequestDispatcher("/msg.jsp").forward(request, response);
    }

    //条件查询
    //方法提交 :在query.jsp中加个隐藏的input,在22行
    private void queryByCondition(HttpServletRequest request, HttpServletResponse response) {
        CustomerBean customerBean = new CustomerBean();
        BeanUtil.requestToBean(request, customerBean);
        List<CustomerBean> list = service.queryByCondition(customerBean);
        request.setAttribute("customers", list);//发到前端
        try {
            request.getRequestDispatcher("/list.jsp").forward(request, response);//请求转发


        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //获取前端发送的页码数
    public int getPc(HttpServletRequest request) {
        String pc = request.getParameter("pc");
        int p;
        if (!IDUtil.isEmpty(pc)) {
            p = Integer.valueOf(pc);
        } else {
            p = 1;
        }
        return p;
    }
}
