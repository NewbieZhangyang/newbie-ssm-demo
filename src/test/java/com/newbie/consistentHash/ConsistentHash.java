package com.newbie.consistentHash;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 实现一致性hash算法，
 * @param <T>
 */
public class ConsistentHash<T> {

    private final IHashFunction hashFunction;// hash 函数接口
    private final int numberOfReplicas;// 每个机器节点关联的虚拟节点个数
    private final SortedMap<Long, T> circle = new TreeMap<Long, T>();// 环形虚拟节点

    /**
     *
     * @param hashFunction
     *            hash 函数接口
     * @param numberOfReplicas
     *            每个机器节点关联的虚拟节点个数
     * @param nodes
     *            真实机器节点
     */
    public ConsistentHash(IHashFunction hashFunction, int numberOfReplicas, Collection<T> nodes) {
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;
        //遍历真实节点，生成对应的虚拟节点
        for (T node : nodes) {
            add(node);
        }
    }

    /**
     * 增加真实机器节点
     *
     * @param node
     */
    public void add(T node) {
        for (int i = 0; i < this.numberOfReplicas; i++) {
            circle.put(this.hashFunction.hash(node.toString() + i), node);
        }
    }

    /**
     * 删除真实机器节点
     *
     * @param node
     */
    public void remove(T node) {
        for (int i = 0; i < this.numberOfReplicas; i++) {
            circle.remove(this.hashFunction.hash(node.toString() + i));
        }
    }

    /**
     * 取得真实机器节点
     *
     * @param key
     * @return
     */
    public T get(String key) {
        if (circle.isEmpty()) {
            return null;
        }

        long hash = hashFunction.hash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Long, T> tailMap = circle.tailMap(hash);// 沿环的顺时针找到一个虚拟节点
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }

        return circle.get(hash); // 返回该虚拟节点对应的真实机器节点的信息
    }
}