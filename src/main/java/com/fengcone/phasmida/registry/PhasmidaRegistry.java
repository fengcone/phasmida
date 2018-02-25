package com.fengcone.phasmida.registry;

import com.fengcone.phasmida.exception.PhasmidaRegistryException;
import com.fengcone.phasmida.fragment.Fragment;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PhasmidaRegistry {
    private static Map<String, Class<? extends Fragment>> registryMap;

    public static void register(String[] signWords, Class<? extends Fragment> fragmentClass) {
        if (registryMap == null) {
            synchronized (PhasmidaRegistry.class) {
                if (registryMap == null) {
                    registryMap = new HashMap<>();
                }
            }
        }
        if (signWords == null || signWords.length == 0) {
            throw new PhasmidaRegistryException("sign word is null ");
        }
        if (fragmentClass == null) {
            throw new PhasmidaRegistryException("fragmentClass is null");
        }
        for (String signWord : signWords) {
            Class<? extends Fragment> aClass = registryMap.get(signWord);
            if (aClass != null && aClass != fragmentClass) {
                throw new PhasmidaRegistryException("this sign word already be registered :" + signWord + ",for class :" + aClass.getSimpleName());
            }
            registryMap.put(signWord, fragmentClass);
            log.debug(fragmentClass.getCanonicalName() + " has been register");
        }
    }

    public static Map<String, Class<? extends Fragment>> getRegistryMap() {
        return registryMap;
    }
}
