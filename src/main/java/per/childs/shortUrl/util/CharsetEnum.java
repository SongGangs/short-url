package per.childs.shortUrl.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : SGang
 * @Title:CharsetEnum
 * @Description
 * @date : 2019/4/22
 */
@AllArgsConstructor
public enum CharsetEnum {
    DEFAULT(0, Charset.DEFAULT_CHARSET),
    LOWER_WITH_NUMBERS(1, Charset.LOWER_WITH_NUMBER),
    UPPER_WITH_NUMBERS(2, Charset.UPPER_WITH_NUMBER),
    LOWER_WITHOUT_NUMBERS(3, Charset.LOWER_ONLY),
    UPPER_WITHOUT_NUMBERS(4, Charset.UPPER_ONLY);

    @Getter
    private int code;

    @Getter
    private char[] charset;

    public static char[] findByCode(int code) {
        for (CharsetEnum charSetEnum : CharsetEnum.values()) {
            if (charSetEnum.getCode() == code) {
                return charSetEnum.getCharset();
            }
        }
        return DEFAULT.getCharset();
    }
}