package com.fengcone.phasmida.core;

import com.fengcone.phasmida.fragment.Fragment;

public class Phasmida {
    private Fragment[] fragments;

    public Phasmida(Fragment[] fragments) {
        this.fragments = fragments;
    }

    public Fragment[] getFragments() {
        return fragments;
    }

    public boolean process(PhasmidaContext context) {
        boolean result;
        Fragment lastFragment = null;
        int fragmentsIndex = 0;
        int nowIndex = 0;
        do {
            Fragment fragment = fragments[fragmentsIndex];
            result = fragment.process(context);
            if (result && lastFragment != null) {
                result = lastFragment.processAfterNext(context);
            }
            if (!result && fragmentsIndex != 0 && nowIndex != context.getEndIndex() && context.getEndIndex() != context.getStringLength()) {
                fragmentsIndex = 0;
                lastFragment = null;
                nowIndex = context.getEndIndex();
                context.clearIndexPair();
                context.setNextNeedBeHead(true);
                continue;
            }
            if (!result) {
                context.clearIndexPair();
                context.setEndIndex(0);
                context.setStartIndex(0);
                context.setResult(result);
                return result;
            }
            fragmentsIndex++;
            lastFragment = fragment;
        } while (fragmentsIndex != fragments.length);
        context.setResult(result);
        return result;
    }

}
