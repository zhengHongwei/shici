package com.tc.util;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.tc.dto.pg.MedicineInCabinetReqDto;
import com.tc.dto.pg.sign.HeadReqDto;
import com.tc.dto.pg.sign.PackageReqDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.Set;

/**
 * RSA签名验签类
 */
public class RSASignatureWithKey {

    private static Logger logger = LoggerFactory.getLogger(RSASignatureWithKey.class);
    /**
     * 签名算法
     */
    public static final String SIGN_ALGORITHMS = "SHA256WithRSA";


    /**
     * RSA签名
     *
     * @param content    待签名数据
     * @return 签名值
     */
    public static  String sign(String content,String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes("utf-8"));
            byte[] signed = signature.sign();
            return Base64.encode(signed);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    /**
     * RSA验签名检查
     *
     * @param content   待签名数据
     * @param sign      签名值
     * @return 布尔值
     */
    public static  boolean doCheck(String content, String sign,String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content.getBytes("utf-8"));
            return signature.verify(Base64.decode(sign));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * 解析参数和签名
     * @param param
     * @param clz
     * @return
     */
    public static PackageReqDto parsingParam(String param,Class clz){
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(param);
        String data = jsonObject.getString("data");
        String jsonStrData = new String(java.util.Base64.getDecoder().decode(data));
        boolean flag = doCheck(data,jsonObject.getString("sign"),Setting.publicKey);
        if(flag){
            jsonObject  = com.alibaba.fastjson.JSONObject.parseObject(jsonStrData);
            PackageReqDto packageReqDto =jsonObject.toJavaObject(PackageReqDto.class);
            packageReqDto.setBody(jsonObject.getJSONObject("body").toJavaObject(clz));
            return packageReqDto;
        }else{
            return null;
        }
    }


    /**
     * 药品入柜模拟
     */
   public static void  medicineInCabinet(){
       HeadReqDto head = new HeadReqDto("渠道号","渠道名称","v1.0");
       MedicineInCabinetReqDto dto = new MedicineInCabinetReqDto("201811261002","YG1001",
               "10","北京市平谷区黄松峪乡社区");
       PackageReqDto packageReqDto = new PackageReqDto(head,dto);
       String str  = com.alibaba.fastjson.JSONObject.toJSONString(packageReqDto);
       String base64 = java.util.Base64.getEncoder().encodeToString(str.getBytes());
       System.out.println("base64编码后内容:\n"+base64);
       String sign = sign(base64,Setting.privateKey);
       System.out.println("签名后内容：\n" +sign);
       boolean flag = doCheck(base64,sign, Setting.publicKey);
       System.out.println("验签结果：\n"+flag);
   }
    public static  void main(String args[]){
       String param = "{\n" +
               "\t\"data\": \"eyJib2R5Ijp7ImNhYmluZXRBZGRyZXNzIjoi5LiK5rW36Ze16KGM5LiD5a6dIiwiY2FiaW5ldEdyaWRJZCI6IjE1NDQ0OTg4MzU5NzIiLCJjYWJpbmV0SWQiOiJzZWVzMDAzIiwicHJlc2NyaXB0aW9uQ29kZSI6IjIwMTgxMTI2MTAwNSJ9LCJoZWFkIjp7ImNoYW5uZWxDb2RlIjoid2pyIiwiY2hhbm5lbE5hbWUiOiLkuIflrrbnkZ7oja/mn5wiLCJ2ZXJzaW9uQ29kZSI6IlYxLjAifX0=\",\n" +
               "\t\"sign\": \"GojFcImBcz79Z4wRHK9I6DNuIL2LgD//JRZGlozuwStOeoYSkr1VWcho71gOVFqrh1pjdQERyCDLboeG+jtFXDrl1tsEVLHfizwO9dbnOA2fKNVWB7PiDxZS3734OkxB2ARZdCQl0sCKEInUy6xgSjULw/p2uijNBzlbgOs4MO7lenJmdGdgzdMsT/MqcipdHyjaEuUEKT7swcXTxgoVXz2P2z/5gmX54KR2ruZLJYaSaaGYsew53lSB3bnRYQaNReG5FVcesh05EjHVXLLj3c2+cysyFgx/3x2JXFZ/2SGWFQQaPoxVipW2FukApGNvh7tqsJP70yuYhDcLxScl1g==\"\n" +
               "}";
        parsingParam(param,Object.class);
       /*String str = "{\"body\":{\"cabinetAddress\":\"上海闵行七宝\",\"cabinetGridId\":\"1544498835972\",\"cabinetId\":\"sees003\",\"prescriptionCode\":\"201811261005\"},\"head\":{\"channelCode\":\"wjr\",\"channelName\":\"万家瑞药柜\",\"versionCode\":\"V1.0\"}}";
        String base64 = java.util.Base64.getEncoder().encodeToString(str.getBytes());
        System.out.println("base64编码后内容:\n"+ base64);
        String sign = sign(base64,Setting.privateKey);
        System.out.println("签名后内容：\n" +sign);
        boolean flag = doCheck(base64,sign, Setting.publicKey);
        System.out.println("验签结果：\n"+flag);
*/
    }

}

