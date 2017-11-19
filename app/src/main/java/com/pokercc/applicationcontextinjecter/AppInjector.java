package com.pokercc.applicationcontextinjecter;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cisco on 2017/11/20.
 */

public class AppInjector implements IAppHolder {
    private final static AppInjector INSTANCE = new AppInjector();
    private final List<IAppHolder> mIAppHolderList = new ArrayList<>();

    private AppInjector() {

    }

    public static AppInjector getInstance() {
        return INSTANCE;
    }

    public void addAppContextHolder(String... classNames) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (int i = 0; classNames != null && i < classNames.length; i++) {
            Class<?> clazz = Class.forName(classNames[i]);
            Object contextHolder = clazz.newInstance();
            if (!(contextHolder instanceof IAppHolder)) {
                throw new RuntimeException("must implements " + IAppHolder.class.getName());
            }
            mIAppHolderList.add((IAppHolder) contextHolder);
        }
    }

    @Override
    public void init(Application app) {
        for (IAppHolder holder : mIAppHolderList) {
            holder.init(app);
        }
    }
}
