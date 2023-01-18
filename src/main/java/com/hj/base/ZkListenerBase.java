package com.hj.base;

import com.hj.annotation.ZkListener;

/**
 * @author: hj
 * @date: 2023/1/18
 * @time: 8:52 AM
 */
public class ZkListenerBase {


    @ZkListener(path = "/test")
    public void test() {
        System.out.println("test");
    }


}
