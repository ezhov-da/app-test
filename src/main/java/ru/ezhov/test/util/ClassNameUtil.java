package ru.ezhov.test.util;

public class ClassNameUtil {

    public static String getCurrentClassName() {
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
