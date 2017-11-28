package com.pokercc.appstarter;

import android.app.Application;

import com.pokercc.appstarter.appentry.TestAppEntry;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by Cisco on 2017/11/25.
 */
@Ignore
@RunWith(CustomManifestLocationTestRunner.class)
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
        List<AppEntry> appInjectors = manifestAppEntryFinder.getAppEntries(app);
        assertThat(appInjectors).isNotNull();
        assertThat(appInjectors).isEmpty();
    }

    @Test
    @Config(manifest = "/appstarter/src/test/res/manifest/one/AndroidManifest.xml")
    public void testFindOneAppCreateMethod() throws Exception {
        List<AppEntry> appInjectors = manifestAppEntryFinder.getAppEntries(app);
        assertThat(appInjectors).isNotNull();
        assertThat(appInjectors).hasSize(1);
    }

    @Test
    @Config(manifest = "/appstarter/src/test/res/manifest/some/AndroidManifest.xml")
    public void testFindSomeAppCreateMethod() throws Exception {
        List<AppEntry> appInjectors = manifestAppEntryFinder.getAppEntries(app);
        assertThat(appInjectors).isNotNull();
        assertThat(appInjectors).hasSize(2);
    }

    /**
     * 测试获取到的AppEntryList 是android manifest 中的appStarter 的书写顺序，
     *
     * @throws Exception
     */
    @Test
    @Config(manifest = "/appstarter/src/test/res/manifest/order/AndroidManifest.xml")
    public void testManifestAsOrder() throws Exception {
        List<AppEntry> appInjectors = manifestAppEntryFinder.getAppEntries(app);
        assertThat(appInjectors).isNotNull();
        assertThat(appInjectors).isNotEmpty();

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
        @OnAppCreate(order = 1)
        public static void onAppCreate(Application application) {
        }
    }

    public static class NormalAppEntry2 implements TestAppEntry {
        @OnAppCreate(order = 2)
        public static void onAppCreate(Application application, String... arg) {
        }
    }

    public static class NormalAppEntry3 implements TestAppEntry {
        @OnAppCreate(order = 3)
        public static void onAppCreate(Application application, String... arg) {
        }
    }

    public static class NormalAppEntry4 implements TestAppEntry {
        @OnAppCreate(order = 4)
        public static void onAppCreate(Application application, String... arg) {
        }
    }

    public static class NormalAppEntry5 implements TestAppEntry {
        @OnAppCreate(order = 5)
        public static void onAppCreate(Application application, String... arg) {
        }
    }

    public static class NormalAppEntry6 implements TestAppEntry {
        @OnAppCreate(order = 6)
        public static void onAppCreate(Application application, String... arg) {
        }
    }

}

//import static com.google.common.truth.Truth8.assertThat; // for assertions on Java 8 types
