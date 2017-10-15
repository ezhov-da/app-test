package ru.ezhov.test.util;

public class StaticClassNameUtil {

    public static String getClassName() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String className = "call class not find";

        if (stackTraceElements.length >= 3) {
            StackTraceElement element = stackTraceElements[2];
            className = element.getClassName();
            String methodName = element.getMethodName();
            return className;
        }

        return className;
    }
}
