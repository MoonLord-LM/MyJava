# MyJava
MyJava

# 笔记
低版本的 JDK 不支持 AES-GCM-256 等高强度算法的问题：  
https://www.oracle.com/java/technologies/javase-jce-all-downloads.html  

## 说明

#### 1. 使用  spring-boot-starter-test  进行单元测试
注解使用：@SpringBootTest @RunWith(Enclosed.class) @Test  
每一个工具类，创建一个对应的测试类  
每一个方法，在测试类中创建一个对应的静态内部类  
每一个测试用例，在静态内部类中创建一个对应的测试方法

#### 2. 对比 Apache Commons Codec 进行性能测试
