# CustomerSystem
这是一个可以添加客户信息并存储到数据库中,对客户信息进行增删改以及搜索的一个简单系统.
----------
##一.功能简介<br/>
###1.添加客户:<br/>
把客户信息持久化到数据库中;<br/>
###2.查询所有客户:<br/>
把数据库中的所有客户信息查找出来,并以分页列表显示出来,并可对客户进行编辑和删除等操作;<br/>
###3.高级搜索:<br/>
多组合形式在数据库中对特定信息的客户进行查询,以搜索结果分页列表显示出来,并可对客户进行编辑和删除等操作.<br/>

##二.运用知识点<br/>
###1.sql语法对数据库进行基本查询;<br/>
###2.数据库连接池cp30;<br/>
###3.dbUtils数据库工具包的运用;<br/>
###3.JavaWeb的MVC设计模式(jsp/servlet/jdbc).<br/>

##三.业务流程<br/>

![image](https://github.com/zchdjb/CustomerSystem/raw/master/WebContent/readmeimage/add.png)<br/>

![image](https://github.com/zchdjb/CustomerSystem/raw/master/WebContent/readmeimage/queryAll.png)<br/>

![image](https://github.com/zchdjb/CustomerSystem/raw/master/WebContent/readmeimage/del.png)<br/>

![image](https://github.com/zchdjb/CustomerSystem/raw/master/WebContent/readmeimage/edit.png)<br/>

![image](https://github.com/zchdjb/CustomerSystem/raw/master/WebContent/readmeimage/query.png)<br/>

##四.重点分析<br/>
###1.底层数据库结构分析图<br/>
![image](https://github.com/zchdjb/CustomerSystem/raw/master/WebContent/readmeimage/database.png)<br/>


###2.分页设计<br/>

####1).需要用一个pageBean对象来装载分页信息,该bean需要有当前页码page code,总记录数total record,每页记录数page size,当前页的记录BeanList,其中还需要设置一个String url属性,用户保存客户的分页请求信息,因为当使用多条件查询后，然后在点击第2 页时，这个第2页超链接没有条件了，所以会丢失条件，所以我们需要在页面上的所有链接都要保留条件！我们要把条件以一个字符串的形式保存到PageBean的url中！这个任务交给Servlet！<br/>

####2).计算公式;<br/>
参数:pc当前页码,begin开始页码,end结束页码<br/>

假设最多可显示10个页码,那么:<br/>
          begin=pc-5<br/>
          end=pc+4<br/>

####3).问题分析:<br/>
- 如果总页数<=10（列表长度），那么begin=1，end=总页数<br/>
- 使用公式计算；begin=pc-5, end=pc + 4；<br/>
- 头溢出：当begin<1时，begin=1<br/>
- 尾溢出：当end>${tp}时，end=${tp}<br/>
