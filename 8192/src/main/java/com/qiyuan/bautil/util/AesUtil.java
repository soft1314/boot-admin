package com.qiyuan.bautil.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AesUtil {

    private final static String ALGORITHM = "AES";
    private final static String ENCODE = "UTF-8";
    public final static String KEY = "weeon@2019gggggg";

    private final static int TWO = 2;

    /**
     * 加密
     * @param data
     * @param key
     * @return
     */
    public static String encrypt(String data, String key) {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(key.getBytes());
            keygen.init(128,secureRandom);

            //3.产生原始对称密钥
            SecretKey originalKey = keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] raw = originalKey.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey deskey = new SecretKeySpec(raw, ALGORITHM);
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byteEncode = data.getBytes(ENCODE);
            //9.根据密码器的初始化方式--加密：将数据加密
            byte[] cipherText = cipher.doFinal(byteEncode);
            StringBuffer sb = new StringBuffer();
            for (int n = 0; n < cipherText.length; n++) {
                String stmp = (Integer.toHexString(cipherText[n] & 0XFF));
                if (stmp.length() == 1) {
                    sb.append("0" + stmp);
                } else {
                    sb.append(stmp);
                }
            }
            return sb.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //如果有错就返加nulll
        return null;
    }


    /**
     * 解密
     * @param data
     * @param key
     * @return
     */
    public static String decrypt(String data, String key) {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(key.getBytes());
            keygen.init(128,secureRandom);

            //3.产生原始对称密钥
            SecretKey originalKey = keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] raw = originalKey.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey enckey = new SecretKeySpec(raw, ALGORITHM);
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, enckey);
            byte[] btxts = new byte[data.length() / 2];
            int count = data.length();
            for (int i = 0; i < count; i += TWO) {
                btxts[i / 2] = (byte) Integer.parseInt(data.substring(i, i + 2), 16);
            }
            return (new String(cipher.doFinal(btxts), ENCODE));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        //如果有错就返加nulll
        return null;
    }

    public static void main(String[] args) {
        String key = "weeon@2019ggg";
        String str = "D:\\Workspace-weeon\\weeon-pdf-files\\.idea\\compiler.xml";
        String afterStr = encrypt(str,key);
        System.out.println(afterStr);
        System.out.println(decrypt(afterStr,key));

    }

}
