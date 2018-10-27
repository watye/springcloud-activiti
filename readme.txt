版本升级配置

v1.0 SpringBoot集成Activiti Modeler(v5.22.0)

1)下载activiti-5.22源码
2)进入源码文件中的modules\activiti-webapp-explorer2\src\main\webapp目录，复制diagram-viewer、editor-app、modeler.html三个文件到springboot项目中的webapp目录下
3)复制ModelEditorJsonRestResource.java,ModelSaveRestResource.java,StencilsetRestResource.java到controller包下，这三个类添加RequestMapping(value = "/service")注解
4）pom.xml添加依赖
	<dependency>
	  <groupId>org.activiti</groupId>
	  <artifactId>activiti-spring-boot-starter-basic</artifactId>
	  <version>${activiti.version}</version>
	</dependency>
	<dependency>
	  <groupId>org.activiti</groupId>
	  <artifactId>activiti-modeler</artifactId>
	  <version>${activiti.version}</version>
	</dependency>
5)springboot启动类添加如下注解，关闭security功能
	@EnableAutoConfiguration(exclude = {
	        org.activiti.spring.boot.SecurityAutoConfiguration.class,
	})
6）修改app-cfg.js配置文件，去掉上下文件路径
ACTIVITI.CONFIG = {
    'contextRoot' : '/service',
};
7)resources目录下添加stencilset.json
8)application.yml添加启动时不检查流程文件配置
spring：
  activiti:
    check-process-definitions: false #启动时不检查流程文件
9)添加SecurityConfiguration配置类禁用csrf功能，否则http的put方法报403错误
10)ModelSaveRestResource类的saveModel方法参数修改成如下，否则报不到参数
  saveModel(@PathVariable String modelId, String json_xml,String svg_xml,String name,String description)
11)编写测试类ActivitiController，访问测试类方法localhost:9000/activiti/create，可以看到流程设计编辑页面
