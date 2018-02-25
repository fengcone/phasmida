package com.fengcone.phasmida.fragment;

import com.fengcone.phasmida.core.IndexPair;
import com.fengcone.phasmida.core.PhasmidaContext;

public class WithAnythingFragment extends AbstractFragment {

    int anythingCount;


    @Override
    public boolean process(PhasmidaContext context) {
        context.setNextNeedBeHead(true);
        return true;
    }

    @Override
    public boolean processAfterNext(PhasmidaContext context) {
        IndexPair lastPair = context.getLastIndexPair(1);
        if (lastPair == null) {
            return false;
        }
        IndexPair lastSecondPair = context.getLastIndexPair(2);
        boolean result;
        if (lastSecondPair == null) {
            result = lastPair.getStartIndex() <= anythingCount;
            if (result) {
                context.putIndexPair(getFragmentsIndex(), 0, context.getStartIndex());
            }
        } else {
            result = lastPair.getStartIndex() - lastSecondPair.getEndIndex() <= anythingCount;
            if (result) {
                context.putIndexPair(getFragmentsIndex(), lastSecondPair.getEndIndex(), lastPair.getStartIndex());
            }
        }
        return result;
    }

    @Override
    public void init(String[] words) {
        if (words == null || words.length == 0) {
            throw new IllegalArgumentException("error expression for withAnythingFragment :" + words);
        }
        anythingCount = Integer.valueOf(words[0]);
    }
}
