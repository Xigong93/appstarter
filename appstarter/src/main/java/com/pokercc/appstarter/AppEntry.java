package com.pokercc.appstarter;

import android.app.Application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;

/**
 * Created by cisco on 2017/11/22.
 */

public class AppEntry implements IAppEntry, Comparable<AppEntry> {
    private final String className;
    private final String[] args;
    private final Method method;
    private final int order;

    public AppEntry(String className, String... args) {
        this(createClass(className), args);
    }

    public AppEntry(Class _class, String... args) {
        this.args = args;
        this.className = _class.getName();
        this.method = findOnAppCreateMethod(_class);
        this.order = method.getAnnotation(OnAppCreate.class).order();
        checkMethod();

    }

    private static Method findOnAppCreateMethod(Class _class) {
        Method appCreateMethod = null;
        for (Method m : _class.getMethods()) {
            OnAppCreate appCreate = m.getAnnotation(OnAppCreate.class);
            if (appCreate == null) {
                continue;
            }

            if (appCreateMethod != null) {
                throw new RuntimeException(MessageFormat.format(
                        "{0} have more than one OnAppCreateMethod : [{1},{2}]", _class.getName(),
                        appCreateMethod.getName(), m.getName()));
            } else {
                appCreateMethod = m;
            }

        }
        if (appCreateMethod == null) {
            throw new RuntimeException("OnAppCreateMethod not find in " + _class.getName());
        }
        return appCreateMethod;
    }

    public String getClassName() {
        return className;
    }

    private static Class<?> createClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AppEntry) {
            return this.toString().equals(obj.toString());
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public String toString() {
        if (method == null) {
            return className;
        }
        return className + "#" + method.getName();
    }


    private void checkMethod() throws RuntimeException {
        if (!Modifier.isStatic(method.getModifiers()) || method.getReturnType() != void.class) {
            throw new RuntimeException("onAppCreateMethod must be public static void " + this.toString());
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length < 1 || parameterTypes[0] != Application.class) {
            throw new RuntimeException("onAppCreateMethod must have one param as Application " + this.toString());
        }

        if (parameterTypes.length > 2 || (parameterTypes.length == 2 && parameterTypes[1] != String[].class)) {
            throw new RuntimeException("onAppCreateMethod can only have two params as (Application app, String[] arg) " + this.toString());
        }
    }

    @Override
    public void onAppCreate(Application app) {
        try {
            if (this.method.getParameterTypes().length == 1) {
                this.method.invoke(null, app);
            } else {
                this.method.invoke(null, app, this.args);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public int getOrder() {
        return order;
    }

    @Override
    public int compareTo(AppEntry other) {
        return this.getOrder() - other.getOrder();
    }
}
