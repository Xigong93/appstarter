package com.pokercc.appstarter;

import android.app.Application;

import com.pokercc.appstarter.appentry.TestAppEntry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by Cisco on 2017/11/25.
 */
//@Ignore
@RunWith(RobolectricTestRunner.class)
public class ManifestAppEntryFinderTest {

    private ManifestAppEntryFinder manifestAppEntryFinder;
    private Application app;

    @Before
    public void setup() {
        manifestAppEntryFinder = new ManifestAppEntryFinder();
        app = RuntimeEnvironment.application;
    }


    @Test
    @Config(manifest = "./manifest/one/AndroidManifest.xml")
    public void testFindOneAppCreateMethod1() throws Exception {
        List<AppEntry> appInjectors = manifestAppEntryFinder.getAppEntries(app);
        assertThat(appInjectors).isNotNull();
        assertThat(appInjectors).hasSize(1);
    }

    @Test
    @Config(manifest = "./manifest/args/AndroidManifest.xml")
    public void testArgs1() throws Exception {
        List<AppEntry> appInjectors = manifestAppEntryFinder.getAppEntries(app);
        assertThat(appInjectors).isNotNull();
        assertThat(appInjectors).hasSize(2);
        Collections.sort(appInjectors);
        AppEntry appEntry = appInjectors.get(0);
        Field argsField = AppEntry.class.getDeclaredField("args");
        argsField.setAccessible(true);
        String[] args = (String[]) argsField.get(appEntry);
        assertThat(args).isEqualTo(new String[]{"1", "2", "3"});

    }

    @Test
    @Config(manifest = "./manifest/args/AndroidManifest.xml")
    public void testArgs2() throws Exception {
        List<AppEntry> appInjectors = manifestAppEntryFinder.getAppEntries(app);
        assertThat(appInjectors).isNotNull();
        assertThat(appInjectors).hasSize(2);
        Collections.sort(appInjectors);
        AppEntry appEntry = appInjectors.get(1);
        Field argsField = AppEntry.class.getDeclaredField("args");
        argsField.setAccessible(true);
        String[] args = (String[]) argsField.get(appEntry);
        assertThat(args.length).isEqualTo(3);

    }

    @Test
    @Config(manifest = "./AndroidManifest.xml")
    public void testNormalFindOnAppCreateMethod() throws Exception {
        List<AppEntry> appInjectors = manifestAppEntryFinder.getAppEntries(app);
        assertThat(appInjectors).isNotNull();
        assertThat(appInjectors).isNotEmpty();
    }

    @Test
    @Config(manifest = "./manifest/one/AndroidManifest.xml")
    public void testFindOneAppCreateMethod() throws Exception {
        List<AppEntry> appInjectors = manifestAppEntryFinder.getAppEntries(app);
        assertThat(appInjectors).isNotNull();
        assertThat(appInjectors).hasSize(1);
    }


    @Test
    @Config(manifest = "./manifest/some/AndroidManifest.xml")
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
    @Config(manifest = "./manifest/order/AndroidManifest.xml")
    public void testManifestAsOrder() throws Exception {
        List<AppEntry> appInjectors = manifestAppEntryFinder.getAppEntries(app);
        assertThat(appInjectors).isNotNull();
        assertThat(appInjectors).isNotEmpty();

    }


    public static class NormalAppEntry1 implements TestAppEntry {
        @OnAppCreate(order = 1)
        public static void onAppCreate(Application application, String... arg) {

        }
    }

    public static class NormalAppEntryArgs1 implements TestAppEntry {
        @OnAppCreate(order = 1)
        public static void onAppCreate(Application application, String... arg) {
        }
    }

    public static class NormalAppEntryArgs2 implements TestAppEntry {
        @OnAppCreate(order = 2)
        public static void onAppCreate(Application application, String... arg) {
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
