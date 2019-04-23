package per.childs.shortUrl.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author : SGang
 * @Title:LogInfo
 * @Description
 * @date : 2019/4/22
 */
@Data
public class LogInfo {

    private Integer id;

    private Integer shortUrlId;

    private String code;

    private Date create_time;

}