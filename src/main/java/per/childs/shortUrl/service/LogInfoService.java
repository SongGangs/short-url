package per.childs.shortUrl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import per.childs.shortUrl.entity.LogInfo;
import per.childs.shortUrl.mapper.LogInfoMapper;
import per.childs.shortUrl.util.RedisUtils;

import javax.annotation.Resource;

/**
 * @author : SGang
 * @Title:LogInfoService
 * @Description
 * @date : 2019/4/23
 */
@Service
public class LogInfoService {
    @Resource
    private LogInfoMapper logInfoMapper;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 统计使用数量
     *
     * @param code 短网址
     * @return
     */
    public Object getRequestCountByCode(String code) {
        String key = "short_url_request_count_" + code;
        if (redisUtils.hasKey(key)) {
            return redisUtils.get(key);
        } else {
            int count = logInfoMapper.getRequestCountByCode(code);
            redisUtils.set(key, count);
            return count;
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void insert(LogInfo logInfo) {
        String key = "short_url_request_count_" + logInfo.getCode();
        if (redisUtils.hasKey(key)) {
            redisUtils.set(key, Integer.valueOf(redisUtils.get(key).toString()) + 1);
        } else {
            redisUtils.set(key, 1);
        }
        logInfoMapper.insert(logInfo);
    }
}