package com.fengcone.phasmida.fragment;

import com.fengcone.phasmida.core.PhasmidaContext;

public class TailFragment extends AbstractFragment {
    @Override
    protected void init(String[] words) {
        if (getFragmentsIndex() + 1 != getPhasmida().getFragments().length) {
            throw new IllegalStateException("tail fragment is not the last fragment!");
        }
    }

    @Override
    public boolean process(PhasmidaContext context) {
        return context.getEndIndex() == context.getStringLength();
    }

    @Override
    public boolean processAfterNext(PhasmidaContext context) {
        return false;
    }
}
