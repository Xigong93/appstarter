package com.pokercc.appstarter;

import android.content.Context;

import java.util.List;


/**
 * Created by Cisco on 2017/11/21.
 */

public interface IAppEntryFinder {

    List<AppEntry> getAppEntries(Context context);
}
