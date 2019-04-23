package per.childs.shortUrl.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author : SGang
 * @Title:ShortUrl
 * @Description
 * @date : 2019/4/22
 */
@Data
public class ShortUrl {

    private Integer id;

    private String code;

    private String originUrl;

    private Date createTime;
}