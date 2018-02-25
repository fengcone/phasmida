package com.fengcone.phasmida.fragment;

import com.fengcone.phasmida.core.PhasmidaContext;

import java.util.ArrayList;
import java.util.List;

public class WithCharRangeFragment extends AbstractFragment {
    int frequency;
    List<RangePair> rangePairs;
    FrequencyType frequencyType = FrequencyType.EQUALS;

    @Override
    protected void init(String[] words) {
        //最后一个参数是匹配次数
        //withCharRange(a-b,\u2018-9,王-八);
        if (words == null || words.length < 2) {
            throw new IllegalArgumentException("error expression for withCharRangeFragment index of " + getFragmentsIndex());
        }
        String frequencyWord = words[words.length - 1];
        if (frequencyWord.endsWith("-")) {
            frequencyType = FrequencyType.LESS_THAN;
            frequencyWord = frequencyWord.substring(0, frequencyWord.length() - 1);
        } else if (frequencyWord.endsWith("+")) {
            frequencyType = FrequencyType.AT_LEAST;
            frequencyWord = frequencyWord.substring(0, frequencyWord.length() - 1);
        }
        try {
            frequency = Integer.valueOf(frequencyWord);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("the last argument of this withCharRangeFragment for frequency is illegal :" + words[words.length - 1], e);
        }
        rangePairs = new ArrayList<>(words.length - 1);
        for (int i = 0; i < words.length - 1; i++) {
            String range = words[i];
            String[] split = range.split("-");
            if (split.length != 2) {
                throw new IllegalArgumentException("the range " + range + " in withCharRangeFragment is illegal : error expression");
            }
            RangePair pair = new RangePair();
            if (split[0].startsWith("\\u")) {
                pair.rangeStart = (char) Integer.parseInt(split[0].substring(2), 16);
            } else {
                if (split[0].length() != 1) {
                    throw new IllegalArgumentException("the range " + range + " in withCharRangeFragment is illegal : error expression");
                }
                pair.rangeStart = split[0].charAt(0);
            }
            if (split[1].startsWith("\\u")) {
                pair.rangeEnd = (char) Integer.parseInt(split[1].substring(2), 16);
            } else {
                if (split[1].length() != 1) {
                    throw new IllegalArgumentException("the range " + range + " in withCharRangeFragment is illegal : error expression");
                }
                pair.rangeEnd = split[1].charAt(0);
            }
            if (pair.rangeStart > pair.rangeEnd) {
                throw new IllegalArgumentException("the range " + range + " in withCharRangeFragment is illegal : the start must be less than the end");
            }
            rangePairs.add(pair);
        }
    }

    @Override
    public boolean process(PhasmidaContext context) {
        int processCount = 0;
        int endIndex = context.getEndIndex();
        boolean result = false;
        for (int i = 0; i < frequency; i++) {
            if (!processOnce(context)) {
                break;
            }
            processCount++;
        }
        if (frequencyType == FrequencyType.EQUALS) {
            if (processCount == frequency && !processOnce(context)) {
                result = true;
            }
        } else if (frequencyType == FrequencyType.LESS_THAN) {
            if (processCount < frequency) {
                result = true;
            }
            if (processCount == frequency && !processOnce(context)) {
                result = true;
            }
        } else if (frequencyType == FrequencyType.AT_LEAST) {
            if (processCount == frequency) {
                result = true;
                do {
                    if (!processOnce(context)) {
                        break;
                    }
                    processCount++;
                } while (true);
            }
        }
        if (result) {
            context.setStartIndex(context.getEndIndex() - processCount);
            context.putIndexPair(getFragmentsIndex(), context.getStartIndex(), context.getEndIndex());
            context.setNextNeedBeHead(false);
            return true;
        }
        context.setEndIndex(endIndex);
        return false;
    }

    private boolean processOnce(PhasmidaContext context) {
        for (RangePair pair : rangePairs) {
            if (context.needBeHead()) {
                int charIndex = context.getEndIndex();
                do {
                    if (charIndex + 1 > context.getStringLength()) {
                        return false;
                    }
                    char chr = context.getString().charAt(charIndex);
                    if (pair.rangeStart <= chr && pair.rangeEnd >= chr) {
                        context.setEndIndex(charIndex + 1);
                        context.setNextNeedBeHead(false);
                        return true;
                    }
                    charIndex++;
                } while (charIndex != context.getStringLength());
            } else {
                if (context.getEndIndex() >= context.getStringLength()) {
                    return false;
                }
                if (pair.rangeStart <= context.getString().charAt(context.getEndIndex()) && pair.rangeEnd >= context.getString().charAt(context.getEndIndex())) {
                    context.setEndIndex(context.getEndIndex() + 1);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean processAfterNext(PhasmidaContext context) {
        return true;
    }

    class RangePair {
        char rangeStart;
        char rangeEnd;
    }

    enum FrequencyType {
        AT_LEAST, EQUALS, LESS_THAN;
    }

    public static void main(String[] args) {
        String unicode = "\\u80be";
        int i = Integer.parseInt(unicode.substring(2), 16);
        System.out.println(i);
        System.out.println(new Character((char) i));
    }
}
