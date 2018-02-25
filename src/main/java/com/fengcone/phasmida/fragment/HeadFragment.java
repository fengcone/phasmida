package com.fengcone.phasmida.fragment;

import com.fengcone.phasmida.core.PhasmidaContext;

public class HeadFragment extends AbstractFragment {
    @Override
    protected void init(String[] words) {
        if (getFragmentsIndex() != 0) {
            throw new IllegalStateException("the head fragment is not the first fragment!");
        }
    }

    @Override
    public boolean process(PhasmidaContext context) {
        context.setNextNeedBeHead(true);
        return context.getEndIndex() == 0;
    }

    @Override
    public boolean processAfterNext(PhasmidaContext context) {
        return true;
    }
}
