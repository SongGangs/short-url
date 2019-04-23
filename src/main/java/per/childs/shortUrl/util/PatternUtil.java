package per.childs.shortUrl.util;

import java.util.regex.Pattern;

/**
 * @author : SGang
 * @Title:Pattern
 * @Description
 * @date : 2019/4/23
 */
public interface PatternUtil {

    Pattern CODE_PATTERN = Pattern.compile("[0-9a-zA-Z]{4,16}");

    Pattern URL_PATTERN = Pattern.compile("https?://[^\\s]+");

}
