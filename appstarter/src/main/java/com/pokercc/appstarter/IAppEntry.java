package com.pokercc.appstarter;

import android.app.Application;

/**
 * Created by cisco on 2017/11/20.
 */

public interface IAppEntry {
    void onAppCreate(Application app);

    String getName();
}
