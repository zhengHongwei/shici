package com.pamc.api.datax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author zhenghongwei943
 * @date 2018/11/21
 * @description：
 **/
public class DataXTest {

    public static void main(String[] args) throws Exception {
        Process process = Runtime.getRuntime().exec("git branch -a");
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(),
                "utf-8"));
        String line = null;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        in.close();
    }
}
