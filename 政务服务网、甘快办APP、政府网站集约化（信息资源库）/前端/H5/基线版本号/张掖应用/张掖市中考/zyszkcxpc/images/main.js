const jmasUrl = "https://zwfw.gansu.gov.cn/jpaas-jags-server/interface";
const basrUrl = "https://zwfw.gansu.gov.cn/szgs/member/wwLogin";
function fwyy (objName) {
    this.el = null;
    this.code = null;
    this.data = null;
    this.sign = '';
    this.inputdata = null;
    this.isLogin = null;
    this.userId = null;
    this.times = 1;
    // 查询开始时间
    this.deadlineTime = "2022-06-26 00:00:00";
    this.isdj = false;
}
fwyy.prototype.init = function (el) {
    this.el = el;
    this.createCode(this.el.code);
    this.ajaxLoginState();
    this.getDeadline();
    $(this.el.code).click({ fwyy: this }, function (e) {
        e.data.fwyy.createCode(e.data.fwyy.el.code);
    });
    $(this.el.resetCode).click({ fwyy: this }, function (e) {
        e.data.fwyy.createCode(e.data.fwyy.el.code);
    });
    $(this.el.InquireBtn).click({ fwyy: this }, function (e) {
        if (fwyy.isdj) {
            e.data.fwyy.showData();
        } else {
            var jzyear = new Date(fwyy.deadlineTime).getFullYear();
            var jzmonth = new Date(fwyy.deadlineTime).getMonth() + 1;
            var jzday = new Date(fwyy.deadlineTime).getDate();
            var jzhours = new Date(fwyy.deadlineTime).getHours();
            var jzmin = new Date(fwyy.deadlineTime).getMinutes();
            layer.open({
                title: '提示',
                content: '暂未到查询时间，请于' + jzyear + '年' + jzmonth + '月' + jzday + '日' + jzhours + '点' + jzmin + '分整后开始查询！',
                btn: ['确定'],
                yes: function (index, layero) {
                    layer.close(index);
                },
                cancel: function () {

                }
            });
        }
    });
    $(this.el.returnBtn).click({ fwyy: this }, function (e) {
        e.data.fwyy.showPage(0, '');
    });
}
fwyy.prototype.getInputval = function () {
    this.inputdata = {};
    var input = this.el.input;
    console.log(input,"input");
    for (var i = 0; i < input.length; i++) {
        this.inputdata[input[i]] = $(input[i]).val();
        console.log(input[i]);
    }
}
fwyy.prototype.setAjaxDate = function () {
    // var type = "1";
    var q = this.inputdata['#v1'];
    var b = this.inputdata['#v2'];
    var c = this.inputdata['#v3'];

    // console.log(q, b)
    // var left = '';
    // var right = '';
    // var zkzh = "{\"type\":\"" + type + "\",\"content\":\"" + q + "\",\"left\":\"" + left + "\",\"right\":\"" + right + "\"}";
    // var ksxm = "{\"type\":\"" + type + "\",\"content\":\"" + b + "\",\"left\":\"" + left + "\",\"right\":\"" + right + "\"}";
    // var zkzh_encode = zkzh;
    // var ksxm_encode = ksxm;

    // var columnId = '36fedc9666724d8ba685d17e2709e729', 
    // var pageSize = 1;
    // var pageNum = 1;
    // var g_app_id = "cmsnzjk";
    // var g_interface_id = "listInfoEnnzjk";
    var timestamp = new Date().valueOf();
    var header = "{}";

    let params = {
        columnId: sm2Encrypt('8365dc2c81104c3ebc80a5806bc1ab7e', publicKey, 1),
        pageNum: sm2Encrypt(1, publicKey, 1),
        pageSize: sm2Encrypt(1, publicKey, 1),
        map: sm2Encrypt(
            JSON.stringify({
                xm: JSON.stringify({
                    type: 1,
                    content: b,
                    left: '',
                    right: ''
                }),
                xh: JSON.stringify({
                    type: 1,
                    content: q,
                    left: '',
                    right: ''
                }),
                sfzh: JSON.stringify({
                    type: 1,
                    content: c,
                    left: '',
                    right: ''
                })
            }),
            publicKey,
            1
        )
    }
    // biz_content = parseParams(biz_content);
    this.data = {
        'version': "1.0",
        'charset': "utf-8",
        'origin': 0,
        'timestamp': timestamp,
        'app_id': "cmsnzjk",
        'interface_id': "listInfoEnnzjk",
        'header': header,
        'biz_content': JSON.stringify(params)
    };
    // this.data.biz_content = '{"param":"' + (biz_content) + '"}';
}
fwyy.prototype.showData = function () {
    this.getInputval();
    var inputCode = $(this.el.inputVCode).val().toUpperCase();
    var valicode = this.valiCode(inputCode);
    var inputVali = this.valiVal();
    if (this.isLogin) {
        if (valicode && inputVali) {
            fwyy.getSearchTimes();
        }
    } else {
        layer.open({
            title: '提示',
            content: '请登录后进行查询！',
            btn: ['确定'],
            yes: function (index, layero) {
                layer.close(index);
            },
            cancel: function () {

            }
        });
    }
}
fwyy.prototype.getSign = function (data) {
    var getSign = null;
    $.ajax({
        type: "post",
        async: false,
        data: data,
        dataType: "json",
        url: jmasUrl + "/createsign.do",
        success: function (result) {
            var success = result.success;
            if (success == true) {
                var sign = result.data.sign;
                getSign = sign;
            }
        }
    });
    return getSign;
}
// 获取是否到达查询开始时间
fwyy.prototype.getDeadline = function () {
    var timeIn = setInterval(function () {
        var cxtime = new Date(fwyy.deadlineTime).valueOf();
        var currenttime = new Date().valueOf();
        if (currenttime >= cxtime) {
            clearInterval(timeIn);
            timeIn = null;
            fwyy.isdj = true;
        }
    }, 1000);
}
fwyy.prototype.loadData = function () {
    this.setAjaxDate();
    this.data.sign = this.getSign(this.data);
    // this.data.biz_content = JSON.parse(this.data.biz_content);
    console.log(this.data, 111);
    $.ajax({
        type: 'post',
        dataType: 'json',
        // contentType: 'application/json',
        data: this.data,
        url: jmasUrl + '/gateway.do',
        success: function (result) {
            fwyy.resultDealWith(result);
        }
    })
}
fwyy.prototype.getSearchTimes = function () {
    // 白银市行政区划编码
    var websiteId = "620400000000";
    var biz_content = {
        "key": this.userId + "_" + websiteId,
    }
    var params = {
        'version': "1.0",
        'charset': "utf-8",
        'origin': 1,
        'timestamp': new Date().valueOf(),
        'app_id': "zkcx",
        'interface_id': "zkcxhcxcs",
        'header': "{}",
        'biz_content': JSON.stringify(biz_content),
    }
    params.sign = this.getSign(params);
    $.ajax({
        type: 'post',
        dataType: 'json',
        data: params,
        async: false,
        url: jmasUrl + '/gateway.do',
        success: function (result) {
            if (result.code == "200") {
                var searchTimes;
                if (JSON.parse(result.data).data == "") {
                    searchTimes = 1;
                } else {
                    searchTimes = parseInt(JSON.parse(result.data).data);
                }
                if (searchTimes <= 5) {
                    fwyy.loadData();
                } else {
                    layer.open({
                        title: '提示',
                        content: '今日查询次数已达到最大，请明日再来！',
                        btn: ['确定'],
                        yes: function (index, layero) {
                            layer.close(index);
                        },
                        cancel: function () {

                        }
                    });
                }
            } else {
                layer.open({
                    title: '提示',
                    content: result.msg,
                    yes: function (index, layero) {
                        layer.close(index);
                    },
                    cancel: function () {

                    }
                });
            }
        }
    })
}
fwyy.prototype.addSearchTimes = function (value) {
    // 白银市行政区划编码
    var websiteId = "620400000000";
    var biz_content = {
        "key": this.userId + "_" + websiteId,
        "value": value
    }
    var params = {
        'version': "1.0",
        'charset': "utf-8",
        'origin': 1,
        'timestamp': new Date().valueOf(),
        'app_id': "zkcx",
        'interface_id': "zkcxxrcxcs",
        'header': "{}",
        'biz_content': JSON.stringify(biz_content),
    }
    params.sign = this.getSign(params);
    $.ajax({
        type: 'post',
        dataType: 'json',
        data: params,
        async: false,
        url: jmasUrl + '/gateway.do',
        success: function (result) {
            console.log("查询次数增加了！");
        }
    });
}
// 埋点
fwyy.prototype.browseBuried = function () {
    var date = new Date();
    var y = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hours = date.getHours();
    var minute = date.getMinutes();
    minute >= 30 ? minute = 30 : minute = "00";
    var time = y + "/" + month + "/" + day + " " + hours + ":" + minute;
    var biz_content = {
        "type": "pc-zys",
        "appName": "zyszkcjcx",
        "time": time
    }
    var params = {
        'version': "1.0",
        'charset': "utf-8",
        'origin': 1,
        'timestamp': new Date().valueOf(),
        'app_id': "sjmd",
        'interface_id': "sjmdjk",
        'header': "{}",
        'biz_content': JSON.stringify(biz_content),
    }
    params.sign = this.getSign(params);
    $.ajax({
        type: 'post',
        dataType: 'json',
        data: params,
        async: false,
        url: jmasUrl + '/gateway.do',
        success: function (result) {
            console.log("埋点");
        }
    });
}
fwyy.prototype.ajaxLoginState = function () {
    $.ajax({
        url: basrUrl + "/getCurrentUserInfoNew1.do",
        type: "get",
        data: {
            num: Math.floor(Math.random() * 10000)
        },
        success: function (result) {
            console.log("result", result);
            fwyy.getLoginState(result);
        }
    });
}
fwyy.prototype.getLoginState = function (res) {
    if (res.code == "200") {
        this.isLogin = true;
        this.userId = res.params.userId;
    } else {
        this.isLogin = false;
    }
}

