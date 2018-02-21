package com.fengcone;

import com.fengcone.phasmida.core.Phasmida;
import com.fengcone.phasmida.core.PhasmidaFactory;
import com.fengcone.phasmida.core.PhasmidaRegistry;
import com.fengcone.phasmida.core.StringContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class WithAnythingTest {
    @Test
    public void testCase1() {
        PhasmidaRegistry.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("withAnything(3).with(我们)");
        StringContext context = new StringContext("天天6我们向上");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert process;
    }

    @Test
    public void testCase2() {
        PhasmidaRegistry.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("with(我们).withAnything(2)");
        StringContext context = new StringContext("天天6我们向上");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert process;
    }

    @Test
    public void testCase3() {
        PhasmidaRegistry.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("with(我们).withAnything(2).with(上)");
        StringContext context = new StringContext("天天6我们向上");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert process;
    }
}
