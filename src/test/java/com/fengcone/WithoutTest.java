package com.fengcone;

import com.fengcone.phasmida.core.Phasmida;
import com.fengcone.phasmida.core.PhasmidaFactory;
import com.fengcone.phasmida.core.PhasmidaRegistry;
import com.fengcone.phasmida.core.StringContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class WithoutTest {
    @Test
    public void testCase1() {
        PhasmidaRegistry.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("with(6).without(我们)");
        StringContext context = new StringContext("天天6我们向上");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert !process;
    }

    @Test
    public void testCase2() {
        PhasmidaRegistry.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("without(我们).with(向上)");
        StringContext context = new StringContext("天天6我们向上");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert !process;
    }

    @Test
    public void testCase3() {
        PhasmidaRegistry.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("with(我们).without(向上)");
        StringContext context = new StringContext("天天6我们向上");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert !process;
    }
}
