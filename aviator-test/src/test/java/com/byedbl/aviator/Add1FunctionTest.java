package com.byedbl.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import org.junit.Test;

public class Add1FunctionTest {

    @Test
    public void call() {
        System.out.println(AviatorEvaluator.execute("add1(1, 2)"));
    }
}