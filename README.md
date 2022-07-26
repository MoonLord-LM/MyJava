# MyJava

My Java Projects

[![Java CI with Maven](https://github.com/MoonLord-LM/MyJava/actions/workflows/maven.yml/badge.svg)](https://github.com/MoonLord-LM/MyJava/actions/workflows/maven.yml)

## 笔记

#### 1. 低版本的 JDK（< 8u161）不支持 AES-GCM-256 等高强度算法的问题：

https://www.oracle.com/java/technologies/javase-jce-all-downloads.html

#### 2. 随机数算法的性能问题：

SecureRandom.getInstanceStrong() 会获取一个当前环境上较强的随机数算法实现  
Linux 环境上是 NativePRNGBlocking 算法，使用 /dev/random 系统接口  
Windows 环境上是 Windows-PRNG 算法，使用 CryptGenRandom 系统接口  
为了避免 /dev/random 阻塞导致随机数生成的阻塞，可以使用 DRBG 算法

#### 3. 常用的 AEAD（Authenticated Encryption with Associated Data）算法：

AEAD 加密算法，在内部逻辑中，同时实现了保密性，完整性和可认证性  
常用的 AEAD 算法：AES-256-GCM、ChaCha20-Poly1305、ChaCha20-Poly1305-IETF、 XChaCha20-Poly1305-IETF  
https://doc.libsodium.org/secret-key_cryptography/aead  
https://www.google.com/search?q=AEAD+%E6%9C%89%E5%93%AA%E4%BA%9B

#### 4. IDEA 使用 maven-javadoc-plugin 插件时，控制台出现乱码

设置系统环境变量，JAVA_TOOL_OPTIONS: -Dfile.encoding=UTF-8  
