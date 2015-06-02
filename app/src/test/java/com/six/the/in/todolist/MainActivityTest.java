package com.six.the.in.todolist;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by shweta on 15-06-01.
 */

@RunWith(RobolectricTestRunner.class)
@Config(
        manifest = "src/main/AndroidManifest.xml",
        emulateSdk = 18,
        shadows = { com.six.the.in.todolist.ShadowSupportMenuInflater.class }
)
public class MainActivityTest extends TestCase {
    @Test
    public void dummyTest() {
        assertEquals(1, 1);
    }

}