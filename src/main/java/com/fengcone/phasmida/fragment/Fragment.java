package com.fengcone.phasmida.fragment;

import com.fengcone.phasmida.core.Phasmida;
import com.fengcone.phasmida.core.PhasmidaContext;

/**
 * @author fengcone
 */
public interface Fragment {

    /**
     * 非表达式头部方法
     *
     * @param context 处理的上下文
     * @return 是否成功被处理
     */
    boolean process(PhasmidaContext context);

    /**
     * 回调方法，执行完成下一个Fragment后需要返回判断的方法 典型fragment为without,withAnything
     *
     * @param context 处理的上下文
     * @return 处理成功与否
     */
    boolean processAfterNext(PhasmidaContext context);

    /**
     * 获得表达式中的关键词语
     *
     * @param phasmida       该Fragment所属的Phasmida
     * @param words          关键词语
     * @param fragmentsIndex 这个Fragment在表达式中为位置索引
     * @param signWord       注册时所用的关键词
     */
    void init(Phasmida phasmida, String[] words, int fragmentsIndex, String signWord);

}
