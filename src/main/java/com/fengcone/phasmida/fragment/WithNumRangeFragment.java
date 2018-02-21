package com.fengcone.phasmida.fragment;

import com.fengcone.phasmida.core.StringContext;

public class WithNumRangeFragment extends AbstractFragment {
    private long rangeStart;
    private long rangeEnd;

    @Override
    public boolean process(StringContext context) {
        if (!context.needBeHead()) {
            char chr = context.getString().charAt(context.getEndIndex());
            if (chr < '0' || chr > '9') {
                return false;
            }
        }
        boolean numFlag = false;
        StringBuilder sb = new StringBuilder();
        int numCount = 0;
        int nahNumCount = 0;
        for (int i = context.getEndIndex(); i < context.getStringLength(); i++) {
            char chr = context.getString().charAt(i);
            if (chr >= '0' && chr <= '9') {
                numFlag = true;
                sb.append(chr);
                numCount++;
                continue;
            } else if (numFlag) {
                break;
            }
            nahNumCount++;
        }
        if (sb.length() == 0 || sb.length() > 19) {
            return false;
        }
        long value = Long.valueOf(sb.toString());
        if (value >= rangeStart && value <= rangeEnd) {
            context.setStartIndex(context.getEndIndex() + nahNumCount);
            context.setEndIndex(context.getEndIndex() + numCount + nahNumCount);
            context.setNextNeedBeHead(false);
            context.putIndexPair(getFragmentsIndex(), context.getStartIndex(), context.getEndIndex());
            return true;
        }
        return false;
    }

    @Override
    public boolean processAfterNext(StringContext context) {
        return true;
    }

    @Override
    protected void init(String[] words) {
        if (words == null || words.length != 2) {
            throw new IllegalArgumentException("error size of words for withNumRangeFragment");
        }
        rangeStart = Long.valueOf(words[0]);
        rangeEnd = Long.valueOf(words[1]);
        if (rangeStart > rangeEnd) {
            throw new IllegalArgumentException("rangStart could not be larger than rangeEnd");
        }
    }

}
