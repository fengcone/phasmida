package com.fengcone.phasmida.fragment;

import com.fengcone.phasmida.core.StringContext;

/**
 * @author fengcone
 */
public class WithFragment extends AbstractFragment {
    String[] withWords;

    @Override
    public boolean process(StringContext context) {
        int nowIndex = context.getEndIndex();
        String tempString = context.getString().substring(nowIndex);
        for (String word : withWords) {
            int indexOf = tempString.indexOf(word);
            if (context.needBeHead()) {
                if (indexOf >= 0) {
                    context.setEndIndex(nowIndex + indexOf + word.length());
                    context.setStartIndex(nowIndex + indexOf);
                    context.putIndexPair(getFragmentsIndex(), context.getStartIndex(), context.getEndIndex());
                    context.setNextNeedBeHead(false);
                    return true;
                }
            } else {
                if (indexOf == 0) {
                    context.setEndIndex(nowIndex + word.length());
                    context.setStartIndex(nowIndex);
                    context.putIndexPair(getFragmentsIndex(), context.getStartIndex(), context.getEndIndex());
                    context.setNextNeedBeHead(false);
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public boolean processAfterNext(StringContext context) {
        return true;
    }

    @Override
    public void init(String[] words) {
        withWords = words;
    }

}
