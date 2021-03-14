package com.devsjk.namecardserver.utils;

import java.util.Random;

public class TokenUtil {
    private static final String ALLCHAR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static final String NUMBER = "0123456789";


    /**
     * for admin
     * 生成唯一的随机token
     * @return
     */
    public static String makeToken(){
        return makeToken(0L,16);
    }


    /**
     * 生成唯一的随机token
     * @param uniqueId
     * @return
     */
    public static String makeToken(Long uniqueId){
        return makeToken(uniqueId,16);
    }

    /**
     * 生成唯一的随机token：
     *          1.唯一ida 转换成16进制 唯一idb
     *          2.唯一idb 随机插入随机码任意位置
     *          3.倒数第三位标志唯一idb 插入位置c
     *          4.倒数第二位随机生成
     *          5.最后一位l 为 唯一idb的 长度n 加上 插入位置c
     *          6.结果：xxxaxxxx3x4 ：唯一idb为10，插入位置是第3位，唯一idb长度为 4-3=1
     *          7.实际最后生成的长度为：随机码长度+唯一idb长度+2
     * @param length:随机码长度
     * @param uniqueId：唯一id
     * @return
     */
    public static String makeToken(Long uniqueId, Integer length) {
        if (length<5) length = 5;
        Random random = new Random();
        //把唯一id转换成16进制
        String hexString = Long.toHexString(uniqueId);
        //记录唯一id转换后的长度
        Integer idLen = String.valueOf(hexString).length();
        //保留一位随机码后面做分隔用
        length -= 1;
        StringBuilder RC = new StringBuilder();
        //生成length-1位的随机码
        for (int i = 0; i < length; i++) {
            RC.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        //生成随机插入位置
        Integer pos = random.nextInt(length-1);
        //把转换后的16进制唯一id按照生成的随机位置插入到随机码中
        String randomCode = RC.insert(pos,hexString).toString();
        //最后把插入了唯一id的随机码拼上 （随机插入位置标记对应的ALLCHAR+一位随机码分隔+随机插入位置标记与批次号长度和的对应的AllCHAR）
        randomCode = randomCode+ALLCHAR.charAt(pos)+ALLCHAR.charAt(random.nextInt(ALLCHAR.length()))+"-"+ALLCHAR.charAt(pos+idLen);

        return randomCode;
    }

    /**
     * 生成验证码
     */
    public static String makeCaptcha(){
        return makeCaptcha(6);
    }

    /**
     * 根据长度生成验证码
     * @param length
     * @return
     */
    public static String makeCaptcha(Integer length){
        if (length<4) length = 4;
        Random random = new Random();
        String randomCode = "";
        StringBuilder sb = new StringBuilder();
        //生成length-1位的随机码
        for (int i = 0; i < length; i++) {
            sb.append(NUMBER.charAt(random.nextInt(NUMBER.length())));
        }
        randomCode = sb.toString();
       return randomCode;
    }
}
