package com.fengcone.phasmida.core;

import com.fengcone.phasmida.exception.PhasmidaRegistryException;
import com.fengcone.phasmida.fragment.Fragment;
import com.fengcone.phasmida.registry.MutexRelationRegistry;
import com.fengcone.phasmida.registry.PhasmidaRegistry;

/**
 * @author fengcone
 */
public class PhasmidaFactory {

    public Phasmida getPhasmida(String regex) {
        if (regex == null || regex.length() == 0) {
            throw new IllegalArgumentException("error regex for " + regex);
        }
        String[] split = getSplit(regex, "\\)\\.", ").");
        Fragment[] fragments = new Fragment[split.length];
        Phasmida phasmida = new Phasmida(fragments);
        for (int i = 0; i < split.length; i++) {
            String string = split[i];
            int indexOf = string.indexOf("(");
            String signWord = string.substring(0, indexOf);
            String originWords = string.substring(indexOf + 1);
            if (originWords.endsWith(")")) {
                originWords = originWords.substring(0, originWords.length() - 1);
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
            newInstance.init(phasmida, getSplit(originWords, ",", ","), i, signWord);
            fragments[i] = newInstance;
        }
        MutexRelationRegistry.checkMutexRelation(fragments);
        return phasmida;
    }

    private String[] getSplit(String originWords, String regexSplitWord, String originSplitWord) {
        String[] split = originWords.split("(?!')" + regexSplitWord + "(?!')");
        for (int i = 0; i < split.length; i++) {
            String word = split[i];
            split[i] = word.replace("'" + originSplitWord + "'", originSplitWord);
        }
        return split;
    }

}
