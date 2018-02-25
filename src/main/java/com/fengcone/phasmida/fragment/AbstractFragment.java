package com.fengcone.phasmida.fragment;

import com.fengcone.phasmida.core.Phasmida;

public abstract class AbstractFragment implements Fragment {

    private int fragmentsIndex;
    private String signWord;
    private Phasmida phasmida;

    protected abstract void init(String[] words);

    @Override
    public void init(Phasmida phasmida, String[] words, int fragmentsIndex, String signWord) {
        this.fragmentsIndex = fragmentsIndex;
        this.signWord = signWord;
        this.phasmida = phasmida;
        init(words);
    }

    public int getFragmentsIndex() {
        return fragmentsIndex;
    }

    public String getSignWord() {
        return signWord;
    }

    public Phasmida getPhasmida() {
        return phasmida;
    }
}
