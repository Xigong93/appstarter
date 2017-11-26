package com.pokercc.appstarter;

import android.content.Context;

import java.util.Collections;
import java.util.List;

/**
 * Created by Cisco on 2017/11/21.
 */

public class ClassNameAppEntryFinder implements IAppEntryFinder {

    private final List<AppEntry> mAppEntries;

    public ClassNameAppEntryFinder(List<AppEntry> appEntries) {
        this.mAppEntries = appEntries;
    }

    @Override
    public List<AppEntry> getAppEntries(Context context) {
        return this.mAppEntries;
    }
}
