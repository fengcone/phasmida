# phasmida
A Java String Tool
#Phasmida



##1.什么是phasmida？
phasmida 是一个字符串的处理工具，旨在通过一段简单的链式表达式描述并匹配出字符串中某些存在的特征，作为程序员的你应该能想到另外一个的东西，正则表达式。工作中有项目需要大量针对字符串做match，识别其中的特征，因为正则表达式读写都比较费劲，很多东西又需要自定义，于是最终就有了phasmida。但是Phasmida并不是为了完全替代正则，提供一种新的实现方式，使得可以更加灵活地处理该字符串特征问题。


####    phasmida的一些设计：
````
链式表达式

读写都很容易，对初学者友好

自定义规则片段，提高灵活性
````
 phasmida [fæz'maɪdə] 是一个生物学上的名词，译为竹节虫目，项目也取其本意，表达式一节节链式组成，像一个竹节虫。
  ![竹节虫（来源见水印)](http://upload-images.jianshu.io/upload_images/5526043-1b5a48e7b02abc7f.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

项目源码地址：https://github.com/fengcone/phasmida



##      2.使用phasmida
 添加依赖：
````
<dependency>
      <groupId>com.github.fengcone</groupId>
      <artifactId>phasmida</artifactId>
      <version>1.1</version>
</dependency>
````
第一个phasmida：识别某几个字符串
````
    RegistryUtil.registerStandardFragments();\\注册标准片段库
    PhasmidaFactory factory = new PhasmidaFactory(); \\phasmida工厂类
    Phasmida phasmida = factory.getPhasmida("with(Hello).with( world, phasmida)");\\通过 表达式获取Phasmida对象
    PhasmidaContext context = new PhasmidaContext("Hello world");\\定义上下文对象
    boolean process = phasmida.process(context);\\调用处理方法，返回是否能被匹配上，匹配的其他信息会在Context对象中
````
最终Context对象:
PhasmidaContext [string=Hello phasmida, endIndex=14, startIndex=5, indexPairs=[IndexPair     [startIndex=0, endIndex=5, fragmentsIndex=0], IndexPair [startIndex=5, endIndex=14, fragmentsIndex=1]]]



##      3.Phasmida的核心设计

####    phasmida表达式
      with(北京).without(路,街,银行)
所有的Phasmida表达式由两个部分组成，signWord 以及括号中的参数，如上表达式中with以及without为signword，而括号中的“北京”,“路”，“银行”等 为参数


####        Fragment接口

````
public interface Fragment {

    /**
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

````


####      fragment之间方法调用顺序
![方法调用顺序](http://upload-images.jianshu.io/upload_images/5526043-215b714fbfa15256.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

init方法是在初始化时，解析参数后传入



####        注册中心
com.fengcone.phasmida.registry.PhasmidaRegistry
````
\\通过向注册中心注册，注册，第一个参数是signWord，第二个参数是具体实现，注意，实现类必须实现Fragment接口
PhasmidaRegistry.register(new String[]{"with"}, WithFragment.class);
````


####        互斥关系注册
某一些Fragment，彼此之间不能组成有效的Phasmida表达式，如“without(非).withAnything(5) ”等，可以通过注册互斥关系来避免，通过调用以下方法实现
com.fengcone.phasmida.registry.MutexRelationRegistry
````
\\这个方法是一个变长参数，可接受多个Fragment对象，代表这样组合的Fragment为互斥关系
MutexRelationRegistry.addMutexRelation(WithoutFragment.class, WithAnythingFragment.class);
````


####        PhasmidaContext  上下文
该对象为处理过程的中间对象，fragment的process方法以及processAfterNext，以及Phasmida对象都是在处理过程总 都是通过该上下文传递数据，感知环境
````
    \\需要被处理的字符串
    String string;

    \\最后一个成功的Fragment的结束游标
    int endIndex;

    \\最后一个成功的Fragment的开始游标
    int startIndex;

    \\所有成功的Fragment中匹配成功的开始与结束游标
    List<IndexPair> indexPairs;

    \\通过设置或获取该值，感知环境中是否应该使用头部方法
    boolean nextNeedBeHead;

    \\最终整个Phasmida的处理结果
    boolean result;
````


####        PhasmidaFactory 工厂类
 该类通过接受一个字符串表达式实例化一个Phasmida对象，典型用法如下：
````
Phasmida phasmida =  new PhasmidaFactory().getPhasmida("with(Hello).with( world, phasmida)");
````


##      4.标准Fragment库介绍


####        withFragment
表达式：with(王).with(建国,建军)
以上表示匹配：“王建国” 、“王建军”   两个字符串，参数中匹配的是或者的关系，且必须匹配上一个


####        withoutFragment
表达式：without(黑).with(龙泉镇)
以上匹配 ：“龙泉镇” 字符串，且不能为“黑龙泉镇”

表达式：with(龙泉).without(乡)
以上匹配 ：“龙泉” 字符串，但不能为“龙泉乡”



####        withAnythingFragment
表达式：withAnything(5).with(北京)
以上匹配的是带有“北京“的字符串，并且”北京“前面的字符串不能超过5个

表达式：with(北京).withAnything(5).with(朝阳)

以上匹配的是带有”北京“和”朝阳“的字符串，并且 ”北京“与”朝阳“ 之间的其他字符串不超过5个



####        withNumRangeFragment
表达式:withNumRange(100,200)

以上匹配的是 字符串的的数字，并且这个数字在100到200之间，



####        withCharRangeFragment
表达式：withCharRange(0-9,A-Z,a-z,-,_,2+)

该表达式中除了最后一个参数，其他均代表范围，如果最终程序会解析以”-“ 分割的范围，如正则表达式中 0-9A-Z 等，这个最终代表的是在unicode中这些Char的数字范围，会将待匹配字符串中单个字符进行范围比较，如果不以”-“分割，那么代表能够对单char进行匹配如上表达式中的 ”-,_,“ 部分。

该表达式最后一个 参数形式为"2+","2","2-"   表示为参数中的范围最终能匹配的个数，分别表示，至少匹配两次，只能匹配两次，至多匹配两次。

另外该表达式支持unicode
如：withCharRange(\u0031-\u0033,2)  表达式等同于 withCharRange(1-3,2)



####        headFragment & tailFragment

表达式：head().with(开心,悲伤).tail()
这两个表达式没有参数，必须与其他的Fragment搭配使用，代表掐头去尾，与正则中 ^ 和$ 功能一致

标准库还在持续完善中....



##      5.典型应用


识别邮箱：
````
withCharRange(0-9,A-Z,a-z,-,_,1+).with(@).withCharRange(0-9,A-Z,a-z,-,_,1+).with(.).withCharRange(0-9,A-Z,a-z,-,_,1+)
````
识别电话号码：
````
with(1).with(3,9,7,5,4).withCharRange(0-9,9)
````

识别门牌号：
````
withNumRange(0,1000).with(房,室,单元,-,号楼,栋,号院,房间,门,座,层).withNumRange(0,1000)
````



##      6.贡献代码
如果您能向该项目贡献代码，维护者将不胜感激。当您发现问题或者想要贡献代码，可以生成一个 [issue](https://github.com/fengcone/phasmida/issues) 或一个[pull request](https://github.com/fengcone/phasmida/pulls).
