# MyJava
My Java Demo Projects

[![Java CI with Maven](https://github.com/MoonLord-LM/MyJava/actions/workflows/maven.yml/badge.svg)](https://github.com/MoonLord-LM/MyJava/actions/workflows/maven.yml)



## 笔记

#### 1. 低版本的 JDK（< 8u161）不支持 AES-GCM-256 等高强度算法的问题：
https://www.oracle.com/java/technologies/javase-jce-all-downloads.html  

#### 1. JDK 只有 AES/CBC/PKCS5Padding 而没有 AES/CBC/PKCS7Padding 的问题：
历史命名问题，实际上 PKCS5Padding 和 PKCS7Padding 是同一种填充算法  
https://crypto.stackexchange.com/questions/9043/what-is-the-difference-between-pkcs5-padding-and-pkcs7-padding  

#### 2. 常用的 AEAD（Authenticated Encryption with Associated Data）算法：
AEAD 算法，在内部逻辑中，同时实现了加解密和认证  
常用的 AEAD 算法：AES-256-GCM（分组）、XChaCha20-IETF-Poly1305（流）  
https://www.google.com/search?q=AEAD+%E6%9C%89%E5%93%AA%E4%BA%9B  

|  算法  |  密钥长度 bit  |  随机数长度 bit  |  分组长度 bit  |  校验值长度 bit  |
|  ----  | ----  | ----  | ----  | ----  |
|  AES-256-GCM  |  256  |  96  |  128  |  128  |
|  ChaCha20-Poly1305-IETF  |  256  |  96  |  512  |  128  |



## 说明

#### 1. 使用  spring-boot-starter-test  进行单元测试
注解使用：@SpringBootTest @RunWith(Enclosed.class) @Test  
每一个工具类，创建一个对应的测试类  
每一个方法，在测试类中创建一个对应的静态内部类  
每一个测试用例，在静态内部类中创建一个对应的测试方法

#### 2. 对比 Apache Commons Codec、Spring Security 进行性能测试
|  本工程函数  |  对比函数  |  开源库  |  性能提升  |
|  ----  | ----  | ----  | ----  |
|  Hex.encode  | Hex.encodeHexString  |  Apache Commons Codec  |  30%  |
|  Hex.decode  | Hex.decodeHex  |  Apache Commons Codec  |  300%  |
|  Hex.encode  | Hex.encode  |  Spring Security  |  10%  |
|  Hex.decode  | Hex.decode  |  Spring Security  |  300%  |



## TODO
ChaCha20-Poly1305-IETF  
https://doc.libsodium.org/secret-key_cryptography/aead#tldr-which-one-should-i-use  
https://stackoverflow.com/questions/57312178/chacha20-poly1305-calculates-wrong-mac  