fwyy.prototype.resultDealWith = function (res) {
    if (res.code == 200) {
        console.log("res",res);
        let a = JSON.parse(res.data).data.dataResult.slice(2)
        result = JSON.parse(sm2Decrypt(a, privateKey, 1))
        var List = result;
        result.dataList.forEach(item => {
            function encodeName (str) {
                if (null != str && str != undefined) {
                    if (str.length <= 2) {
                        return "*" + str.substr(1, str.length);
                    } else if (str.length <= 3) {
                        return "**" + str.substr(2, str.length);
                    } else if (str.length > 3 && str.length <= 6) {
                        return "**" + str.substr(2, str.length);
                    } else if (str.length > 6) {
                        return str.substr(0, 2) + "****" + str.substr(6, str.length);
                    }
                } else {
                    return "";
                }
            }
            console.log(item.xm)
            item.xm = encodeName(item.xm)
            item.sfzh = item.sfzh.replace(/^(.).*(.)$/, '$1********$2')
        })
        if (List.totalCount > 0) {
            let c = List.dataList[0];
            console.log(c);
            var html = this.createHtml(c);

            this.showPage(1, html);
            fwyy.times++;
            fwyy.addSearchTimes(fwyy.times);
        } else {
            layer.open({
                title: '查询无结果',
                content: '请检查姓名或准考证号是否输入正确！',
                btn: ['确定'],
                yes: function (index, layero) {
                    layer.close(index);
                    $("#v2").focus();
                    fwyy.createCode(fwyy.el.code);
                },
                cancel: function () {
                    $("#v2").focus();
                    fwyy.createCode(fwyy.el.code);
                }
            });
            // this.showPage(1, '<!-- 查询无结果 --><div class="noneImg" style="text-align: center;"><img src="images/doesNotExist.png" alt=""><p>暂无数据</p></div>');
        }
    } else {
        this.showPage(1, '<!-- 查询无结果 --><div class="noneImg" style="text-align: center;"><img src="images/doesNotExist.png" alt=""><p>查询异常</p></div>');
    }
}

