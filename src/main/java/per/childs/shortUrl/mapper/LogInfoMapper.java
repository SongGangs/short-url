package per.childs.shortUrl.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import per.childs.shortUrl.entity.LogInfo;

/**
 * @author : SGang
 * @Title:LogInfoMapper
 * @Description
 * @date : 2019/4/23
 */
@Mapper
public interface LogInfoMapper {

    @Insert("INSERT INTO tb_log (code) VALUES (#{logInfo.code});")
    void insert(@Param("logInfo") LogInfo logInfo);

    @Select("SELECT count(code) FROM `tb_log` WHERE `code`=#{code};")
    int getRequestCountByCode(@Param("code") String code);

}