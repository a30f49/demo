package com.sample.viewdrophelper.app;

import android.app.Activity;
import android.view.Gravity;
import com.sample.viewdrophelper.view.OffsetLayout;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by vincent on 2015/3/13.
 */
@RunWith(RobolectricTestRunner.class)
public class DragContentActivityTest {
    @Test
    public void test_layoutParams(){

        Activity activity = Robolectric.buildActivity(DragContentActivity.class)
                .create().start().resume().get();
        assertTrue(activity!=null);

        OffsetLayout container = (OffsetLayout) activity.findViewById(R.id.container);
        assertTrue(container!=null);

        OffsetLayout.LayoutParams lp = (OffsetLayout.LayoutParams)
                container.getChildAt(0).getLayoutParams();

        assertEquals(Gravity.TOP, lp.getGravity());
        assertEquals(100, (int)lp.getOffsetX());
        assertEquals(100, (int)lp.getOffsetY());
    }

    @Test
    public void test_offset(){

    }
}
