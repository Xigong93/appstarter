package com.pokercc.appinjector;

import android.app.Application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Created by cisco on 2017/11/22.
 */

public class OnAppCreateMethod implements IAppEntry {
    private final String className;
    private final String[] args;
    private Method method;

    public OnAppCreateMethod(String className, String... args) {
        this(createClass(className), args);
    }

    public OnAppCreateMethod(Class _class, String... args) {
        this.className = _class.getName();
        this.args = args;
        for (Method staticMethod : _class.getMethods()) {
            OnAppCreate onAppCreate = staticMethod.getAnnotation(OnAppCreate.class);
            if (onAppCreate == null) {
                continue;
            } else if (this.method != null) {
                throw new RuntimeException("One class only have on OnAppCreateMethod " + this.toString());
            } else {
                this.method = staticMethod;
            }

        }
        isLegal();

    }

    public String getClassName() {
        return className;
    }

    public static Class<?> createClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return className + "#" + method.getName();
    }

//    不打印参数了，避免泄露账号和密码
//    @Override
//    public String toString() {
//        String stringArgs = this.args == null ? "" : Arrays.toString(this.args);
//        return getName() + " ( application, " + stringArgs + " ) ";
//    }

    private void isLegal() {
        if (this.method == null) {
            throw new RuntimeException("OnAppCreateMethod not find in " + this.toString());
        }
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
}
