package com.pokercc.appstarter;

import android.app.Application;

import com.pokercc.appstarter.appentry.NotPublicMethodAppEntry;
import com.pokercc.appstarter.appentry.TestAppEntry;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/**
 * Created by cisco on 2017/11/25.
 */
public class AppEntryTest {


    @Test
    public void testLegalMethod() throws Exception {
        new AppEntry(getClass(), "1", "2").onAppCreate(null);
        new AppEntry(NormalAppEntry1.class).onAppCreate(null);
        new AppEntry(NormalAppEntry1.class, "1", "2").onAppCreate(null);

    }

    @Test
    public void testLegalMethod2() throws Exception {
        new AppEntry(NormalAppEntry2.class).onAppCreate(null);
        new AppEntry(NormalAppEntry2.class, "1", "2").onAppCreate(null);

    }

    @Test
    public void testGetClassName() {
        String className = new AppEntry(NormalAppEntry2.class).getClassName();
        assertEquals(className, NormalAppEntry2.class.getName());
        assertTrue(className.contains("$"));
    }

    @Test
    public void testToString() {
        String toString = new AppEntry(NormalAppEntry2.class).toString();
        assertEquals(NormalAppEntry2.class.getName() + "#onAppCreate", toString);
    }

    @Test(expected = RuntimeException.class)
    public void testNotPublicClass() throws Exception {
        new AppEntry("com.pokercc.appstarter.appentry.NotPublicAppEntryHull$NotPublicAppEntry")
                .onAppCreate(null);
    }

    @Test(expected = RuntimeException.class)
    public void testNotExistsAppEntryClass() throws Exception {
        new AppEntry("com.pokercc.appstarter.NotExistsAppEntry");
    }

    @Test(expected = RuntimeException.class)
    public void testNotPublicMethod() throws Exception {
        new AppEntry(NotPublicMethodAppEntry.class);
    }

    @Test(expected = RuntimeException.class)
    public void testNotStaticMethod() throws Exception {
        new AppEntry(NotStaticMethod.class);
    }

    @Test(expected = RuntimeException.class)
    public void testNoAnnotation() throws Exception {
        new AppEntry(NoAnnotation.class);
    }

    @Test(expected = RuntimeException.class)
    public void testNoParams() throws Exception {
        new AppEntry(NoParamsAppEntry.class);
    }

    @Test(expected = RuntimeException.class)
    public void testIllegalParams() throws Exception {
        new AppEntry(IllegalParamsClass.class);
    }

    @Test(expected = RuntimeException.class)
    public void testTooMuchParamsClass() throws Exception {
        new AppEntry(TooMuchParamsClass.class);
    }

    @Test(expected = RuntimeException.class)
    public void testIllegalParams2() throws Exception {
        new AppEntry(IllegalParams2Class.class);
    }

    @Test(expected = RuntimeException.class)
    public void testIllegalParams3() throws Exception {
        new AppEntry(IllegalParams3Class.class);
    }

    @Test(expected = RuntimeException.class)
    public void testNotVoidReturnType() throws Exception {
        new AppEntry(NotVoidReturnType.class);
    }

    @Test(expected = RuntimeException.class)
    public void testMoreThanOneAppCreateMethod() throws Exception {
        new AppEntry(MoreThanOneAppCreateMethod.class);
    }

    @OnAppCreate
    public static void onAppCreate(Application application, String... args) {

        assertArrayEquals(new String[]{"1", "2"}, args);
    }

//    @Test
//    public void testHashCode() {
//        OnAppCreateMethod onAppCreateMethod1 = new OnAppCreateMethod("com.pokercc.appstarter.ManifestAppEntryFinderTest$NormalAppEntry1");
//        OnAppCreateMethod onAppCreateMethod2 = new OnAppCreateMethod("com.pokercc.appstarter.ManifestAppEntryFinderTest$NormalAppEntry1");
//        assertThat(onAppCreateMethod1).isEqualTo(onAppCreateMethod2);
//        assertThat(onAppCreateMethod1.hashCode()).isEqualTo(onAppCreateMethod2.hashCode());
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


    public static class NotStaticMethod implements TestAppEntry {
        @OnAppCreate
        public void onAppCreate(Application application, String... arg) {
        }
    }

    public static class NoAnnotation implements TestAppEntry {
        public static void onAppCreate(Application application, String... arg) {

        }
    }

    public static class NoParamsAppEntry implements TestAppEntry {
        @OnAppCreate
        public static void onAppCreate() {

        }
    }

    public static class IllegalParamsClass implements TestAppEntry {
        @OnAppCreate
        public static void onAppCreate(String... args) {

        }
    }

    public static class TooMuchParamsClass implements TestAppEntry {
        @OnAppCreate
        public static void onAppCreate(Application application, String a, String... arg) {

        }
    }

    public static class IllegalParams2Class implements TestAppEntry {
        @OnAppCreate
        public static void onAppCreate(int a, int b) {

        }
    }

    public static class IllegalParams3Class implements TestAppEntry {
        @OnAppCreate
        public static void onAppCreate(Application application, String a) {

        }
    }

    public static class NotVoidReturnType implements TestAppEntry {
        @OnAppCreate
        public static boolean onAppCreate(Application application, String... arg) {
            return true;
        }
    }

    public static class MoreThanOneAppCreateMethod implements TestAppEntry {
        @OnAppCreate
        public static void onAppCreate(Application application, String... arg) {

        }

        @OnAppCreate
        public static void onAppCreate2(Application application, String... arg) {

        }

    }

}