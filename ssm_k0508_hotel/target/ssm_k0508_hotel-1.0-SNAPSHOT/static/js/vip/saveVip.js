layui.use(['jquery','layer', 'table','form','laydate'], function() {
    var $ = layui.jquery    //引入jquery模块
        , layer = layui.layer  //引用layer弹出层模块
        , table = layui.table  //引用table数据表格模块
        , form = layui.form  //引用form表单模块
        , laydate = layui.laydate;  //引用日期模块

    //layer.msg("测试。。");

    var checkIdCardIf = false;  //判断身份证号的唯一性全局变量

    var checkPhoneIf = false;  //判断手机号的唯一性全局变量

    //自定义验证
    form.verify({  //做表单提交时的验证
        checkIdCard: function(value, item){ //value：表单的值、item：表单的DOM对象
            checkIdCard(value);  //身份证号的唯一性验证，发送ajax请求访问数据库
            if(!checkIdCardIf){
                return '该身份证号已被使用！！';
            }
        },
        checkPhone: function(value, item){ //value：表单的值、item：表单的DOM对象
            checkPhone(value);  //手机号的唯一性验证，发送ajax请求访问数据库
            if(!checkPhoneIf){
                return '该手机号已被使用！！';
            }
        }
    });

    //下拉框的监听
    form.on('select(vipRate)', function(data){
        var nowDateStr = getNowDate(new Date());//获取当前时间的字符串  格式：yyyy/MM/dd HH:mm:ss
        $("#createDate").val(nowDateStr);  //将当前数据字符串数据回填到隐藏域，一并做添加提交
        if(data.value=='0.8'){  //得到被选中的值
            //生成会员卡号：当前时间转为数字字符串+01/02
            $("#vipNum").val(dateReplace(nowDateStr)+"01");
        } else {
            $("#vipNum").val(dateReplace(nowDateStr)+"02");
        }
    });

    //监听提交按钮，执行添加
    form.on('submit(demo2)', function (data) {
        var saveJsonVip = data.field;  //重新将查询条件赋值
        saveVip(saveJsonVip);  //执行添加
        return false;  //阻止表单跳转提交
    });

    //身份证号的唯一性验证
    function checkIdCard(idcard){
        //在$.post()前把ajax设置为同步请求：
        $.ajaxSettings.async = false;
        $.post(
            "vip/getCountByParams", //调用的是base系列的方法，只需要改mapper.xml文件
            {"idcard":idcard},
            function (data){
                console.log(data);
                if(data == 0){
                    checkIdCardIf = true;
                    //tipsMore: true允许多个吸附框的弹出
                    layer.tips('此身份证号可以使用','#idcard', {tips: [2,'green'],time:2000,tipsMore: true});
                }else{
                    checkIdCardIf = false;
                    layer.tips('此身份证号已被使用','#idcard', {tips: [2,'red'],time:2000,tipsMore: true});
                }
            },"json"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }

    //手机号的唯一性验证
    function checkPhone(phone){
        //在$.post()前把ajax设置为同步请求：
        $.ajaxSettings.async = false;
        $.post(
            "vip/getCountByParams", //调用的是base系列的方法，只需要改mapper.xml文件
            {"phone":phone},
            function (data){
                console.log(data);
                if(data == 0){
                    checkPhoneIf = true;
                    layer.tips('此手机号可以使用','#phone', {tips: [2,'green'],time:2000,tipsMore: true});
                }else{
                    checkPhoneIf = false;
                    layer.tips('此手机号已被使用','#phone', {tips: [2,'red'],time:2000,tipsMore: true});
                }
            },"json"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }



    //会员添加
    function saveVip(saveJsonVip){
        $.post(
            "vip/saveT", //调用的是base系列的方法，只需要改mapper.xml文件
            saveJsonVip,
            function (data){
                if(data === 'success'){
                    layer.msg("会员数据添加成功！",{icon: 1,time:2000,anim: 4,shade:0.5});
                    //定时器，2s后跳转到会员信息显示页面
                    setTimeout("window.location.href='model/toShowVip'",2000);
                }else{
                    layer.msg("会员数据添加失败！",{icon: 2,time:2000,anim: 4,shade:0.5});
                }
            },"text"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }
});