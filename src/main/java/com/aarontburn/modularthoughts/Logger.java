package com.aarontburn.modularthoughts;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
    // https://stackoverflow.com/questions/1994255/how-to-write-console-output-to-a-txt-file
    // Make this class extend PrintStream to dump log to file


    // TODO: dump log output to a visual console or file

    public static void log(final Object text) {
        if (text instanceof Exception) {
            logException((Exception) text);
            return;
        }

        System.out.println(getTimeStamp() + (text == null ? "null" : text.toString()));
    }



    public static void logException(final Exception e) {
        System.err.println(getTimeStamp() + e.getClass() + ": " + e.getLocalizedMessage());

        for (StackTraceElement traceElement : e.getStackTrace())
            System.err.println("\tat " + traceElement);

    }

    private static String getTimeStamp() {

        return "[" + new SimpleDateFormat("yyyy/MM/dd | HH:mm:ss").format(Calendar.getInstance().getTime()) + " " + getCallerMethodName() + "] ";
    }

    private static String getCallerMethodName() {
        final StackTraceElement e = Thread.currentThread().getStackTrace()[4];

        final String classPath = e.getClassName();

        final String[] splitClassName = classPath.split("\\.");
        final String simpleClassName = splitClassName[splitClassName.length - 1];

        return "{@" + simpleClassName + " -> " + e.getMethodName() + "()}";
    }
}
