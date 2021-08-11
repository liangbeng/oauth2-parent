package com.ben.springbootcache.test;

import cn.hutool.core.util.RandomUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(RandomUtil.randomNumbers(6));
        }
    }
}
