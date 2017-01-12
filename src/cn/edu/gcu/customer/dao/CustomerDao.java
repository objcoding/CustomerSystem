package cn.edu.gcu.customer.dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.edu.gcu.customer.domain.Customer;
import cn.edu.gcu.customer.domain.PageBean;
import cn.itcast.jdbc.TxQueryRunner;
/**
 * dao层:
 * 底层使用commons.dbutils工具实现对数据库的访问,
 * 使用cn.itcast.jdbc.TxQueryRunner优化了数据库的连接.
 * @author zch
 *
 */
public class CustomerDao{
		private QueryRunner queryRunner = new TxQueryRunner();//依赖
		/**
		 * 增加客户
		 * @param c
		 */
		public void add(Customer c){
			try {
			String sql = "insert into t_customer values(?,?,?,?,?,?,?)";
			Object[] params = {c.getCid(), c.getCname(), c.getGender(),
					c.getBirthday(), c.getCellphone(), c.getEmail(), c.getDescription()};
				queryRunner.update(sql, params);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
//		//查询所有客户
//		public List<Customer> findAll(){
//			try {
//				String sql = "select * from t_customer";
//				List<Customer> c = queryRunner.query(sql, new BeanListHandler<Customer>(Customer.class));
//				return c;
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//		}
		/**
		 * 查询所有客户（分页查询）
		 * @param pageCode
		 * @param pageSize
		 * @return
		 */
		public PageBean<Customer> finAll(int pageCode, int pageSize) {
			try {
				/*
				 * 1，设置pageBean对象pb
				 * 2，设置pageBean的pageCode和totalSize
				 * 3，设置totalRecord
				 * 4，得到beanList，给pageBean
				 * 5，返回pageBean
				 */
				PageBean<Customer> pb = new PageBean<Customer>();
				pb.setPageCode(pageCode);
				pb.setPageSize(pageSize);
				/*
				 * 求totalRecord
				 */
				String sql = "select count(*) from t_customer";
				Number tr = (Number)queryRunner.query(sql, new ScalarHandler());
				int totalRecord = tr.intValue();
				pb.setTotalRecord(totalRecord);
				/*
				 * 设置beanList,通过pageCode和pageSize
				 */
				String sqlList = "select * from t_customer order by cname limit ?,?";
				List<Customer> cList = queryRunner.query(sqlList, 
						new BeanListHandler<Customer>(Customer.class),
						(pageCode - 1) * pageSize , pageSize);
				pb.setBeanList(cList);
				return pb;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		/**
		 * 通过客户cid查找相关客户
		 * @param cid
		 * @return
		 */
		public Customer findByCid(String cid){
			String sql = "select * from t_customer where cid=?";
			try {
				Customer c = queryRunner.query(sql, new BeanHandler<Customer>(Customer.class),cid);
				return c;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		/**
		 * 编辑客户信息
		 * @param c
		 */
		public void edit(Customer c){
			try{
			String sql = "update t_customer set cname=?,gender=?,birthday=?,cellphone=?,email=?,description=? where cid=?";
			Object[] params = {c.getCname(), c.getGender(),
					c.getBirthday(), c.getCellphone(), c.getEmail(), c.getDescription(), c.getCid()};
				queryRunner.update(sql, params);
			}catch(SQLException e){
				throw new RuntimeException(e);
			}
		}
		//删除客户
		public void del(String cid){
			try {
				String sql = "delete from t_customer where cid=?";
				queryRunner.update(sql, cid);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		//高级查询
//		public List<Customer> query(Customer c){
//			try {
//				//给定参数
//				List<Object> params = new ArrayList<Object>();
//				//给定字符串缓冲区
//				StringBuffer sql = new StringBuffer("select * from T_customer where 1=1 ");//给一个无用参数把where写进去
//				
//				String cname = c.getCname();
//				String gender = c.getGender();
//				String cellphone = c.getCellphone();
//				String email = c.getEmail();
//				
//				if(cname != null && !(cname.trim().isEmpty())){
//					sql.append("and cname like ?");
//					params.add("%" + cname + "%");
//				}
//				if(gender != null && !(gender.trim().isEmpty())){
//					sql.append("and gender=?");
//					params.add(gender);
//				}
//				if(cellphone != null && !(cellphone.trim().isEmpty())){
//					sql.append("and cellphone like ?");
//					params.add("%" + cellphone + "%");
//				}
//				if(email != null && !(email.trim().isEmpty())){
//					sql.append("and email like ?");
//					params.add("%" + email + "%");
//				}
//				
//				return queryRunner.query(sql.toString(),
//						new BeanListHandler<Customer>(Customer.class), params.toArray());
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//		}
		/**
		 * 高级查询(分页)
		 * @param c
		 * @param pageCode
		 * @param pageSize
		 * @return
		 */
		public PageBean<Customer> query(Customer c, int pageCode, int pageSize) {
			
			try {
				/*
				 *1,创建pageBean对象
				 *2,设置已有的属性,pageCode和pageSize
				 *3,得到totalRecord
				 *4,得到beanList
				 */
				PageBean<Customer> pb = new PageBean<Customer>();
				pb.setPageCode(pageCode);
				pb.setPageSize(pageSize);
				/*
				 * 得到pageRecord
				 */
				//给定参数
				List<Object> params = new ArrayList<Object>();
				//给定字符串缓冲区
				StringBuffer cntSql = new StringBuffer("select count(*) from T_customer");//给一个无用参数把where写进去
				StringBuffer whereSql = new StringBuffer(" where 1=1 ");
				
				String cname = c.getCname();
				String gender = c.getGender();
				String cellphone = c.getCellphone();
				String email = c.getEmail();
				/*
				 * where条件
				 */
				if(cname != null && !(cname.trim().isEmpty())){
					whereSql.append(" and cname like ?");
					params.add("%"+cname+"%");
				}
				if(gender != null && !(gender.trim().isEmpty())){
					whereSql.append(" and gender=?");
					params.add(gender);
				}
				if(cellphone != null && !(cellphone.trim().isEmpty())){
					whereSql.append(" and cellphone like ?");
					params.add("%"+cellphone+"%");
				}
				if(email != null && !(email.trim().isEmpty())){
					whereSql.append(" and email like ?");
					params.add("%"+email+"%");
				}
				/*
				 * 执行sql子句得到totalRecord
				 */
				Number num = (Number)queryRunner.query(cntSql.append(whereSql).toString(),
						new ScalarHandler(), params.toArray());
				int totalRecord = num.intValue();
				pb.setTotalRecord(totalRecord);
				/*
				 * sql语句字符缓冲区,查询所有列,
				 */
				StringBuffer sql = new StringBuffer("select * from T_customer ");
				StringBuffer limitSql = new StringBuffer("limit ?,?");
				/*
				 * 需要给出limit后面两个问号的值
				 */
				params.add((pageCode - 1) * pageSize);
				params.add(pageSize);
				
				//生成beanList,通过pageCode和pageSize,并返回
				List<Customer> beanList = queryRunner.query(sql.append(whereSql).append(limitSql).toString(),
						new BeanListHandler<Customer>(Customer.class), params.toArray());
				pb.setBeanList(beanList);
				return pb;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
}
