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
			//TODO  回溯的方法可以再考虑下，可以回溯到初始的endIndex,但是必须以不同方式去匹配，with(天津,天津市)
			// 回溯到里的时候，如果上一次是用天津匹配成功的，就不能以天津再去匹配了，而是需要天津市
			// 可以再context 中加入是否完成某个fragment的所有结果，
			// 如果完成了所有结果，那么回溯到nowIndex,如果不是，回溯到最初始的Index
			if (!result && fragmentsIndex != 0 && nowIndex != context.getEndIndex() && context.getEndIndex() != context.getStringLength()) {
				fragmentsIndex = 0;
				lastFragment = null;
				nowIndex = context.getEndIndex();
				context.clearIndexPair();
				context.setNextNeedBeHead(true);
				continue;
			}
			if (!result) {
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
