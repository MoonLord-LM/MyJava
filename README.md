# MyJava
My Java Demo Projects

[![Java CI with Maven](https://github.com/MoonLord-LM/MyJava/actions/workflows/maven.yml/badge.svg)](https://github.com/MoonLord-LM/MyJava/actions/workflows/maven.yml)



## 笔记

#### 1. 低版本的 JDK（< 8u161）不支持 AES-GCM-256 等高强度算法的问题：
https://www.oracle.com/java/technologies/javase-jce-all-downloads.html  

#### 2. JDK 只有 AES/CBC/PKCS5Padding 而没有 AES/CBC/PKCS7Padding 的问题：
历史命名问题，实际上 PKCS5Padding 和 PKCS7Padding 是同一种填充算法  
https://crypto.stackexchange.com/questions/9043/what-is-the-difference-between-pkcs5-padding-and-pkcs7-padding  

#### 3. 随机数算法的性能问题：
SecureRandom.getInstanceStrong() 会获取一个当前环境上较强的随机数算法实现  
Linux 环境上是 NativePRNGBlocking 算法，使用 /dev/random 系统接口  
Windows 环境上是 Windows-PRNG 算法，使用 CryptGenRandom 系统接口  
为了避免 /dev/random 阻塞导致随机数生成的阻塞，可以使用 DRBG 算法  

#### 4. 常用的 AEAD（Authenticated Encryption with Associated Data）算法：
AEAD 算法，在内部逻辑中，同时实现了加解密和校验  
常用的 AEAD 算法：AES-256-GCM、ChaCha20-Poly1305  
https://www.google.com/search?q=AEAD+%E6%9C%89%E5%93%AA%E4%BA%9B  

|  算法  |  密钥长度  |  随机数长度  |  原文分组长度  |  校验值长度  |  加密后长度增加  |
|  ----  | ----  | ----  | ----  | ----  | ----  |
|  AES-256-GCM  |  256 bit  |  96 bit  |  128 bit  |  128 bit  |  96 bit +  128 bit  |
|  ChaCha20-Poly1305  |  256 bit  |  64 bit  |  512 bit  |  128 bit  |  64 bit +  512 bit  |



## 说明

#### 1. 使用  spring-boot-starter-test  进行单元测试
注解使用：@SpringBootTest @RunWith(Enclosed.class) @Test  
每一个工具类，创建一个对应的测试类  
每一个方法，在测试类中创建一个对应的静态内部类  
每一个测试用例，在静态内部类中创建一个对应的测试方法

#### 2. 部分函数，对比 Apache Commons Codec、Spring Security 进行性能测试
|  本工程函数  |  对比函数  |  开源库  |  性能对比  |
|  ----  | ----  | ----  | ----  |
|  Hex.encode  | Hex.encodeHexString  |  Apache Commons Codec  |  \> +30%  |
|  Hex.encode  | Hex.encode  |  Spring Security  |  \> -10%  |
|  Hex.decode  | Hex.decodeHex  |  Apache Commons Codec  |  \> +30%  |
|  Hex.decode  | Hex.decode  |  Spring Security  |  \> +70%  |



## TODO
ChaCha20-Poly1305  
https://doc.libsodium.org/secret-key_cryptography/aead#tldr-which-one-should-i-use  
https://stackoverflow.com/questions/57312178/chacha20-poly1305-calculates-wrong-mac  


