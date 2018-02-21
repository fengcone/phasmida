package com.fengcone;

import com.fengcone.phasmida.core.Phasmida;
import com.fengcone.phasmida.core.PhasmidaFactory;
import com.fengcone.phasmida.registry.RegistryUtil;
import com.fengcone.phasmida.core.PhasmidaContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class HouseNumberTest {
    @Test
    public void testCase1() {
        RegistryUtil.registerStandardFragments();
        PhasmidaFactory factory = new PhasmidaFactory();
        Phasmida phasmida = factory.getPhasmida("withNumRange(0,10000).with(房,室,单元,-,号楼,栋,号院,房间,门,座,层).withNumRange(0,1000)");
        PhasmidaContext context = new PhasmidaContext("好景国际3号楼601");
        boolean process = phasmida.process(context);
        log.info(context.toString());
        assert process;
        context = new PhasmidaContext("好景国际3-601");
        process = phasmida.process(context);
        log.info(context.toString());
        assert process;
    }
}
/**
 * 好景国际3单元601房").equals("好景国际");
 * assert getSimpleAddress("好景国际3号楼601").equals("好景国际3号楼");
 * assert getSimpleAddress("好景国际601").equals("好景国际601");
 * assert getSimpleAddress("好景国际3-601").equals("好景国际3-601");
 * assert getSimpleAddress("好景国际三号楼601").equals("好景国际三号楼");
 * assert getSimpleAddress("好景国际三栋601").equals("好景国际三栋");
 * assert getSimpleAddress("好景国际三栋楼601").equals("好景国际三栋楼");
 * assert getSimpleAddress("好景国际3栋601").equals("好景国际3栋");
 * assert getSimpleAddress("好景国际3幢601").equals("好景国际3幢");
 * assert getSimpleAddress("好景国际3栋楼601").equals("好景国际3栋楼");
 * assert getSimpleAddress("好景国际9号院").equals("好景国际9号院");
 * assert getSimpleAddress("好景国际3单元601房间").equals("好景国际");
 * assert getSimpleAddress("好景国际3单元601房间 ~").equals("好景国际3单元601房间 ~");
 * assert getSimpleAddress("好景国际A座21FF").equals("好景国际A座21FF");
 * assert getSimpleAddress("好景国际A座21层").equals("好景国际");
 * assert getSimpleAddress("好景国际A座21F").equals("好景国际");
 * assert getSimpleAddress("好景国际B座四单元").equals("好景国际");
 * assert getSimpleAddress("好景国际50号").equals("好景国际50号");
 * assert getSimpleAddress("好景国际17-02门
 */
