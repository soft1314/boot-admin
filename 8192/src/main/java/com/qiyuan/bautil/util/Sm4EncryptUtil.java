package com.qiyuan.bautil.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import static cn.hutool.crypto.Mode.CBC;
import static cn.hutool.crypto.Padding.ZeroPadding;

public class Sm4EncryptUtil {

    public static String encrypt(String plainTxt,String padding,String key){
        String cipherTxt = "";
        SymmetricCrypto sm4 = new SM4(CBC, ZeroPadding, padding.getBytes(CharsetUtil.CHARSET_UTF_8), key.getBytes(CharsetUtil.CHARSET_UTF_8));
        byte[] encrypHex = sm4.encrypt(plainTxt);
        cipherTxt = Base64.encode(encrypHex);
        return "{SM4}" + cipherTxt;
    }

    public static String decrypt(String cipherTxt,String padding,String key){
        if(!cipherTxt.startsWith("{SM4}")){
            return cipherTxt;
        }
        cipherTxt = cipherTxt.substring(5);
        String plainTxt = "";
        SymmetricCrypto sm4 = new SM4(CBC, ZeroPadding, padding.getBytes(CharsetUtil.CHARSET_UTF_8), key.getBytes(CharsetUtil.CHARSET_UTF_8));
        byte[] cipherHex = Base64.decode(cipherTxt);
        plainTxt = sm4.decryptStr(cipherHex, CharsetUtil.CHARSET_UTF_8);
        return plainTxt;
    }

    public static void main(String[] argc){
        String originTxt = "这是一次注定载入中华民族伟大志为核心的党中央周围，必将在新时代新征程上赢得更加伟大的胜利和荣光。";
        System.out.println("原文: " + originTxt);
        String cipherTxt = encrypt(originTxt,"abc1111111111333","iviviviviviviviv");
        System.out.println("密文: " + cipherTxt);
        String plainTxt = decrypt(cipherTxt,"abc1111111111333","iviviviviviviviv");
        System.out.println("解密结果: " + plainTxt);

        cipherTxt = SmUtil.sm4().encryptBase64(originTxt);
        System.out.println("密文: " + cipherTxt);
        plainTxt = SmUtil.sm4().decryptStr(cipherTxt);
        System.out.println("解密结果: " + plainTxt);

    }
}
