package com.newbie.consistentHash;

/**
 * 机器节点对象
 */
public class PcNode {
    private String ip;  //物理ip
    private String name;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ip;
    }
}
