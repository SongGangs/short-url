package per.childs.shortUrl.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import per.childs.shortUrl.dto.ResponseBo;
import per.childs.shortUrl.dto.ShortUrlRequestDTO;
import per.childs.shortUrl.entity.LogInfo;
import per.childs.shortUrl.expection.GenerateException;
import per.childs.shortUrl.service.LogInfoService;
import per.childs.shortUrl.service.ShortUrlService;
import per.childs.shortUrl.util.PatternUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;

/**
 * @author : SGang
 * @Title:ShortUrlRestController
 * @Description
 * @date : 2019/4/22
 */
@RestController
@RequestMapping("api")
public class ShortUrlRestController {

    @Autowired
    private ShortUrlService shortUrlService;
    @Autowired
    private LogInfoService logInfoService;

    @PostMapping("generate")
    public ResponseBo generate(@RequestBody ShortUrlRequestDTO dto, HttpServletRequest request) {
        String short_code = "";
        // 参数检查及适配
        try {
            ShortUrlRequestDTO.adapt(dto);
            if (!StringUtils.isBlank(dto.getCode())) {
                if (!shortUrlService.generateShortUrl(dto.getCode(), dto.getOriginUrl())) {
                    throw new GenerateException("生成短网址失败");
                } else {
                    short_code = dto.getCode();
                }
            } else {
                short_code = shortUrlService.generateShortUrl(dto.getOriginUrl(), dto.getTargetLength(), dto.getCharsetType());
            }
        } catch (IllegalArgumentException e) {
            return ResponseBo.fail(e.getMessage());
        } catch (GenerateException e) {
            return ResponseBo.fail(e.getMessage());
        }
        return ResponseBo.ok(request.getHeader("Referer") + short_code);
    }


    @GetMapping("/queryRequestCount")
    public ResponseBo queryRequestCount(String shortUrl) {
        try {
            if (StringUtils.isBlank(shortUrl)) {
                throw new IllegalArgumentException("短网址不能为空");
            }
            Matcher urlMatcher = PatternUtil.URL_PATTERN.matcher(shortUrl);
            if (!urlMatcher.matches()) {
                throw new IllegalArgumentException("短网址不规范");
            }
        } catch (IllegalArgumentException e) {
            return ResponseBo.fail(e.getMessage());
        }

        int protocolEnd = shortUrl.indexOf("://");
        String code = shortUrl;
        if (protocolEnd != -1) {
            code = code.substring(protocolEnd + 3);
            int hostEnd = code.indexOf("/");
            if (hostEnd == -1) {
                return ResponseBo.fail("短网址不规范");
            }
            code = code.substring(hostEnd + 1);
        }

        return ResponseBo.ok(logInfoService.getRequestCountByCode(code));
    }


}