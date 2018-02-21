package com.fengcone.phasmida.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fengcone.phasmida.exception.PhasmidaMutexException;
import com.fengcone.phasmida.exception.PhasmidaRegistryException;
import com.fengcone.phasmida.fragment.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PhasmidaRegistry {
    private static Map<String, Class<? extends Fragment>> registryMap;
    private static Map<String, String> mutexRelationMap;
    private static Set<Integer> mutexRelationSizeSet;

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
            log.info(fragmentClass.getCanonicalName() + " has been register");
        }
    }

    public static Map<String, Class<? extends Fragment>> getRegistryMap() {
        return registryMap;
    }

    @SuppressWarnings("unchecked")
    public static void addMutexRelation(Class<? extends Fragment>... mutexFragments) {
        if (mutexRelationMap == null) {
            synchronized (PhasmidaRegistry.class) {
                if (mutexRelationMap == null) {
                    mutexRelationMap = new HashMap<>();
                    mutexRelationSizeSet = new HashSet<>();
                }
            }
        }
        mutexRelationSizeSet.add(mutexFragments.length);
        mutexRelationMap.put(Arrays.stream(mutexFragments).map(Class::getCanonicalName).collect(Collectors.joining("-")), Arrays.stream(mutexFragments).map(Class::getSimpleName).collect(Collectors.joining("-")));
        log.info("register mutex relation :{}", Arrays.stream(mutexFragments).map(Class::getSimpleName).collect(Collectors.joining("-")));
    }

    public static void checkMutexRelation(Fragment[] fragments) {
        if (mutexRelationMap == null) {
            return;
        }
        List<String> fragmentsClassList = Arrays.stream(fragments).map(Fragment::getClass).map(Class::getCanonicalName).collect(Collectors.toList());
        for (Integer size : mutexRelationSizeSet) {
            for (int i = 0; i < fragments.length; i++) {
                if (i + size > fragments.length) {
                    continue;
                }
                List<String> subList = fragmentsClassList.subList(i, i + size);
                String simpleValue = mutexRelationMap.get(subList.stream().collect(Collectors.joining("-")));
                if (simpleValue != null) {
                    throw new PhasmidaMutexException(" mutex fragment relation for " + simpleValue);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void registerStandardFragments() {
        register(new String[]{"with"}, WithFragment.class);
        register(new String[]{"without"}, WithoutFragment.class);
        register(new String[]{"withAnything"}, WithAnythingFragment.class);
        register(new String[]{"withNumRange"}, WithNumRangeFragment.class);
        addMutexRelation(WithoutFragment.class, WithAnythingFragment.class);
        addMutexRelation(WithoutFragment.class, WithoutFragment.class);
        addMutexRelation(WithAnythingFragment.class, WithAnythingFragment.class);
        addMutexRelation(WithAnythingFragment.class, WithoutFragment.class);
        addMutexRelation(WithFragment.class, WithoutFragment.class, WithFragment.class);
        addMutexRelation(WithNumRangeFragment.class, WithoutFragment.class, WithNumRangeFragment.class);
        log.info(mutexRelationMap.values().toString());
    }
}
