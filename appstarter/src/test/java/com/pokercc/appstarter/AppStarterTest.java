package com.pokercc.appstarter;

import android.app.Application;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;


/**
 * Created by cisco on 2017/11/29.
 */
@RunWith(RobolectricTestRunner.class)
public class AppStarterTest {
    public static int count = 0;

    @Before
    public void setUp() throws Exception {
        count = 0;
    }

    @After
    public void tearDown() throws Exception {
        count = 0;

    }

    @Test
    public void dispatchAppCreate() throws Exception {
        Application application = RuntimeEnvironment.application;
        AppStarter.newBuilder(application)
                .unableAndroidManifest()
                .addAppEntryList(Arrays.asList(
                        new AppEntry(AppEntry1.class),
                        new AppEntry(AppEntry2.class)
                ))
                .build()
                .dispatchAppCreate(application);
        assertThat(count).isEqualTo(2);
    }

    @Test
    public void newBuilder() throws Exception {
    }

    public static class AppEntry1 {
        @OnAppCreate(order = 1)
        public static void onAppCreate(Application application) {
            count++;

        }
    }

    public static class AppEntry2 {
        @OnAppCreate(order = 2)
        public static void onAppCreate(Application application) {
            count++;


        }
    }
}