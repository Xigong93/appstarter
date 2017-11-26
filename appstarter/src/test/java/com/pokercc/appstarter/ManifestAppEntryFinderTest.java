package com.pokercc.appstarter;

import android.app.Application;

import com.pokercc.appstarter.appentry.TestAppEntry;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.DuplicateFormatFlagsException;
import java.util.List;

import static org.junit.Assert.*;
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
//import static com.google.common.truth.Truth8.assertThat; // for assertions on Java 8 types

/**
 * Created by Cisco on 2017/11/25.
 */
@RunWith(CustomManifestLocationTestRunner.class)
//@Config(constants = BuildConfig.class)
public class ManifestAppEntryFinderTest {

    private ManifestAppEntryFinder manifestAppEntryFinder;
    private Application app;

    @Before
    public void setup() {
        manifestAppEntryFinder = new ManifestAppEntryFinder();
        app = RuntimeEnvironment.application;
    }




    @Test
    public void testNormalFindOnAppCreateMethod() throws Exception {
        List<OnAppCreateMethod> appInjectors = manifestAppEntryFinder.getAppInjectors(app);
        assertThat(appInjectors).isNotNull();
        assertThat(appInjectors).isEmpty();
    }

    @Test
    @Config(manifest = "/appstarter/src/test/res/manifest/one/AndroidManifest.xml")
    public void testFindOneAppCreateMethod() throws Exception {
        List<OnAppCreateMethod> appInjectors = manifestAppEntryFinder.getAppInjectors(app);
        assertThat(appInjectors).isNotNull();
        assertThat(appInjectors).hasSize(1);
    }

    @Test
    @Config(manifest = "/appstarter/src/test/res/manifest/some/AndroidManifest.xml")
    public void testFindSomeAppCreateMethod() throws Exception {
        List<OnAppCreateMethod> appInjectors = manifestAppEntryFinder.getAppInjectors(app);
        assertThat(appInjectors).isNotNull();
        assertThat(appInjectors).hasSize(2);
    }

//    @Test(expected = RuntimeException.class)
//    @Config(manifest = "/test/res/manifest/duplication/AndroidManifest.xml")
//    public void testDuplicationFindAppCreateMethod() throws Exception {
//        List<OnAppCreateMethod> appInjectors = manifestAppEntryFinder.getAppInjectors(app);
//        assertThat(appInjectors).isNotNull();
//        assertThat(appInjectors).hasSize(1);
//
//    }


    public static class NormalAppEntry1 implements TestAppEntry {
        @OnAppCreate
        public static void onAppCreate(Application application) {
        }
    }

    public static class NormalAppEntry2 implements TestAppEntry {
        @OnAppCreate
        public static void onAppCreate(Application application, String... arg) {
        }
    }

}