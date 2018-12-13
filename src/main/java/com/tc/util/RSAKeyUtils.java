package com.tc.util;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RSAKeyUtils {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";
    public static final int KEY_SIZE = 2048;
    /**
     * 生成密钥对
     *
     * @return
     */
    public static Map<String, byte[]> generateKeyBytes() {

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator
                    .getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            Map<String, byte[]> keyMap = new HashMap<String, byte[]>();
            keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
            keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原公钥
     *
     * @param keyBytes
     * @return
     */
    public static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原私钥
     *
     * @param keyBytes
     * @return
     */
    public static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = factory
                    .generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String args[]) throws InterruptedException {
//        String s="[{\"a\":\"a1\",\"b\":20},{\"a\":\"a2\",\"b\":30}]";
//
//        JsonArray jsonArray = new JsonParser().parse(s).getAsJsonArray();
//
//        for(JsonElement jsonElement : jsonArray){
//            JsonObject jsonObject = (JsonObject) jsonElement;
//
//            String a = jsonObject.get("a").getAsString();
//            int b =jsonObject.get("b").getAsInt();
//        }
//
//        System.out.println(jsonArray);
        AtomicInteger count = new AtomicInteger();
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>(500);
        for (int i = 0; i < 200; i++) {
            workQueue.add(
                    new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("-------" + Thread.currentThread().getName() + "-----start" + System.currentTimeMillis());
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("-------" + Thread.currentThread().getName() + "-----end" + System.currentTimeMillis());
//                            System.out.println(count.incrementAndGet());
                        }
                    }
            );
        }

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(16, 16, 0, TimeUnit.SECONDS, workQueue);
        poolExecutor.prestartAllCoreThreads();

        System.out.println("===================main");
        poolExecutor.shutdown();
        System.out.println("-------------------over");

//        ExecutorService executors = Executors.newFixedThreadPool(16);
//
//        Random rand = new Random();
//        for(int i=0;i<200;i++){
//            executors.execute(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("--------------"+Thread.currentThread().getName()+"-start------------"+System.currentTimeMillis());
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("--------------"+Thread.currentThread().getName()+"-end------------"+System.currentTimeMillis());
////                    System.out.println(count.incrementAndGet());
//                }
//            });
//        }
//
//        System.out.println("=================main");
//        executors.shutdown();
//        System.out.println("=================over");



    }
}
