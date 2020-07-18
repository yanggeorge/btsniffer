package com.threelambda.btsniffer.bt;

import com.google.gson.Gson;
import com.threelambda.btsniffer.bt.routingtable.DynamicRoutingTable;
import com.threelambda.btsniffer.bt.routingtable.KBucket;
import com.threelambda.btsniffer.bt.routingtable.Node;
import com.threelambda.btsniffer.bt.util.BitMap;
import com.threelambda.btsniffer.bt.util.DebugInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ym
 * @date 2019/10/29
 */
@Slf4j
public class DynamicRoutingTableTest {
    @Test
    public void test() {
        Gson gson = new Gson();
        String debugInfoString = "{\"localId\":\"0111000000110011011010010100100100110100001101010111001101101111011001000100001001011000001101010100100001001101010100010110101001101011011011100100001001010010\",\"insertNodeId\":\"1010001111100101010111111000101001010110101111010010101111101011011000010010011011110001100101100000101111011100110000110110001110100011010100110000011110100110\",\"nodes\":[{\"ip\":\"121.210.73.181\",\"port\":27032,\"nodeId\":\"0000010000111010100111111101011000011110000001100100110101011010011110101101110011010010010111110111110000001001110111111010010100000000001001100100111110100001\"},{\"ip\":\"177.102.102.140\",\"port\":63017,\"nodeId\":\"0110010001100100110000100011100110100011101100010111001010010101001110010000011100100111111110111111011100110111101100010101110110111101110100101101010101110110\"},{\"ip\":\"1.162.113.90\",\"port\":9761,\"nodeId\":\"1010101010000011010110000110101101101101111010000110100011000100000010010101101101110011101011100110010000111111001010001011000100110101011111111000111101000100\"},{\"ip\":\"99.249.15.89\",\"port\":24874,\"nodeId\":\"1110101001010101110010100010111110110111011100000111011101001100110111111000010000000011010001100100000000101101011011010110101000110011001111000111001101100000\"},{\"ip\":\"90.78.122.232\",\"port\":14974,\"nodeId\":\"1101101000101010100000100101001101000100011101000101000000100010011001011010001001001100110111100110001001011110010001101010011100100111111011000100001111111000\"},{\"ip\":\"194.223.78.232\",\"port\":59696,\"nodeId\":\"1111010011100010000011101011010010101101010110111000101011100000000110101000011100010111011010000110001011011110001011000111001110010011111000001101101001101000\"},{\"ip\":\"5.22.205.176\",\"port\":18071,\"nodeId\":\"0100101000111001000101110011101010110110011111110001101010011010011101000101010110110101010110111110011010110001110011001010100000000011100100111010100010010011\"},{\"ip\":\"185.184.171.41\",\"port\":47993,\"nodeId\":\"0111000110100100110010011111100000101010111001000001000100010010000000101000100100101000100010111001000011101100000101110110010110111100000110101010011110001100\"},{\"ip\":\"88.238.105.122\",\"port\":3443,\"nodeId\":\"0100101011000000010001010111010110100000011100101001111011101110011010001001001000100101100000100010011100011110101110111000010101111100000001011000010000000000\"},{\"ip\":\"93.103.141.103\",\"port\":6881,\"nodeId\":\"0011101100100000100000001110010001111111101100001001101100000010001011100001010110110010100010000010101011001001101000111101001010101010110101110001000001111101\"},{\"ip\":\"95.67.162.62\",\"port\":53121,\"nodeId\":\"1111011001001001001110011111100001101000011001011001110010001000001100011110000010000111111001111011111110100110101111101010001001111010010101000100111011100100\"},{\"ip\":\"77.253.228.49\",\"port\":64730,\"nodeId\":\"1110000011100000011111111000001010100001011100111010010000010101101011001100010000111101101101111101101001100110011011101010011010010010100110101101000000110101\"},{\"ip\":\"31.168.254.73\",\"port\":52229,\"nodeId\":\"0110011001001110001010111101000101001000110100110011011011100011010000100000110101010011011100110111010000111111111001110001011001100001001001111001000001101110\"},{\"ip\":\"121.161.36.251\",\"port\":37949,\"nodeId\":\"1011110010100000001001111111101110111111111100100000010111100011101001101000110001011101001110101010011001111011010001001011001100110101001001110000111000010001\"},{\"ip\":\"82.221.103.244\",\"port\":6881,\"nodeId\":\"1110101111111111001101100110100101110011010100011111111101001010111011000010100111001101101110101010101111110010111110111110001101000110011111001100001001100111\"},{\"ip\":\"60.227.141.198\",\"port\":6881,\"nodeId\":\"0111111001011111111000101100010100001100111101110101110010011010011101100111011110010110111110000010010110001000010110011100110011111010001000001101001101100110\"},{\"ip\":\"67.215.246.10\",\"port\":6881,\"nodeId\":\"0011001011110101010011100110100101110011010100011111111101001010111011000010100111001101101110101010101111110010111110111110001101000110011111001100001001100111\"},{\"ip\":\"2.93.62.85\",\"port\":49653,\"nodeId\":\"0100010111011111000010110000010000100101100011100010011110011011011111111101110000101100010000101001100001010001000110011000000111011010100111101110000001001101\"},{\"ip\":\"37.21.254.12\",\"port\":6881,\"nodeId\":\"0010101111101000110101001101001011111011011100010110111101001010001010100100010001010001000110100111101111101001101110101010010111010011110001011011000100011011\"},{\"ip\":\"87.98.162.88\",\"port\":6881,\"nodeId\":\"0011110000000000011100100111001101001000101100111011100011101101011100001011101010100001111000010100000100011011001110000110100111011000010010000001001100100001\"}]}";
        DebugInfo debugInfo = gson.fromJson(debugInfoString, DebugInfo.class);

        List<DebugInfo.DebugNode> nodes = debugInfo.getNodes();
        List<Node> nodeList = nodes.stream().map(debugNode -> {
            return new Node(BitMap.fromHumanString(debugNode.getNodeId()), debugNode.getIp(), debugNode.getPort());
        }).collect(Collectors.toList());


        String localId = debugInfo.getLocalId();
        DynamicRoutingTable routingTable = new DynamicRoutingTable(localId);
        for (Node node : nodeList) {
            routingTable.tryInsert(node);
        }

        KBucket kBucket = routingTable.getKBucket(BitMap.fromHumanString(debugInfo.getInsertNodeId()));
        assert kBucket!= null;

        KBucket kBucket1 = routingTable.getKBucket(localId);
        assert kBucket1 != null;

        assert routingTable.getPrefixKBucketMap().size() == 3;

        routingTable.logMetric();
    }


