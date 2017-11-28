package com.pokercc.appstarter;

import android.net.Uri;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by cisco on 2017/11/28.
 */

@RunWith(RobolectricTestRunner.class)
public class AppSchemeTest {
    @Test
    public void testQuery() {
        Uri parse = Uri.parse("appStarter://com.pokercc.appstarter.ManifestAppEntryFinderTest$NormalAppEntry1:8888?process=baidu&a=b");
        assertThat(parse.getQueryParameter("process")).isEqualTo("baidu");
        assertThat(parse.getPort()).isEqualTo(8888);
        assertThat(parse.getHost()).isNotEmpty();

    }
}
