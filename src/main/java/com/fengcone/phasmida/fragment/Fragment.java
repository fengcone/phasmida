package com.fengcone.phasmida.fragment;

import com.fengcone.phasmida.core.StringContext;

/**
 * @author fengcone
 */
public interface Fragment {

	/**
	 * 非表达式头部方法
	 * 
	 * @param context
	 * @return
	 */
	boolean process(StringContext context);

	/**
	 * 回调方法，执行完成下一个Fragment后需要返回判断的方法 典型fragment为without,withAnything
	 * 
	 * @param context
	 * @return
	 */
	boolean processAfterNext(StringContext context);

	/**
	 * 获得表达式中的关键词语
	 * 
	 * @param words 关键词语
	 * @param fragmentsIndex 这个Fragment在表达式中为位置索引
	 */
	void init(String[] words, int fragmentsIndex,String signWord);
}
