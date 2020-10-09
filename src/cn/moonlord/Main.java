package cn.moonlord;

import cn.moonlord.security.Hash;
import cn.moonlord.security.Pbkdf2;
import cn.moonlord.security.Random;
import cn.moonlord.security.Xor;
import cn.moonlord.test.SecurityTest;
import com.sun.org.apache.bcel.internal.generic.IXOR;
import sun.security.rsa.RSASignature;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World");
        //SecurityTest.test1();
        //SecurityTest.test2();
        //SecurityTest.test3();
        SecurityTest.test4();
        SecurityTest.test5();
    }

}
