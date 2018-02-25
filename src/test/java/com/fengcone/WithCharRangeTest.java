package com.fengcone;

import com.fengcone.phasmida.core.Phasmida;
import com.fengcone.phasmida.core.PhasmidaContext;
import com.fengcone.phasmida.core.PhasmidaFactory;
import com.fengcone.phasmida.registry.RegistryUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class WithCharRangeTest {

    @Test
    public void testCase1() {
        PhasmidaContext context = PhasmidaTestUtil.test("withCharRange(1-3,2)", "13");
        assert context.isResult();
        assert context.getStartIndex() == 0;
        assert context.getEndIndex() == 2;
        context = PhasmidaTestUtil.test("withCharRange(1-3,2+)", "123");
        assert context.isResult();
        assert context.getStartIndex() == 0;
        assert context.getEndIndex() == 3;
        context = PhasmidaTestUtil.test("withCharRange(1-3,2+)", "123");
        assert context.isResult();
        assert context.getStartIndex() == 0;
        assert context.getEndIndex() == 3;
        context = PhasmidaTestUtil.test("withCharRange(\\u0031-\\u0033,2)", "12B");
        assert context.isResult();
        context = PhasmidaTestUtil.test("withCharRange(1-3,A-B,2+)", "12B");
        assert context.isResult();
        context = PhasmidaTestUtil.test("with(12).withCharRange(1-3,A-B,1)", "12B");
        assert context.isResult();
    }

    @Test
    public void testCase2() {
        try {
            PhasmidaTestUtil.test("with(12).withCharRange(1-3,A-B,1?)", "12B");
        } catch (IllegalArgumentException e) {
            assert true;
            return;
        }
        assert false;
    }


}
