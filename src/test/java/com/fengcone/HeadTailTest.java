package com.fengcone;

import com.fengcone.phasmida.core.Phasmida;
import com.fengcone.phasmida.core.PhasmidaContext;
import com.fengcone.phasmida.core.PhasmidaFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class HeadTailTest {
    @Test
    public void testCase1() {
        PhasmidaContext context = PhasmidaTestUtil.test("head().with(好景国际).tail()", "好景国际3号楼601");
        assert !context.isResult();
        context = PhasmidaTestUtil.test("head().with(好景国际).tail()", "好景国际");
        assert context.isResult();
        log.info(context.toString());
    }
}