fwyy.prototype.showPage = function (n, html) {
    if (n == 0) {
        this.createCode(this.el.code);
        $(this.el.cxbox).css('display', 'block');
        $(this.el.cxjg).css('display', 'none');
        $(this.el.returnBtn).css('display', 'none');
        $(this.el.inputVCode).val('');

    } else if (n == 1) {
        $(this.el.cxjg).css('display', 'block').html(html);
        $(this.el.returnBtn).css('display', 'block');
        $(this.el.cxbox).css('display', 'none');
    }
}
fwyy.prototype.createHtml = function (c) {
    var html = "";
    html = '<table><tbody>';
    html += '<tr><td width="20%" class="colLeft">准考证号</td><td id="txt1" width="30%">' + c.xh + '</td><td width="20%" class="colLeft">姓名</td><td id="txt1">' + c.xm + '</td></tr>';
    html += '<tr><td width="20%" class="colLeft">身份证号</td><td id="txt1" colspan="3">' + c.sfzh + '</td></tr>';
    html += '<tr><td width="20%" class="colLeft">语文</td><td id="txt1" width="30%">' + c.yw + '</td><td width="20%" class="colLeft">数学</td><td id="txt1">' + c.sx + '</td></tr>';
    html += '<tr><td width="20%" class="colLeft">英语</td><td id="txt1" width="30%">' + c.yy + '</td><td width="20%" class="colLeft">化学</td><td id="txt1">' + c.hx + '</td></tr>';
    html += '<tr><td width="20%" class="colLeft">道德与法治</td><td id="txt1" width="30%" rowspan="2">' + c.ddfz_ls + '</td><td width="20%" class="colLeft">生物学</td><td id="txt1" rowspan="2">' + c.sw_dl + '</td></tr>';
    html += '<tr><td width="20%" class="colLeft">历史</td><td width="20%" class="colLeft">地理</td></tr>';
     html += '<tr><td width="20%" class="colLeft">综合素质评价</td><td id="txt1" width="30%">' + c.zhpj + '</td><td width="20%" class="colLeft">照顾加分</td><td id="txt1">' + c.jf + '</td></tr>';
    html += '<tr><td width="20%" class="colLeft">总成绩</td><td id="txt1" colspan="3">' + c.zf + '</td></tr>';
    html += '</tbody></table>';
    html = html.replace(/(undefined)|(null)|(NULL)/g, '暂无数据');
    return html;
}
fwyy.prototype.setTips = function (el, msg, ys, empty) {
    msg = msg.length > 0 ? msg : $(el).attr('placeholder');
    $(el).addClass(ys).attr('placeholder', msg).focus();
    $(el).bind('input propertychange', function () {
        $(this).removeClass('msg-warn');
    })
    if (empty == 1) {
        $(el).val('');
    }
}
fwyy.prototype.createCode = function (el_code) {
    var code = "";
    var codeLength = 5;
    var random = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
        'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
    for (var i = 0; i < codeLength; i++) {
        var charIndex = Math.floor(Math.random() * 36);
        code += random[charIndex];
    }
    this.code = code;
    $(el_code).val(code);
}
fwyy.prototype.valiCode = function (inputCode) {
    var flag = true;
    if (inputCode.length <= 0) {
        flag = false;
        this.setTips(this.el.inputVCode, '请输入验证码', 'msg-warn', 1);
    } else if (inputCode == this.code) {
        flag = true;
    } else {
        flag = false;
        this.createCode(this.el.code);
        this.setTips(this.el.inputVCode, '验证码输入错误', 'msg-warn', 1);
    }
    return flag;

}
fwyy.prototype.valiVal = function () {
    var regNum = /^[0-9]*$/;
    var regSfz =  /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    var valiNum = 0;
    for (var k in this.inputdata) {
        if (this.inputdata[k].length <= 0) {
            this.setTips(k, '', 'msg-warn', 0);
            valiNum++;
        } else {
            if (!regNum.test(this.inputdata[k]) && k === "#v1") {
                layer.open({
                    title: '提示',
                    content: '准考证号只能为数字！',
                    btn: ['确定'],
                    yes: function (index, layero) {
                        layer.close(index);
                        $("#v1").focus();
                    },
                    cancel: function () {
                        $("#v1").focus();
                    }
                });
                valiNum++;
            }
            if (!regSfz.test(this.inputdata[k]) && k === "#v3") {
                layer.open({
                    title: '提示',
                    content: '身份证号码格式不正确！',
                    btn: ['确定'],
                    yes: function (index, layero) {
                        layer.close(index);
                        $("#v3").focus();
                    },
                    cancel: function () {
                        $("#v3").focus();
                    }
                });
                valiNum++;
            }
        }
    }
    if (valiNum === 0) {
        return true;
    } else {
        return false;
    }

}

