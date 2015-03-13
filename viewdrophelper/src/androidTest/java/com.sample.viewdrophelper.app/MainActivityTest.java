package com.sample.viewdrophelper.app;

import android.app.Activity;
import android.view.Gravity;
import com.sample.viewdrophelper.view.ContentLayout;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.sample.viewdrophelper.app.R;

/**
 * Created by vincent on 2015/3/13.
 */
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    @Test
    public void test_layoutParams(){

        Activity activity = Robolectric.buildActivity(MainActivity.class)
                .create().start().resume().get();
        assertTrue(activity!=null);

        ContentLayout container = (ContentLayout) activity.findViewById(R.id.container);
        assertTrue(container!=null);

        ContentLayout.LayoutParams lp = (ContentLayout.LayoutParams)
                container.getChildAt(0).getLayoutParams();

        assertEquals(Gravity.TOP, lp.getGravity());
        assertEquals(100, (int)lp.getOffsetX());
        assertEquals(100, (int)lp.getOffsetY());
    }

    @Test
    public void test_offset(){

    }
}
