package cn.edu.gcu.customer.web.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.gcu.customer.domain.Customer;
import cn.edu.gcu.customer.domain.PageBean;
import cn.edu.gcu.customer.service.CustomerService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class CustomerServlet
 */
@WebServlet("/CustomerServlet")
public class CustomerServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private CustomerService customerService=new CustomerService();//依赖业务层
    /**
     * 添加客户
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1，把数据表单封装到一个客户对象中
		 * 2，设置用户cid
		 * 3，调用service的add方法完成添加客户
		 * 4，把添加信息保存到request域中
		 * 5，转发到msg.jsp
		 */
		Customer c = CommonUtils.toBean(request.getParameterMap(), Customer.class);
		c.setCid(CommonUtils.uuid());
		customerService.add(c);
		request.setAttribute("msg", "添加成功");
		return "f:msg.jsp";
	}
	/*
	 * 查询所有用户
	 */
//	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//		/*
//		 * 1，把查找到的所有的用户信息保存的一个list集合中，集合中每个对象代表一个客户
//		 * 2，把集合保存到request域中
//		 * 3，转发到list.jsp
//		 */
//		List<Customer> cList = customerService.findAll();
//		request.setAttribute("cstmList", cList);
//		return "f:list.jsp";
//	}
	
	/**
	 * 查询所有客户(分页显示)
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		/*
		 * 1，获取页面传输的pageCode
		 * 2，给定totalSize
		 * 3，使用pageCode和pageSize调用service方法
		 * 4，得到pageBean后保存到request域，转发到list.jsp
		 */
		int pageCode = getPageCode(request);
		int pageSize = 10;
		PageBean<Customer> pb = customerService.findAll(pageCode, pageSize);
		//截取url
    	pb.setUrl(getUrl(request));
		request.setAttribute("pb", pb);
		return "f:list.jsp";
	}
	
	//获取当前页面并把它转换成int值
	private int getPageCode(HttpServletRequest req){
		String value=req.getParameter("pageCode");
		if(value == null || value.trim().isEmpty()){
			return 1;
		}
		return Integer.parseInt(value);
	}
    /**
     * 编辑客户信息前的加载工作
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String preEdit(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException{
    	/*
    	 * 1，获取客户cid
    	 * 2，调用service的load完成客户信息的加载
    	 * 3，把客户对象保存到request域中
    	 * 4，转发到edit.jsp
    	 */
    	String cid = request.getParameter("cid");
    	Customer c = customerService.load(cid);
    	request.setAttribute("c", c);
    	return "f:edit.jsp";
    }
   /**
    * 编辑客户信息
    * @param request
    * @param response
    * @return
    * @throws ServletException
    * @throws IOException
    */
    public String edit(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException{
    	/*
    	 * 1，封装表单数据到Customer对象中
    	 * 2，调用service的edit方法完成修改
    	 * 3，保存修改信息到request域中
    	 * 4，转发到msg.jsp
    	 */
    	Customer c = CommonUtils.toBean(request.getParameterMap(), Customer.class);
    	customerService.edit(c);
    	request.setAttribute("msg", "修改用户信息成功！");
    	return "f:msg.jsp";
    }
  /**
   * 删除客户
   * @param request
   * @param response
   * @return
   * @throws ServletException
   * @throws IOException
   */
    public String del(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException{
    	/*
    	 * 1，获取客户cid
    	 * 2，调用service层的del方法完成删除客户
    	 * 3，把删除信息保存到request中
    	 * 4，转发到msg.jsp
    	 */
    	String cid = request.getParameter("cid");
    	customerService.del(cid);
    	request.setAttribute("msg", "删除客户成功！");
    	return "f:msg.jsp";
    }
    /*
     * 高级查询
     */
//    public String query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//    	/*
//    	 * 1,封装表单数据
//    	 * 2，调用service层的query方法返回结果
//    	 * 3，保存集合到request域中
//    	 * 4，转发到list.jsp
//    	 */
//    	Customer criteria = CommonUtils.toBean(request.getParameterMap(), Customer.class);
//    	List<Customer> cList = customerService.query(criteria);
//    	request.setAttribute("cstmList", cList);
//    	return "f:list.jsp";
//    }
    
    /**
     * 高级查询(分页显示)
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String query(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException{
    	//System.out.println(getUrl(request));
    	//把请求参数封装到criteria中,
    	Customer criteria = CommonUtils.toBean(request.getParameterMap(), Customer.class);
    	//处理编码问题
    	criteria = encoding(criteria);
    	int pageCode = getPageCode(request);
    	int pageSize = 10;
    	
    	PageBean<Customer> pb = customerService.query(criteria, pageCode, pageSize);
    	//截取url
    	pb.setUrl(getUrl(request));
    	request.setAttribute("pb", pb);
    	return "f:list.jsp";
    }
    /*
     * 处理编码
     */
    private Customer encoding(Customer criteria) throws UnsupportedEncodingException {
		String cname = criteria.getCname();
		String gender = criteria.getGender();
		String cellphone = criteria.getCellphone();
		String email = criteria.getEmail();
		
		if(cname != null && !cname.trim().isEmpty()){
			cname = new String (cname.getBytes("ISO-8859-1"), "UTF-8");
			criteria.setCname(cname);
		}
		if(gender != null && !gender.trim().isEmpty()){
			gender = new String (gender.getBytes("ISO-8859-1"), "UTF-8");
			criteria.setGender(gender);
		}
		if(cellphone != null && !cellphone.trim().isEmpty()){
			cellphone = new String (cellphone.getBytes("ISO-8859-1"),"UTF-8");
			criteria.setCellphone(cellphone);
		}
		if(email != null && !email.trim().isEmpty()){
			email = new String (email.getBytes("ISO-8859-1"),"UTF-8");
			criteria.setEmail(email);
		}
		return criteria;
	}
	/*
     * 截取url:附带请求参数信息
     * 把请求设置为get才能截取
     */
    private String getUrl(HttpServletRequest req){
    	String contextPath = req.getContextPath();
    	String servletPath = req.getServletPath();
    	String queryString = req.getQueryString();
    	
    	//判断参数是否包含pageCode,如果包含,去除
    	if(queryString.contains("&pageCode=")){
    		int index = queryString.lastIndexOf("&pageCode=");
    		queryString = queryString.substring(0,index);
    	}
    	return contextPath+servletPath + "?" + queryString;
    }
}
