package com.fengcone;

import com.fengcone.phasmida.core.Phasmida;
import com.fengcone.phasmida.core.PhasmidaContext;
import com.fengcone.phasmida.core.PhasmidaFactory;
import com.fengcone.phasmida.registry.RegistryUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PhasmidaTestUtil {
    public static PhasmidaContext test(String regex, String matchString) {
        RegistryUtil.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida(regex);
        PhasmidaContext context = new PhasmidaContext(matchString);
        phasmida.process(context);
        log.info(context.toString());
        return context;
    }
}
