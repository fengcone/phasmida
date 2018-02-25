package com.fengcone.phasmida.registry;

import com.fengcone.phasmida.exception.PhasmidaMutexException;
import com.fengcone.phasmida.fragment.Fragment;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class MutexRelationRegistry {
    private static Map<String, String> mutexRelationMap;
    private static Set<Integer> mutexRelationSizeSet;
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
}
