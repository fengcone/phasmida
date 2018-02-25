package com.fengcone.phasmida.registry;

import com.fengcone.phasmida.fragment.*;

public class RegistryUtil {

    @SuppressWarnings("unchecked")
    public static void registerStandardFragments() {
        PhasmidaRegistry.register(new String[]{"with"}, WithFragment.class);
        PhasmidaRegistry.register(new String[]{"without"}, WithoutFragment.class);
        PhasmidaRegistry.register(new String[]{"withAnything"}, WithAnythingFragment.class);
        PhasmidaRegistry.register(new String[]{"withNumRange"}, WithNumRangeFragment.class);
        PhasmidaRegistry.register(new String[]{"head"}, HeadFragment.class);
        PhasmidaRegistry.register(new String[]{"tail"}, TailFragment.class);
        PhasmidaRegistry.register(new String[]{"withCharRange"}, WithCharRangeFragment.class);
        MutexRelationRegistry.addMutexRelation(WithoutFragment.class, WithAnythingFragment.class);
        MutexRelationRegistry.addMutexRelation(WithoutFragment.class, WithoutFragment.class);
        MutexRelationRegistry.addMutexRelation(WithAnythingFragment.class, WithAnythingFragment.class);
        MutexRelationRegistry.addMutexRelation(WithAnythingFragment.class, WithoutFragment.class);
        MutexRelationRegistry.addMutexRelation(WithFragment.class, WithoutFragment.class, WithFragment.class);
        MutexRelationRegistry.addMutexRelation(WithNumRangeFragment.class, WithoutFragment.class, WithNumRangeFragment.class);
    }
}
