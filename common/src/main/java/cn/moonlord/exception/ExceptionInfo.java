package cn.moonlord.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

public class ExceptionInfo {

    private static final int MAX_MESSAGE_SIZE = 4 * 1024;

    private static final int MAX_STACK_TRACE_SIZE = 256 * 1024;

    public static final String SENSITIVE_EXCEPTION_MESSAGE = "sensitive exception, message is hidden";

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

    public static final List<String> SENSITIVE_PACKAGE_LIST = Arrays.asList(
            "java.sql.",
            "org.springframework.jdbc.",
            "org.springframework.dao.",
            "org.apache.ibatis.exceptions."
    );

    public static String getSafeMessage(Throwable exception) {
        if (exception == null) {
            return "";
        }
        String className = exception.getClass().getName();
        if (SENSITIVE_EXCEPTION_LIST.contains(className)) {
            return SENSITIVE_EXCEPTION_MESSAGE;
        }
        for (String sensitivePackage : SENSITIVE_PACKAGE_LIST) {
            if(className.startsWith(sensitivePackage)) {
                return SENSITIVE_EXCEPTION_MESSAGE;
            }
        }
        String message = exception.getMessage();
        if(message == null) {
            message = "";
        }
        return message;
    }

    public static String getFullSafeMessage(Throwable exception) {
        if (exception == null) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append(getSafeMessage(exception));
        Throwable cause = exception.getCause();
        while (cause != null && buffer.length() < MAX_MESSAGE_SIZE) {
            buffer.append(" / ");
            buffer.append(getSafeMessage(cause));
            cause = cause.getCause();
        }
        return buffer.toString();
    }

    public static String getStackTrace(Throwable exception) {
        if (exception == null) {
            return "";
        }
        try ( StringWriter buffer = new StringWriter(); PrintWriter writer = new PrintWriter(buffer); ) {
            exception.printStackTrace(writer);
            return buffer.toString();
        } catch (IOException e) {
            return "";
        }
    }

    public static String getFullStackTrace(Throwable exception) {
        if (exception == null) {
            return "";
        }
        try (StringWriter buffer = new StringWriter(); ) {
            buffer.append(exception.toString());
            StackTraceElement[] traces = exception.getStackTrace();
            for (StackTraceElement trace : traces) {
                buffer.append(System.lineSeparator());
                buffer.append("\tat ");
                buffer.append(trace.toString());
            }
            Throwable cause = exception.getCause();
            while (cause != null && buffer.toString().length() < MAX_STACK_TRACE_SIZE) {
                buffer.append(System.lineSeparator());
                buffer.append("Caused by: ");
                buffer.append(getFullStackTrace(cause));
                cause = cause.getCause();
            }
            return buffer.toString();
        } catch (IOException e) {
            return "";
        }
    }

}
