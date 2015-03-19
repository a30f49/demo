package com.sample.java.dummy;

import com.sample.java.dummy.JavaDeveloper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.PrintStream;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Administrator on 2014/12/25.
 */
public class JavaDeveloperTest {

    private JavaDeveloper javaDeveloper;

    @Mock
    private PrintStream output;

    @Before
    public void beforeTest(){
        initMocks(this);
        javaDeveloper = new JavaDeveloper(output);
    }

    @Test
    public void shouldDoJavaStuff() {
        javaDeveloper.doStuff();
        verify(output, times(1)).println("I am developing with Java.");
    }

}