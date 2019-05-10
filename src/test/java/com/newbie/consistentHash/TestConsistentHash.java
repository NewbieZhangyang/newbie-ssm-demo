package com.newbie.consistentHash;

import org.junit.Test;

import java.util.*;

/**
 * 测试：一致性hash算法的使用
 */
public class TestConsistentHash {
    /**
     * 模拟数据库，存储数据
     */
    private static Map<String, Integer> nodeDatabase = new HashMap<String, Integer>();
    /**
     * 模拟服务器节点集群
     */
    private static List<PcNode> nodes = new ArrayList<PcNode>(0);

    /**
     * 测试一：使用一致性hash算法，进行普通存储
     *
     * @param args
     */
    public static void main(String[] args) {
        long sTime = System.currentTimeMillis();
        int countPcNode = 8;
        int numberOfReplicas = 79;
        int countPerson = 345670;
        setPcNodeMap(countPcNode);
        runApp(numberOfReplicas, countPerson);
        printNodeDatabase();
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - sTime) + " 毫秒");
    }

    /**
     * 打印输出数据
     */
    public static void printNodeDatabase() {
        int index = 0;
        int totalNum = 0;
        Set<Map.Entry<String, Integer>> entrySet = nodeDatabase.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            String ip = entry.getKey();
            int countPerson = entry.getValue();
            System.out.println((++index) + ": ip = " + ip + " ,总人数：" + countPerson);
            totalNum += countPerson;
        }
        System.out.println("总人数：" + totalNum);
    }

    /**
     * 模拟向数据库存取数据
     *
     * @param numberOfReplicas : 每个机器节点关联的虚拟节点个数
     * @param countPerson      : 要保存到数据库多少个用户
     */
    public static void runApp(int numberOfReplicas, int countPerson) {
        //初始化一致性hash算法的实例对象
        IHashFunction hashFunction = new HashFunction();   // hash 函数接口
        ConsistentHash<PcNode> consistentHash = new ConsistentHash(hashFunction, numberOfReplicas, nodes);
        //随机生成用户，保存到数据库中
        for (int i = 0; i < countPerson; i++) {
            String idno = getIdno();
            Person person = new Person();
            person.setIdno(idno);
            //计算存储的节点
            PcNode node = consistentHash.get(idno);
            //存入数据，
            String ip = node.getIp();
            int counrrentCountNum = nodeDatabase.get(ip);
            nodeDatabase.put(ip, counrrentCountNum + 1);
        }
    }

    /**
     * 设置真实机器节点
     *
     * @param countPcNode ：真实机器数量
     */
    public static void setPcNodeMap(int countPcNode) {
        String prefixIp = "192";
        for (int i = 0; i < countPcNode; i++) {
            //随机生成 ip地址
            String ip = prefixIp;
            for (int j = 0; j < 3; j++) {
                int random = new Random().nextInt(255);
                ip += "." + random;
            }
            //初始化机器节点
            PcNode node = new PcNode();
            node.setIp(ip);
            //node.setName(String.valueOf(new HashFunction().hash(ip)));
            //System.out.println(node);
            //将机器节点数据，赋值给属性：nodes
            nodes.add(node);
            nodeDatabase.put(ip, 0);
        }
    }

    /**
     * 生成身份证号ID
     *
     * @return
     */
    public static String getIdno() {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < 18; i++) {
            int random = new Random().nextInt(10);
            if (random == 0) {
                random = 4;
            }
            sb.append(random);
        }
        return sb.toString();
    }

    /**
     * 批量生成一写用户，保存到List集合中
     *
     * @param countPersonNum : 生成的用户总数
     * @return
     */
    public static List<Person> getListPerson(int countPersonNum) {
        List<Person> personList = new ArrayList<Person>();
        for (int i = 0; i < countPersonNum; i++) {
            String idno = getIdno();
            Person person = new Person();
            person.setIdno(idno);
            personList.add(person);
        }
        return personList;
    }
}
