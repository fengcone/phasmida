package com.fengcone.phasmida.core;

import com.fengcone.phasmida.exception.PhasmidaRegistryException;
import com.fengcone.phasmida.fragment.Fragment;

/**
 * @author fengcone
 */
public class PhasmidaFactory {

    public Phasmida getPhasmida(String regex) {
        if (regex == null || regex.length() == 0) {
            throw new IllegalArgumentException("error regex for " + regex);
        }
        String[] split = regex.split("\\)\\.");
        Fragment[] fragments = new Fragment[split.length];
        for (int i = 0; i < split.length; i++) {
            String string = split[i];
            int indexOf = string.indexOf("(");
            String signWord = string.substring(0, indexOf);
            String words = string.substring(indexOf + 1);
            if (words.endsWith(")")) {
                words = words.substring(0, words.length() - 1);
            }
            Class<? extends Fragment> aClass = PhasmidaRegistry.getRegistryMap().get(signWord);
            if (aClass == null) {
                throw new PhasmidaRegistryException("could not found sign word:" + signWord);
            }
            Fragment newInstance;
            try {
                newInstance = aClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            newInstance.init(words.split(","), i, signWord);
            fragments[i] = newInstance;
        }
        PhasmidaRegistry.checkMutexRelation(fragments);
        return new Phasmida(fragments);
    }
}
