package per.childs.shortUrl.dto;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author : SGang
 * @Title:ResponseBo
 * @Description
 * @date : 2019/4/23
 */
public class ResponseBo extends HashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = -8713837118340960775L;


    public static ResponseBo ok(Object rs) {
        ResponseBo responseBo = new ResponseBo();
        responseBo.put("code", 200);
        responseBo.put("data", rs);
        return responseBo;
    }

    public static ResponseBo fail(String msg) {
        ResponseBo responseBo = new ResponseBo();
        responseBo.put("code", 500);
        responseBo.put("msg", msg);
        return responseBo;
    }
}