function isValType (v) {
    return v == undefined || v == null ? '' : v;
}

//加密
function encrypt (word, keyStr) {
    keyStr = keyStr ? keyStr : 'c92832183b2c4c9394487e25f989125c'; //判断是否存在ksy，不存在就用定义好的key
    var key = CryptoJS.enc.Utf8.parse(keyStr);
    var srcs = CryptoJS.enc.Utf8.parse(word);
    var encrypted = CryptoJS.AES.encrypt(srcs, key, { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 });
    return encrypted.toString();
}
//解密
function decrypt (word, keyStr) {
    keyStr = keyStr ? keyStr : 'c92832183b2c4c9394487e25f989125c';
    var key = CryptoJS.enc.Utf8.parse(keyStr);
    var decrypt = CryptoJS.AES.decrypt(word, key, { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 });
    return CryptoJS.enc.Utf8.stringify(decrypt).toString();
}
// // JSON转url参数
// function parseParams (data) {
//     try {
//         var tempArr = [];
//         for (var i in data) {
//             var key = encodeURIComponent(i);
//             var value = encodeURIComponent(data[i]);
//             tempArr.push(key + '=' + value);
//         }
//         var urlParamsStr = tempArr.join('&');
//         return urlParamsStr;
//     } catch (err) {
//         return '';
//     }
// }