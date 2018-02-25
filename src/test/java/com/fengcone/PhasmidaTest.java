package com.fengcone;


import com.fengcone.phasmida.core.Phasmida;
import com.fengcone.phasmida.core.PhasmidaFactory;
import com.fengcone.phasmida.registry.RegistryUtil;
import org.junit.Test;

public class PhasmidaTest {
    @Test
    public void testCase1() {
        RegistryUtil.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("with(','天台呢,地狱呢).with(好好').'学习)");
        assert phasmida != null;
    }
}
