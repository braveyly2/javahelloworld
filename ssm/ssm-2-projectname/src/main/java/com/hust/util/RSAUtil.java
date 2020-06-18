package com.hust.util;

import com.hust.constant.GlobalConstant;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved ,Designed by TVT
 *
 * @author
 * @Description:
 * @date 2018/8/23 10:00
 */

public class RSAUtil {
    private static final String RSA_ALGORITHM = "RSA";
    private static final Provider provider = new BouncyCastleProvider();

    public static int hello(){
        return 1;
    }

    public static Map<String, String> createKeys(int keySize) throws Exception {
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM, provider);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        //初始化KeyPairGenerator对象,密钥长度
//        kpg.initialize(keySize, new SecureRandom());
        kpg.initialize(keySize);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64Util.encryptBase64(publicKey.getEncoded());
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64Util.encryptBase64(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);
        LogUtil.debug(String.format("生成RSA秘钥对，PublicKey: [ %s ], PrivateKey:[ %s ]", publicKeyStr, privateKeyStr), "RSA");

        return keyPairMap;
    }

    /**
     * 得到公钥
     *
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) {
        //通过X509编码的Key指令获得公钥对象
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64Util.decryptBase64(publicKey));
            RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
            return key;
        } catch (Exception e) {
            throw new RuntimeException(String.format("RSA获取PublicKey失败"), e);
        }
    }


    /**
     * 得到私钥
     *
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) {
        //通过PKCS#8编码的Key指令获得私钥对象
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64Util.decryptBase64(privateKey));
            RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
            return key;
        } catch (Exception e) {
            throw new RuntimeException(String.format("RSA获取PrivateKey失败"), e);
        }
    }

    /**
     * 公钥加密
     *
     * @param data      待加密数据
     * @param publicKey 公钥对象
     * @return
     */
    private static String publicEncrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", provider);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64Util.encryptBase64(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(GlobalConstant.DEFAULT_CHARSET), publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("RSA公钥加密时遇到异常", e);
        }
    }

    /**
     * 公钥加密
     *
     * @param data      待加密数据
     * @param publicKey 公钥字符串
     * @return
     */
    public static String publicEncrypt(String data, String publicKey) {
//        LogUtil.debug(String.format("-- RSA公钥加密 -- data: %s, publicKey: %s", data, publicKey), "RSA");
        return publicEncrypt(data, RSAUtil.getPublicKey(publicKey));
    }

    /**
     * 私钥解密
     *
     * @param data       待解密数据
     * @param privateKey 私钥对象
     * @return
     */
    private static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", provider);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64Util.decryptBase64(data), privateKey.getModulus().bitLength()), GlobalConstant.DEFAULT_CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("RSA私钥解密时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     *
     * @param data       待解密数据
     * @param privateKey 私钥字符串
     * @return
     */
    public static String privateDecrypt(String data, String privateKey) {
//        LogUtil.debug(String.format("-- RSA私钥解密 -- data: %s, privateKey: %s", data, privateKey), "RSA");
        return privateDecrypt(data, RSAUtil.getPrivateKey(privateKey));
    }

    /**
     * 私钥加密
     *
     * @param data       待加密数据
     * @param privateKey 私钥对象
     * @return
     */
    private static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", provider);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64Util.encryptBase64(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(GlobalConstant.DEFAULT_CHARSET), privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("RSA私钥加密时遇到异常", e);
        }
    }

    /**
     * 私钥加密
     *
     * @param data       待加密数据
     * @param privateKey 私钥字符串
     * @return
     */
    public static String privateEncrypt(String data, String privateKey) {
        LogUtil.debug(String.format("-- RSA私钥加密 -- data: %s, privateKey: %s", data, privateKey), "RSA");
        return privateEncrypt(data, RSAUtil.getPrivateKey(privateKey));
    }

    /**
     * 公钥解密
     *
     * @param data      待解密数据
     * @param publicKey 公钥对象
     * @return
     */
    private static String publicDecrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", provider);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64Util.decryptBase64(data), publicKey.getModulus().bitLength()), GlobalConstant.DEFAULT_CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("RSA公钥解密时遇到异常", e);
        }
    }

    /**
     * 公钥解密
     *
     * @param data      待解密数据
     * @param publicKey 公钥字符串
     * @return
     */
    public static String publicDecrypt(String data, String publicKey) {
//        LogUtil.debug(String.format("-- RSA公钥解密 -- data: %s, publicKey: %s", data, publicKey), "RSA");
        return publicDecrypt(data, RSAUtil.getPublicKey(publicKey));
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("rsaSplitCodec", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }

    /**
     * 从pem格式(-----BEGIN PUBLIC KEY-----)的key获取一行key
     *
     * @param pem
     * @return
     */
    public static String pemToKeyPublic(String pem) {
        if (pem == null) return "";
        if (pem.indexOf("KEY-----") > 0) {
            pem = pem.substring(pem.indexOf("KEY-----") + "KEY-----".length());
        }
        if (pem.indexOf("-----END") > 0) {
            pem = pem.substring(0, pem.indexOf("-----END"));
        }
//        pem = pem.replace("/", "_").replace("+", "-").replace("\n", "");
        pem = pem.replace("\n", "");
        return pem;
    }

    public static void main(String[] args) {
        String data = "hZh5SQEu6JqwiOT5yBtq1lURnKFSgokirgTR/4eSVDvrj1hc/g7GDFB+YUaNmjGfSQaavVwDuI/AzswgHwqIBrl+7lfvLY//6laq/3e89HsWAuB4KLM5rEnfXnKwtVi8evH1NPepI9JGZELgFCaCMQ313LsY6qyxMHV3fN1Kfvc=";
//        String data = "Ew5c0nCMvkHL/e7HG3SC6i1CtHXWlOAaXd4bvR9qXzuF4P+wN+/rRnrjWurKxegbHNUPh0px5i1rB2B/VZAsEgh3M2N7fRtBu7RcEiDtPGXPoWmVLioPpQ5iH6tFwOH9Y8cwToYZFtLGp4Z/YP3yC22MibKPQbCp0KXsmA2zktk=";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDkG+AElqCjTqFqF3d1ZePhuIUjCE0cUl9morPWuYciR9jLowc/rYhvf9Y38+YRVLOSUZV1v8vU+xrhB5XAdchF/UyMzT5uYn+f7jg3l+y1EfEB4Bdj8dYb8xtHc+SDgpQst+2wuvkdfvztMcGZ8lsTFGwF1mg2FieKKIT6VJYrvQIDAQAB";
//        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDkG+AElqCjTqFqF3d1ZePhuIUjCE0cUl9morPWuYciR9jLowc/rYhvf9Y38+YRVLOSUZV1v8vU+xrhB5XAdchF/UyMzT5uYn+f7jg3l+y1EfEB4Bdj8dYb8xtHc+SDgpQst+2wuvkdfvztMcGZ8lsTFGwF1mg2FieKKIT6VJYrvQIDAQAB";
        String r = RSAUtil.publicDecrypt(data, publicKey);
        System.out.println(r);
    }
}