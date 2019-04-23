function generate() {
    var obj = {};
    var url = $.trim($("#url").val());
    obj.originUrl = url;
    if (url == null || url.match(/http[s]{0,1}?:\/\/[^\s]+/) == null) {
        alert("请输入正确格式的原始网址!");
        return;
    }

    var shortUrl = $.trim($("#shortUrl").val());
    obj.code = shortUrl;
    if (shortUrl.length > 0 && shortUrl.match(/[0-9a-zA-Z]{4,16}/) == null) {
        alert("请输入正确格式的指定短网址(4到16位数字及大小写字母)!");
        return;
    }

    if (shortUrl.length <= 0) {
        var length = $.trim($("#length").val());
        obj.targetLength = length;
        if (length.length <= 0 || length.match(/[0-9]{1,2}/g) == null) {
            alert("请输入正确格式的长度(4 ~ 16)!");
            return;
        }
        var charset = $("#charset").val();
        obj.charsetType = charset;
        if (charset.length == 0) {
            charset = 0;
        }
    }

    $.ajax({
        url: "/api/generate",
        type: "POST",
        dataType: "json",
        async: false,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(obj),
        success: function (data) {
            console.log(data);
            if (data.code == 200) {
                alert("短网址生成成功：" + data.data);
                $("#result").html(data.data);
                $("#result").attr("href", data.data);
            } else {
                alert(data.msg);
            }
        },
        beforeSend: function () {
            $("#result").html("");
            $("#result").attr("href", "javascript:;");
        },
        error: function (data) {
            alert("请求错误");
        }
    });

}

function queryReqCount() {
    var shortUrl = $.trim($("#queriedShortUrl").val());
    $.ajax({
        url: "/api/queryRequestCount?shortUrl=" + shortUrl,
        type: "GET",
        async: false,
        success: function (data) {
            console.log(data);
            if (data.code == 200) {
                $("#reqCount").html(data.data);
            } else {
                alert(data.msg);
            }
        },
        beforeSend: function () {
            $("#reqCount").html("");
        },
        error: function (data) {
            alert("请求错误");
        }
    });
}
