package com.fengcone;

import com.fengcone.phasmida.core.Phasmida;
import com.fengcone.phasmida.core.PhasmidaFactory;
import com.fengcone.phasmida.core.PhasmidaRegistry;
import com.fengcone.phasmida.core.StringContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class WithTest {

    @Test
    public void testCase1() {
        PhasmidaRegistry.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("with(6)");
        StringContext context = new StringContext("天天6我们向上6好");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert process;
        assert context.getStartIndex() == 2;
        assert context.getEndIndex() == 3;

    }

    @Test
    public void testCase2() {
        PhasmidaRegistry.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("with(6).with(我们)");
        StringContext context = new StringContext("天天6我们向上6好");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert process;
        assert context.getEndIndex() == 5;
        assert context.getStartIndex() == 3;
    }

    @Test
    public void testCase3() {
        PhasmidaRegistry.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("with(6).without(我们)");
        StringContext context = new StringContext("天天6我们向上");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert !process;
    }

    @Test
    public void testCase4() {
        PhasmidaRegistry.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("with(好好,坏坏).with(学习)");
        StringContext context = new StringContext("好好学习天天向上");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert process;
        context = new StringContext("坏坏学习天天向上");
        process = phasmida.process(context);
        log.info(context.toString());
        assert process;
    }
}
