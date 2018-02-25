package com.fengcone;

import com.fengcone.phasmida.core.PhasmidaContext;
import org.junit.Test;

public class EmailPhoneMatchTest {
    @Test
    public void testCase1() {
        String regex = "withCharRange(0-9,A-Z,a-z,-,_,1+).with(@).withCharRange(0-9,A-Z,a-z,-,_,1+).with(.).withCharRange(0-9,A-Z,a-z,-,_,1+)";
        PhasmidaContext context = PhasmidaTestUtil.test(regex, "fengcone@163.com");
        assert context.isResult();
    }

    @Test
    public void testCase2() {
        String regex = "head().with(1).with(3,9,7,5,4).withCharRange(0-9,9).tail()";
        PhasmidaContext context = PhasmidaTestUtil.test(regex, "13007774542");
        assert context.isResult();
    }
}
