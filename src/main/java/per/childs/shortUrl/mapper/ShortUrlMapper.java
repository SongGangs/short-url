package per.childs.shortUrl.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import per.childs.shortUrl.entity.ShortUrl;

/**
 * @author : SGang
 * @Title:ShortUrlMapper
 * @Description
 * @date : 2019/4/22
 */
@Mapper
public interface ShortUrlMapper {

    @Insert(" INSERT INTO tb_short_url_info (code, origin_url) VALUES (#{shortUrl.code},#{shortUrl.originUrl});")
    boolean insert(@Param("shortUrl") ShortUrl shortUrl);

    @Select("SELECT origin_url FROM `tb_short_url_info` WHERE `code`=#{code} limit 1;")
    String getOriginUrlByCode(@Param("code") String code);
}