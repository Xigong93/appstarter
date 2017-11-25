package com.pokercc.demo;


import android.app.Application;

import com.pokercc.appstarter.ManifestAppEntryFinder;
import com.pokercc.appstarter.OnAppCreateMethod;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
//import static com.google.common.truth.Truth8.assertThat; // for assertions on Java 8 types

/**
 * Created by Cisco on 2017/11/25.
 */
@RunWith(RobolectricTestRunner.class)
//@Config(
////        constants = BuildConfig.class
//        application = TestApplication.class
//)
public class ManifestAppEntryFinderTest {

    private ManifestAppEntryFinder manifestAppEntryFinder;

    @Before
    public void setup() {
        manifestAppEntryFinder = new ManifestAppEntryFinder();
//        app = RuntimeEnvironment.application;
    }


    @Test
//    @Config(manifest = "\\src\\test\\res\\manifest\\one\\AndroidManifest.xml")
//    @Config(manifest = "E:\\android\\app_starter\\sample\\src\\test\\AndroidManifest.xml")
    public void getAppInjectors() throws Exception {
        List<OnAppCreateMethod> appInjectors = manifestAppEntryFinder.getAppInjectors(RuntimeEnvironment.application);
        assertThat(appInjectors).isNotNull();
        assertThat(appInjectors).hasSize(1);
    }


}