package per.childs.shortUrl.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import per.childs.shortUrl.expection.GenerateException;
import per.childs.shortUrl.util.PatternUtil;

import java.util.regex.Matcher;

/**
 * @author : SGang
 * @Title:ShortUrlRequestDTO
 * @Description
 * @date : 2019/4/23
 */
@Data
public class ShortUrlRequestDTO {

    private String originUrl;

    /**
     * 指定短网址
     */
    private String code;

    /**
     * 短网址长度
     */
    private Integer targetLength = 4;

    /**
     * 字符集
     */
    private Integer charsetType = 0;

    public static void adapt(ShortUrlRequestDTO dto) {
        if (StringUtils.isBlank(dto.getOriginUrl())) {
            throw new IllegalArgumentException("需要转换的原网址不能为空");
        }
        Matcher urlMatcher = PatternUtil.URL_PATTERN.matcher(dto.getOriginUrl());
        if (!urlMatcher.matches()) {
            throw new IllegalArgumentException("原网址不规范");
        }
        if (dto.getTargetLength() > 16 || dto.getTargetLength() < 4) {
            throw new IllegalArgumentException("目标长度只能在4-16位");
        }

        if (!StringUtils.isBlank(dto.getCode())) {
            Matcher matcher = PatternUtil.CODE_PATTERN.matcher(dto.getCode());
            if (!matcher.matches()) {
                throw new IllegalArgumentException("短网址只能为字符串+数字");
            }
        }
    }

}