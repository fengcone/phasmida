package com.fengcone;

import com.fengcone.phasmida.core.Phasmida;
import com.fengcone.phasmida.core.PhasmidaFactory;
import com.fengcone.phasmida.registry.RegistryUtil;
import com.fengcone.phasmida.core.PhasmidaContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class WithoutTest {
    @Test
    public void testCase1() {
        PhasmidaContext context = PhasmidaTestUtil.test("with(6).without(我们)", "天天6我们向上");
        assert !context.isResult();
        context = PhasmidaTestUtil.test("without(我们).with(向上)", "天天6我们向上");
        assert !context.isResult();
        context = PhasmidaTestUtil.test("with(我们).without(向上)", "天天6我们向上");
        assert !context.isResult();
    }
}
