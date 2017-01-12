package cn.edu.gcu.customer.service;
import cn.edu.gcu.customer.dao.CustomerDao;
import cn.edu.gcu.customer.domain.Customer;
import cn.edu.gcu.customer.domain.PageBean;
/**
 * 业务逻辑层
 * @author zch
 *
 */
public class CustomerService {
	//依赖dao层
	private CustomerDao customerDao = new CustomerDao();
	/**
	 * 添加客户
	 * @param c
	 */
	public void add(Customer c){
		customerDao.add(c);
	}
	/**
	 * 查询所有客户
	 */
//	public List<Customer> findAll(){
//		return customerDao.findAll();
//	}
	/**
	 * 加载客户
	 * @param cid
	 * @return
	 */
	public Customer load(String cid){
		return customerDao.findByCid(cid);
	}
	/**
	 * 编辑客户
	 * @param c
	 */
	public void edit(Customer c){
		customerDao.edit(c);
	}
	public void del(String cid){
		customerDao.del(cid);
	}
	/**
	 * 高级查询
	 */
//	public List<Customer> query(Customer c){
//		
//		return customerDao.query(c);
//	}
	/**
	 * 查找所有客户(分页显示)
	 * @param pageCode
	 * @param pageSize
	 * @return
	 */
	public PageBean<Customer> findAll(int pageCode, int pageSize) {
		return customerDao.finAll(pageCode, pageSize);
	}
	/**
	 * 高级查询(分页显示)
	 * @param criteria
	 * @param pageCode
	 * @param pageSize
	 * @return
	 */
	public PageBean<Customer> query(Customer criteria, int pageCode, int pageSize) {
		return customerDao.query(criteria, pageCode, pageSize);
	}
}