    @Test
    public void test2() {
        BitMap localId = new BitMap(160).set(0).set(2).set(3);
        System.out.println(localId.toHumanString());
        DynamicRoutingTable rt = new DynamicRoutingTable(localId);
        BitMap id1 = new BitMap(160).set(0).set(2).set(3);
        BitMap id2 = new BitMap(160).set(1).set(2).set(3);
        BitMap id3 = new BitMap(160).set(0).set(2).set(4);
        BitMap id4 = new BitMap(160).set(1).set(2).set(6);
        BitMap id5 = new BitMap(160).set(0).set(2).set(6);
        //与id5相同则不会被添加
        BitMap id6 = new BitMap(160).set(0).set(2).set(6);
        BitMap id7 = new BitMap(160).set(0).set(2).set(7);
        BitMap id8 = new BitMap(160).set(0).set(2).set(8);
        BitMap id9 = new BitMap(160).set(0).set(1).set(9);
        BitMap id10 = new BitMap(160).set(0).set(2).set(10);
        BitMap id11 = new BitMap(160).set(0).set(2).set(11);
        rt.tryInsert(new Node(id1.getData(), "0.0.0.1", 10));
        rt.tryInsert(new Node(id2, "0.0.0.2", 10));
        rt.tryInsert(new Node(id3, "0.0.0.3", 10));
        rt.tryInsert(new Node(id4, "0.0.0.4", 10));
        rt.tryInsert(new Node(id5, "0.0.0.5", 10));
        rt.tryInsert(new Node(id6, "0.0.0.6", 10));
        rt.tryInsert(new Node(id7, "0.0.0.7", 10));
        rt.tryInsert(new Node(id8, "0.0.0.8", 10));
        rt.tryInsert(new Node(id9, "0.0.0.9", 10));
        rt.tryInsert(new Node(id10, "0.0.0.10", 10));
        rt.tryInsert(new Node(id11, "0.0.0.11", 10));
        ;
        System.out.println("---");
        rt.getAddrNodePairMap().values().forEach(pair->System.out.println(pair.left.getId().toHumanString()));
        System.out.println("---");

        BitMap targetId = new BitMap(160);
        List<Node> list = rt.getNearest(targetId, 8);

        for (Node node : list) {
            System.out.println(node.getId().toHumanString());
        }

        KBucket kBucket = rt.getKBucket(id1);
        if (kBucket == null) {
            log.info("is null");
        }
        rt.logMetric();
        rt.removeByAddr("/0.0.0.11:10");
        rt.logMetric();
        BitMap id12 = new BitMap(160).set(0).set(2).set(12);
        rt.tryInsert(new Node(id12, "0.0.0.10", 10));
        rt.logMetric();

    }
}