package com.fengcone;

import com.fengcone.phasmida.core.Phasmida;
import com.fengcone.phasmida.core.PhasmidaFactory;
import com.fengcone.phasmida.registry.PhasmidaRegistry;
import com.fengcone.phasmida.core.PhasmidaContext;
import com.fengcone.phasmida.registry.RegistryUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class WithTest {

    @Test
    public void testCase1() {
        PhasmidaContext context = PhasmidaTestUtil.test("with(6)", "天天6我们向上");
        assert context.isResult();
        assert context.getStartIndex() == 2;
        assert context.getEndIndex() == 3;

        context = PhasmidaTestUtil.test("with(6).with(我们)", "天天6我们向上6好");
        assert context.isResult();
        assert context.getEndIndex() == 5;
        assert context.getStartIndex() == 3;
        context = PhasmidaTestUtil.test("with(6).without(我们)", "天天6我们向上");
        assert !context.isResult();
        context = PhasmidaTestUtil.test("with(好好,坏坏).with(学习)", "好好学习天天向上");
        assert context.isResult();
        context = PhasmidaTestUtil.test("with(好好,坏坏).with(学习)", "坏坏学习天天向上");
        assert context.isResult();
    }

    @Test
    public void testCase5(){
        RegistryUtil.registerStandardFragments();
        Phasmida phasmida = PhasmidaFactory.getPhasmida("with(Hello).with( world, phasmida)");
        PhasmidaContext context = new PhasmidaContext("Hello phasmida");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert process;
    }
}
