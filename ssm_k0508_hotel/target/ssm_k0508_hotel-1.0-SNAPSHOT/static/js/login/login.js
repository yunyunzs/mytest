layui.use(['jquery','layer','form'], function() {
    var $ = layui.jquery    //引入jquery模块
        , layer = layui.layer  //引用layer弹出层模块
        , form = layui.form  //引用form表单模块

    //layer.msg("测试。。");

    var verifyCodeIf= false;//验证码验证的判断

    //判断是否被拦截转发到的登陆页面
    if($("#loginUIMsg").val()=="loginUIMsg"){  //是被拦截的
        layer.msg("对不起，请先登陆！！",{icon: 7,time:2000,anim: 6,shade:0.5});
    }

    //登录的表单提交监听
    form.on('submit(login)', function(data){
        console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
        //向服务器发送指令，验证用户名和密码
        checkLogin(data.field);
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    //验证码输入框失去焦点时验证
    $("#yzm").blur(function () {
        var yzm = $(this).val();  //获取用户输入的验证码
        if(yzm.length==5){
            //进行服务器端的验证码验证
            verifyCheck(yzm);
        }else {
            layer.tips('验证码格式错误！','#yzm', {tips: [2,'red'],time:2000,tipsMore: true});
        }
    });

    //验证用户名和密码
    form.verify({
        userName: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
                return '用户名不能有特殊字符';
            }
            if(/(^\_)|(\__)|(\_+$)/.test(value)){
                return '用户名首尾不能出现下划线\'_\'';
            }
            if(/^\d+\d+\d$/.test(value)){
                return '用户名不能全为数字';
            }
            if(value.length<3||value.length>12){
                return '用户名长度为3-12位';
            }
        }
        //我们既支持上述函数式的方式，也支持下述数组的形式
        //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
        ,pwd: [/^[\S]{6,12}$/,'密码必须6到12位，且不能出现空格']
        ,verifyCheck:function (value, item) {
            if(!verifyCheckIf){  //如果验证码验证不通过
                return "验证码不正确";
            }
        }
    });

    //定义验证服务器的验证码
    function verifyCheck(yzm) {
        //在$.post()前把ajax设置为同步请求：
        $.ajaxSettings.async=false;
        $.post(
            "user/checkVerifyCode",
            {"yzm":yzm},
            function (data) {
                console.log("data:",data);
                if(data === 'success'){
                    verifyCheckIf = true;
                    layer.tips('验证码验证正确。','#yzm', {tips: [2,'green'],time:2000,tipsMore: true});
                }else { //验证失败
                    verifyCheckIf = false;
                    layer.tips('验证码验证错误！', '#yzm', {tips: [2, 'red'], time: 2000, tipsMore: true});
                }
            },"text" //text : 表示后端响应的是文本
        ).error(function (){
            layer.msg("数据请求异常！",{icon: 3,time:2000,anim: 3,shade:0.5});
        })
    }

    //向服务器发送指令，验证用户和密码
    function checkLogin(JsonLogin) {
        $.post(
            "user/checkLogin",
            JsonLogin,
            function (data) {
                if(data === 'success'){
                    layer.msg("登录成功！",{icon: 1,time:2000,anim: 3,shade:0.5});
                    //在2S之后，跳转到系统首页
                    setTimeout("window.location.href='model/toHome'",2000);
                }else{
                    layer.msg("登录失败！",{icon: 7,time:2000,anim: 3,shade:0.5});
                }
            },"text"
        ).error(function () {
            layer.msg("数据请求异常！",{icon: 7,time:2000,anim: 3,shade:0.5});
        })
    }

});