package per.childs.shortUrl.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import per.childs.shortUrl.entity.LogInfo;
import per.childs.shortUrl.service.LogInfoService;
import per.childs.shortUrl.service.ShortUrlService;
import per.childs.shortUrl.util.PatternUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;

/**
 * @author : SGang
 * @Title:ShortUrlController
 * @Description
 * @date : 2019/4/23
 */
@Controller
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @Autowired
    private LogInfoService logInfoService;

    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("/{code:[0-9a-zA-Z]{4,16}}")
    public ModelAndView redirect(@PathVariable(value = "code") String code, HttpServletRequest request) {
        String originUrl = shortUrlService.getOriginUrlByCode(code);
        ModelAndView modelAndView = new ModelAndView();
        if (StringUtils.isBlank(originUrl)) {
//            modelAndView.setViewName("redirect:");
            //跳转原网址
            RedirectView redirectView = new RedirectView();
            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
            redirectView.setUrl(request.getRequestURL().toString().replace(request.getRequestURI(), ""));
            modelAndView.setView(redirectView);
            return modelAndView;
        }
        //添加访问记录
        LogInfo log = new LogInfo();
        log.setCode(code);
        logInfoService.insert(log);
        //跳转原网址
        RedirectView redirectView = new RedirectView();
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        redirectView.setUrl(originUrl);
        modelAndView.setView(redirectView);
        return modelAndView;
    }
}