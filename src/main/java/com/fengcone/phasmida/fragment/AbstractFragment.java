package com.fengcone.phasmida.fragment;

public abstract class AbstractFragment implements Fragment {

    private int fragmentsIndex;
    private String signWord;

    protected abstract void init(String[] words);

    @Override
    public void init(String[] words, int fragmentsIndex, String signWord) {
        this.fragmentsIndex = fragmentsIndex;
        this.signWord = signWord;
        init(words);
    }

    public int getFragmentsIndex() {
        return fragmentsIndex;
    }

    public String getSignWord() {
        return signWord;
    }
}
