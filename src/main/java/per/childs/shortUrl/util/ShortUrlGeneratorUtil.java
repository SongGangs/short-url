package per.childs.shortUrl.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

/**
 * @author : SGang
 * @Title:ShortUrlUtil
 * @Description
 * @date : 2019/4/22
 */
public class ShortUrlGeneratorUtil {

    @Value("${util.encrypt.key}")
    private static String key;

    public static String[] generator(String originUrl, int targetLen, int charsetType) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        // 要使用生成 URL 的字符
        char[] chars = CharsetEnum.findByCode(charsetType);
        long lastIndexOfCharset = chars.length - 1;
        // 对传入网址进行 MD5 加密
        String hex = EncryptUtil.md5(key + originUrl);
        // 位移步长
        int step = 32 / targetLen;

        String[] resUrl = new String[4];
        //得到 4组短链接字符串
        for (int i = 0; i < 4; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String tempSubString = hex.substring(i * 8, i * 8 + 8);
            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long hexLong = 0x3FFFFFFF & Long.parseLong(tempSubString, 16);
            char[] currentResult = new char[targetLen];
            //循环获得每组6位的字符串
            for (int j = 0; j < targetLen; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引(具体需要看chars数组的长度   以防下标溢出，注意起点为0)
//                long index = 0x0000003D & hexLong;
                long index = lastIndexOfCharset & hexLong;
                // 把取得的字符相加
                currentResult[j] = chars[(int) index];
                // 每次循环按位右移 5 位
                hexLong = hexLong >> step;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = new String(currentResult);
        }
        return resUrl;

    }

}