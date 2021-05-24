layui.use(['jquery','layer', 'table','form','upload','element'], function() {
    var $ = layui.jquery    //引入jquery模块
        , layer = layui.layer  //引用layer弹出层模块
        , table = layui.table  //引用table数据表格模块
        , form = layui.form  //引用form表单模块
        , upload = layui.upload //文件上传组件
        , element = layui.element; //引入常用元素操作的组件


    //layer.msg("测试。。");

    //七牛云的存储空间的域名
    var qnyName = "http://qt6nn5f1q.hn-bkt.clouddn.com/";

    var arrUl = $("#LAY_preview").find("ul");

    //初始化所有的客房数据
    loadAllRooms();

    //初始化所有的客房类型数据
    loadAllRoomType();



    //常规使用 - 普通图片上传
    var uploadInst = upload.render({
        elem: '#test1' //绑定上传的容器
        ,url: 'rooms/uploadRoomPic' //改成您自己的上传接口
        ,data: {"path":"D:\\img"} //传递到后端的参数，表示上传路径
        ,field: "myFile"  //表示文件上传的对象
        ,auto: false //取消自动上传
        ,bindAction: '#test9'  //绑定开始上传文件按钮，实现手动上传
        ,size: 1024  //设置文件最大可允许上传的大小，单位 KB。不支持ie8/9
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#demo1').attr('src', result); //图片链接（base64）
            });

            element.progress('demo', '0%'); //进度条复位
            layer.msg('上传中', {icon: 16, time: 0});
        }
        ,done: function(res){  //执行服务器上传后的函数回调
            //如果上传失败
            if(res.code > 0){
                return layer.msg("文件上传失败！",{icon: 3,time:2000,anim: 6,shade:0.5});
            }else{
                console.log(res.newFileName);
                return layer.msg("文件上传成功！",{icon: 1,time:2000,anim: 4,shade:0.5});
            }
            //上传成功的一些操作
            //……
            $('#demoText').html(''); //置空上传失败的状态
        }
        ,error: function(){ //上传异常的函数回调
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
        //进度条
        ,progress: function(n, index, e){
            element.progress('demo', n + '%'); //可配合 layui 进度条元素使用
            if(n == 100){
                layer.msg('上传完毕', {icon: 1});
            }
        }
    });

    //将打扫的客房进行删除和设置为空闲状态的操作
    $("ul").eq(2).on("click","button",function () {
        //取到判断进行操作的变量
        var event = $(this).val();
        //获取客房id
        var roomid = $(this).attr("roomid");
        if(event=='del'){  //执行显示状态的修改操作
            layer.confirm('真的删除此客房数据吗？', function (index) {
                //执行客房显示状态的修改
                updRoomsFlag(roomid,'0');
                layer.close(index);  //关闭当前的询问框
            });
        }else {
            //layer.msg("空闲状态修改。。");
            layer.confirm('真的把此客房改为空闲状态吗？', function (index) {
                //执行客房空闲状态的修改2（打扫）----->0（空闲）
                updRoomsStatus(roomid,'0');
                layer.close(index);  //关闭当前的询问框
            });
        }
    });

    //点击添加按钮
    $("#saveRoomsUI").click(function () {
        //2.清空添加的表单
        $("form").eq(0).find("input").val("");
        element.progress('demo', '0%'); //进度条复位
        //3.回显原有默认图片
        $('#demo1').attr('src', "${qnyName}/fm1.jpg");
        $("#roomPicId").val("fm1.jpg");
        //1.将添加界面弹出
        layer.open({
            type:1,  //弹出类型
            title:"客房添加操作界面",  //弹框标题
            area:['400px','500px'],  //弹框款高度
            anim: 2,  //弹出的动画效果
            shade:0.5,  //阴影遮罩
            content:$("#saveRoomsDiv")  //弹出的内容
        });
    });

    //将空闲的客房的显示状态由1(显示)----->0(不显示)
    $("ul").eq(0).on("click","button",function () {
        //获取客房id
        var roomid = $(this).attr("roomid");
        layer.confirm('真的删除此客房数据吗？', function (index) {
            //执行客房显示状态的修改
            updRoomsFlag(roomid,'0');
            layer.close(index);  //关闭当前的询问框
        });
    });

    var checkRoomNumIf = false;  //房间号唯一性验证的变量

    //1.自定义验证
    form.verify({  //做表单提交时的验证
        roomNum: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(value.length<4||value.length>5){
                return '房间号长度为4-5位';
            }
            if(!checkRoomNumIf){  //已经进行过唯一性验证了
                return '房间号已被使用';
            }
        }
    });

    //2.进行房间号的唯一性验证
    $("#roomNum").blur(function () {
        var roomNum = $(this).val();
        if(roomNum.length>=4&&roomNum.length<=5) {
            checkRoomNum(roomNum);  //进行房间号的唯一性验证
        }
    });

    //加载所有的客房数据
    function loadAllRooms(){
        $.post(
            "/rooms/loadAll", //调用的是base系列的方法，只需要改mapper.xml文件
            function (data){
                console.log("data:",data);
                var roomStatus0 = ""; //空闲客房状态数据标签字符串
                var roomStatus1 = "";//已入住客房状态数据标签字符串
                var roomStatus2 = "";//打扫客房状态数据标签字符串
                $.each(data, function (i,room){
                    if(room.roomStatus == '0'){ //空闲
                        roomStatus0 += `
                        <li style="background-color: #009688;">
                        <img class="layui-anim" src="${qnyName}/${room.roomPic}" width="135" height="135">
                        <div class="code">
                        <span style="display: block; color: #0C0C0C;">
                        ${room.roomNum} - ${room.roomType.roomTypeName} - ${room.roomType.roomPrice} 元/天
                        </span>
                        <button type="button" value="del" roomid="${room.id}" class="layui-btn layui-btn-danger layui-btn-xs">删除</button>
                        </div>
                        </li>
                        `;
                    }else if(room.roomStatus == '1'){ //已入住
                        roomStatus1 += `
                        <li style="background-color: red;">
                        <img class="layui-anim" src="${qnyName}/${room.roomPic}" width="135" height="135">
                        <div class="code">
                        <span style="display: block; color: #0C0C0C;">
                        ${room.roomNum} - ${room.roomType.roomTypeName} - ${room.roomType.roomPrice} 元/天
                        </span>
                        </div>
                        </li>
                        `;
                    }else{ //打扫
                        roomStatus2 += `
                        <li style="background-color: blueviolet;">
                        <img class="layui-anim" src="${qnyName}/${room.roomPic}" width="135" height="135">
                        <div class="code">
                        <span style="display: block; color: #0C0C0C;">
                        ${room.roomNum} - ${room.roomType.roomTypeName} - ${room.roomType.roomPrice} 元/天
                        </span>
                        <button type="button" value="del" roomid="${room.id}" class="layui-btn layui-btn-danger layui-btn-xs">删除</button>
                        <button type="button" value="upd" roomid="${room.id}" class="layui-btn layui-btn-normal layui-btn-xs">空闲</button>
                        </div>
                        </li>
                        `;
                    }
                })
                //分别将三种状态的客房标签数据填充到对应的ul列表中
                $(arrUl[0]).html(roomStatus0);
                $(arrUl[1]).html(roomStatus1);
                $(arrUl[2]).html(roomStatus2);
                hoverOpenImg(); //加载图片放大函数
            },"json"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }

    //图片放大镜
    function hoverOpenImg(){
        var img_show = null; // tips提示
        $('img').hover(function(){
            var img = "<img class='img_msg' src='"+$(this).attr('src')+"' style='width:580px;' />";
            img_show = layer.tips(img, this,{
                tips:[2, 'rgba(41,41,41,.5)']
                ,area: ['600px']
                ,time: -1  //永久显示
                ,anim: 3
            });
        },function(){
            layer.close(img_show);
        });
        $('img').attr('style','max-width:270px');
    }

    //客房显示状态的修改
    function updRoomsFlag(roomid,flag){
        $.post(
            "/rooms/updT", //调用的是base系列的方法，只需要改mapper.xml文件
            {"id":roomid, "flag":flag},
            function (data){
                if(data === 'success'){
                    //重新加载客房数据
                    loadAllRooms();
                    layer.msg("空闲客房删除成功！",{icon: 1,time:2000,anim: 4,shade:0.5});
                }else{
                    layer.msg("空闲客房删除失败！",{icon: 2,time:2000,anim: 4,shade:0.5});
                }
            },"text"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }

    //客房空闲状态的修改2（打扫）----->0（空闲）
    function updRoomsStatus(roomid,roomStatus){
        $.post(
            "/rooms/updT", //调用的是base系列的方法，只需要改mapper.xml文件
            {"id":roomid, "roomStatus":roomStatus},
            function (data){
                if(data === 'success'){
                    //重新加载客房数据
                    loadAllRooms();
                    layer.msg("客房状态修改成功！",{icon: 1,time:2000,anim: 4,shade:0.5});
                }else{
                    layer.msg("客房状态修改失败！",{icon: 2,time:2000,anim: 4,shade:0.5});
                }
            },"text"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }

    //加载所有的客房类型数据
    function loadAllRoomType(){
        $.post(
            "/roomType/loadAll", //调用的是base系列的方法，只需要改mapper.xml文件
            function (data){
                console.log(data);
                var roomTypeStr = `<option value="">---请选择客房类型---</option>`;
                $.each(data,function (i, roomType){
                    roomTypeStr += `<option value="${roomType.id}">${roomType.roomTypeName} - ${roomType.roomPrice}</option>`;
                })
                $("#selRoomType").html(roomTypeStr);
                form.render("select"); //渲染下拉框
            },"json"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }

    //房间号的唯一性验证
    function checkRoomNum(roomNum){
        //在$.post()前把ajax设置为同步请求：
        $.ajaxSettings.async = false;
        $.post(
            "/rooms/getCountByParams", //调用的是base系列的方法，只需要改mapper.xml文件
            {"roomNum":roomNum},
            function (data){
                console.log(data);
                if(data == 0){
                    checkRoomNumIf = true;
                    layer.tips('此房间号可用','#roomNum', {tips: [2,'green'],time:2000});
                }else{
                    checkRoomNumIf = false;
                    layer.tips('此房间号已被使用','#roomNum', {tips: [2,'red'],time:2000});
                }
            },"json"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }

    //执行客房的添加操作
    function saveRooms(saveJsonRooms){
        $.post(
            "/rooms/saveT", //调用的是base系列的方法，只需要改mapper.xml文件
            saveJsonRooms,
            function (data){
                if(data === 'success'){
                    //重新加载客房数据
                    loadAllRooms();
                    layer.msg("客房数据添加成功！",{icon: 1,time:2000,anim: 4,shade:0.5});
                }else{
                    layer.msg("客房数据添加失败！",{icon: 2,time:2000,anim: 4,shade:0.5});
                }
            },"text"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }
});