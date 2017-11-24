package com.pokercc.appstarter;

import android.app.Application;

import com.pokercc.appstarter.appentry.NotPublicMethodAppEntry;
import com.pokercc.appstarter.appentry.TestAppEntry;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by cisco on 2017/11/25.
 */
public class OnAppCreateMethodTest {


    @Test
    public void testLegalMethod() throws Exception {
        new OnAppCreateMethod(getClass(), "1", "2").onAppCreate(null);
        new OnAppCreateMethod(NormalAppEntry1.class).onAppCreate(null);
        new OnAppCreateMethod(NormalAppEntry1.class, "1", "2").onAppCreate(null);

    }

    @Test
    public void testLegalMethod2() throws Exception {
        new OnAppCreateMethod(NormalAppEntry2.class).onAppCreate(null);
        new OnAppCreateMethod(NormalAppEntry2.class, "1", "2").onAppCreate(null);

    }

    @Test
    public void testGetClassName() {
        String className = new OnAppCreateMethod(NormalAppEntry2.class).getClassName();
        assertEquals(className, NormalAppEntry2.class.getName());
        assertTrue(className.contains("$"));
    }

    @Test
    public void testToString() {
        String toString = new OnAppCreateMethod(NormalAppEntry2.class).toString();
        assertEquals(NormalAppEntry2.class.getName() + "#onAppCreate", toString);
    }

    @Test(expected = RuntimeException.class)
    public void testNotPublicClass() throws Exception {
        new OnAppCreateMethod("com.pokercc.appstarter.appentry.NotPublicAppEntryHull$NotPublicAppEntry")
                .onAppCreate(null);
    }

    @Test(expected = RuntimeException.class)
    public void testNotExistsAppEntryClass() throws Exception {
        new OnAppCreateMethod("com.pokercc.appstarter.NotExistsAppEntry");
    }

    @Test(expected = RuntimeException.class)
    public void testNotPublicMethod() throws Exception {
        new OnAppCreateMethod(NotPublicMethodAppEntry.class);
    }

    @Test(expected = RuntimeException.class)
    public void testNotStaticMethod() throws Exception {
        new OnAppCreateMethod(NotStaticMethod.class);
    }

    @Test(expected = RuntimeException.class)
    public void testNoAnnotation() throws Exception {
        new OnAppCreateMethod(NoAnnotation.class);
    }

    @Test(expected = RuntimeException.class)
    public void testNoParams() throws Exception {
        new OnAppCreateMethod(NoParamsAppEntry.class);
    }

    @Test(expected = RuntimeException.class)
    public void testIllegalParams() throws Exception {
        new OnAppCreateMethod(IllegalParamsClass.class);
    }

    @Test(expected = RuntimeException.class)
    public void testTooMuchParamsClass() throws Exception {
        new OnAppCreateMethod(TooMuchParamsClass.class);
    }

    @Test(expected = RuntimeException.class)
    public void testIllegalParams2() throws Exception {
        new OnAppCreateMethod(IllegalParams2Class.class);
    }

    @Test(expected = RuntimeException.class)
    public void testIllegalParams3() throws Exception {
        new OnAppCreateMethod(IllegalParams3Class.class);
    }

    @Test(expected = RuntimeException.class)
    public void testNotVoidReturnType() throws Exception {
        new OnAppCreateMethod(NotVoidReturnType.class);
    }

    @Test(expected = RuntimeException.class)
    public void testMoreThanOneAppCreateMethod() throws Exception {
        new OnAppCreateMethod(MoreThanOneAppCreateMethod.class);
    }

    @OnAppCreate
    public static void onAppCreate(Application application, String... args) {

        assertArrayEquals(new String[]{"1", "2"}, args);
    }

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