package com.pokercc.applicationcontextinjecter;

import android.app.Application;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;

/**
 * Created by cisco on 2017/11/20.
 */

@Keep
public interface IAppHolder {
    void init(@NonNull Application app);
}
