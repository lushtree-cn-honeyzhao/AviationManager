# AviationManager［机票销售管理系统］
一级经销商将机票转销给二级经销商 之间的过程 流程处理


#部署说明#
##1)	目录
	/resources 配置文件目录<br/>
	/core 共用核心源代码 <br/>
	/src应用源代码 <br/>
	/codegen 代码生成器 <br/>
	/test 单元测试目录 <br/>
	/doc 一些帮助文档 以及核心包api文档 <br/>
	/WebRoot jsp等页面文件夹 <br/>
	注：resources、src、core、WebRoot是项目必须导入的目录。如果没有core目录，那么一般WebRoot\WEB-INF\lib目录下一般会包含AviationManagerSystem_core_*.jar这个文件也是可以的，<br/>
	对应的源代码放置于doc目录下（AviationManagerSystem_core _*_resource.jar）<br/>
##2)  部署说明
  a)安装基础环境：数据库、IDE（Intellij IDEA、Eclipse等）、JDK6、Tomcat6 <br/>
  b)新建数据库（UTF-8编码），导入数据库脚本，脚本在/resources/data/目录之下，一般可以使用Navicat等数据库客户端工具直接导入。<br/>
  c)Eclipse可以新建一个动态的web工程、复制resources、src、core三个文件夹到工程根目录, WebRoot下的所有文件到jsp目录之下（eclipse默认应该是WebContent目录），<br/>
  在工程的属性配置中需要把resources、core加入为源代码目录（注：eclipse默认生成的只有src为源代码目录）。<br/>
  Intellij IDEA可以通过向导的方式直接导入，比较智能，很方便使用，具体的就不介绍了。<br/>
  d)部署到Tomcat。<br/>

##3)	环境要求
	运行环境:JDK1.6 Tomcat6<br/>
	数据库:MySQL5.1 Oracle10G <br/>
	(/resources/data/ 目录下有相关脚本 建议使用Navicat导入) <br/>
	dmp导入(表空间名:AviationManagerSystem_data 临时表空间:AviationManagerSystem_temp)<br/>

##4)	主要技术
    ###后台:
    Struts2.3.16.1<br/>
	  Spring3.1.14<br/>
	  Hibernate3.6.10<br/>
	  Jackson2.2.0<br/>
	  EhCache<br/>
	  Hibernate validator4.3.1(后台校验框架,基于BO注解)<br/>
    ###前台:
    jQuery1.10.2<br/>
    jQuery EasyUI1.3.5(前台ui组件)<br/>
