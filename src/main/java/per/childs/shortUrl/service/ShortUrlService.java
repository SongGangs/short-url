package per.childs.shortUrl.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import per.childs.shortUrl.entity.ShortUrl;
import per.childs.shortUrl.expection.GenerateException;
import per.childs.shortUrl.mapper.ShortUrlMapper;
import per.childs.shortUrl.util.RedisUtils;
import per.childs.shortUrl.util.ShortUrlGeneratorUtil;

import javax.annotation.Resource;

/**
 * @author : SGang
 * @Title:ShortUrlService
 * @Description
 * @date : 2019/4/22
 */
@Service
public class ShortUrlService {
    @Resource
    private ShortUrlMapper shortUrlMapper;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 生成 短网址
     *
     * @param code      短网址 标识
     * @param originUrl 原URL
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean generateShortUrl(String code, String originUrl) {
        boolean result = true;
        String URL = shortUrlMapper.getOriginUrlByCode(code);
        if (StringUtils.isBlank(URL)) {
            ShortUrl shortUrl = new ShortUrl();
            shortUrl.setOriginUrl(originUrl);
            shortUrl.setCode(code);
            result = shortUrlMapper.insert(shortUrl);
        }
        if (result) {
            redisUtils.set("short_url_code_" + code, originUrl);
        }
        return result;
    }

    /**
     * 生成 短网址
     *
     * @param originUrl    原URL
     * @param targetLength 目标短网址长度
     * @param charsetType  类型
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public String generateShortUrl(String originUrl, int targetLength, int charsetType) {
        String[] candidates = ShortUrlGeneratorUtil.generator(originUrl, targetLength, charsetType);
        for (String candidate : candidates) {
            if (generateShortUrl(candidate, originUrl)) {
                return candidate;
            }
        }
        throw new GenerateException("生成短网址失败。");
    }

    /**
     * 根据短网址查找原网址
     *
     * @param code 短网址
     * @return
     */
    public String getOriginUrlByCode(String code) {
        String key = "short_url_code_" + code;
        if (redisUtils.hasKey(key)) {
            return redisUtils.get(key).toString();
        } else {
            return shortUrlMapper.getOriginUrlByCode(code);
        }
    }

}