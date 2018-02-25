package com.fengcone.phasmida.fragment;

import com.fengcone.phasmida.core.PhasmidaContext;

public class WithoutFragment extends AbstractFragment {

    private String[] withoutWords;

    @Override
    public boolean process(PhasmidaContext context) {
        if (getFragmentsIndex() == 0) {
            context.setNextNeedBeHead(true);
            return true;
        }
        String tempString = context.getString().substring(context.getEndIndex());
        int maxWordLength = 0;
        for (String word : withoutWords) {
            if (tempString.startsWith(word)) {
                return false;
            }
            if (word.length() > maxWordLength) {
                maxWordLength = word.length();
            }
        }
        int pairEndIndex = context.getEndIndex() + maxWordLength > context.getString().length() ? context.getString().length() : context.getEndIndex() + maxWordLength;
        context.putIndexPair(getFragmentsIndex(), context.getEndIndex(), pairEndIndex);
        return true;
    }

    @Override
    public boolean processAfterNext(PhasmidaContext context) {
        String tempString = context.getString().substring(0, context.getStartIndex());
        int maxWordLength = 0;
        for (String word : withoutWords) {
            if (tempString.endsWith(word)) {
                return false;
            }
            if (word.length() > maxWordLength) {
                maxWordLength = word.length();
            }
        }
        maxWordLength = context.getStartIndex() > maxWordLength ? maxWordLength : 0;
        context.putIndexPair(getFragmentsIndex(), context.getStartIndex() - maxWordLength, context.getStartIndex());
        context.setNextNeedBeHead(true);
        return true;
    }

    @Override
    public void init(String[] words) {
        withoutWords = words;
    }

}
