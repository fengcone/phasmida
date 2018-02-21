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
        RegistryUtil.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("with(6).without(我们)");
        PhasmidaContext context = new PhasmidaContext("天天6我们向上");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert !process;
    }

    @Test
    public void testCase2() {
        RegistryUtil.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("without(我们).with(向上)");
        PhasmidaContext context = new PhasmidaContext("天天6我们向上");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert !process;
    }

    @Test
    public void testCase3() {
        RegistryUtil.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("with(我们).without(向上)");
        PhasmidaContext context = new PhasmidaContext("天天6我们向上");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert !process;
    }
}
