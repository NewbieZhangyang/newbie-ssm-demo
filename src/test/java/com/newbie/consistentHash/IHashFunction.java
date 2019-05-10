package com.newbie.consistentHash;

public interface IHashFunction {
    Long hash(String key);
}
