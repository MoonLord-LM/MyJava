package cn.moonlord.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

public class ExceptionInfo {

    private static final int MAX_MESSAGE_SIZE = 64 * 1024;

    public static final String SENSITIVE_EXCEPTION_MESSAGE = "sensitive exception, message is hidden";

    // 规范：
    public static final List<String> SENSITIVE_EXCEPTION_LIST = Arrays.asList(
            "java.io.FileNotFoundException",
            "java.util.jar.JarException",
            "java.util.MissingResourceException",
            "java.security.acl.NotOwnerException",
            "java.util.ConcurrentModificationException",
            "javax.naming.InsufficientResourcesException",
            "java.net.BindException",
            "java.lang.OutOfMemoryError",
            "java.lang.StackOverflowError",
            "java.sql.SQLException"
    );

    // 补充：
    public static final List<String> SENSITIVE_PACKAGE_LIST = Arrays.asList(
            "java.sql.",
            "org.springframework.jdbc.",
            "org.springframework.dao.",
            "org.apache.ibatis.exceptions."
    );

    public static String getSafeMessage(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        String className = throwable.getClass().getName();
        if (SENSITIVE_EXCEPTION_LIST.contains(className)) {
            return SENSITIVE_EXCEPTION_MESSAGE;
        }
        for (String sensitivePackage : SENSITIVE_PACKAGE_LIST) {
            if(className.startsWith(sensitivePackage)) {
                return SENSITIVE_EXCEPTION_MESSAGE;
            }
        }
        String message = throwable.getMessage();
        if(message == null) {
            message = "";
        }
        return message;
    }

    public static String getFullSafeMessage(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append(getSafeMessage(throwable));
        Throwable cause = throwable.getCause();
        while (cause != null && buffer.length() < MAX_MESSAGE_SIZE) {
            buffer.append(" / ");
            buffer.append(getSafeMessage(cause));
            cause = cause.getCause();
        }
        return buffer.toString();
    }

    public static String getStackTrace(Throwable throwable) {
        try ( StringWriter buffer = new StringWriter(); PrintWriter writer = new PrintWriter(buffer); ) {
            throwable.printStackTrace(writer);
            writer.flush();
            return buffer.toString();
        } catch (IOException e) {
            return "";
        }
    }

}
