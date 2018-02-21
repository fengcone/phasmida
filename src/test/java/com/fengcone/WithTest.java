package com.fengcone;

import com.fengcone.phasmida.core.Phasmida;
import com.fengcone.phasmida.core.PhasmidaFactory;
import com.fengcone.phasmida.registry.RegistryUtil;
import com.fengcone.phasmida.core.PhasmidaContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class WithTest {

    @Test
    public void testCase1() {
        RegistryUtil.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("with(6)");
        PhasmidaContext context = new PhasmidaContext("天天6我们向上6好");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert process;
        assert context.getStartIndex() == 2;
        assert context.getEndIndex() == 3;

    }

    @Test
    public void testCase2() {
        RegistryUtil.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("with(6).with(我们)");
        PhasmidaContext context = new PhasmidaContext("天天6我们向上6好");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert process;
        assert context.getEndIndex() == 5;
        assert context.getStartIndex() == 3;
    }

    @Test
    public void testCase3() {
        RegistryUtil.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("with(6).without(我们)");
        PhasmidaContext context = new PhasmidaContext("天天6我们向上");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert !process;
    }

    @Test
    public void testCase4() {
        RegistryUtil.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("with(好好,坏坏).with(学习)");
        PhasmidaContext context = new PhasmidaContext("好好学习天天向上");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert process;
        context = new PhasmidaContext("坏坏学习天天向上");
        process = phasmida.process(context);
        log.info(context.toString());
        assert process;
    }
}
