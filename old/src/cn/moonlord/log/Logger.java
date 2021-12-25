package cn.moonlord.log;

import java.time.LocalDateTime;

public class Logger {

    public static boolean infoEnabled = true;

    public static void info(Object message) {
        if(infoEnabled == false) {
            return;
        }
        System.out.println("[ " + LocalDateTime.now() + " ] " + "[ " + Thread.currentThread().getName() + " ] " + message);
        if(message != null) {
            if(message instanceof Exception) {
                ((Exception) message).printStackTrace(System.out);
            }
        }
    }

    public static void warn(Object message) {
        System.err.println("[ " + LocalDateTime.now() + " ] " + "[ " + Thread.currentThread().getName() + " ] " + message);
        if(message != null) {
            if(message instanceof Exception) {
                ((Exception) message).printStackTrace(System.err);
            }
        }
    }

}
