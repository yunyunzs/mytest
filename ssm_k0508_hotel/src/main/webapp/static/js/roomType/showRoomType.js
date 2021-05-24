layui.use(['jquery','layer','form','element','laypage'], function() {
    var $ = layui.jquery    //引入jquery模块
        , layer = layui.layer  //引用layer弹出层模块
        , form = layui.form  //引用form表单模块
        ,element = layui.element  //引用面板模块
        ,laypage = layui.laypage  //引用分页模块

    var page =1;  //当前页初始值为1

    var limit= 3 ;  //每一页数据条数

    var count =0;  //总的数据条数

    var qnyName = "http://qt6nn5f1q.hn-bkt.clouddn.com/";//七牛云的存储空间的域名

    var checkRoomsOfRoomTypeIf = false;  //验证房房型是否可删的判断

    var checkRoomTypeNameIf = false;   //验证房型名称唯一性判断

    //初始化客房类型首页数据，只执行1次
    loadPageRoomType();

    //初始化分页加载
    loadPage();

    //进行分页加载
    function loadPage(){
        //分页的完整功能
        laypage.render({
            elem: 'test1'  //绑定的分页标签容器
            ,count: count //总的数据条数
            ,page:page
            ,limit:limit //每一页显示3条数据，默认为10条
            ,limits:[2,3,5,8,10]  //进行每一页数据条数选择
            //展示分页标签的内容
            ,layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
            ,jump: function(obj,first){  //进行分页操作时的函数回调
                console.log("page="+obj.curr);
                console.log("limit="+obj.limit);
                page = obj.curr;  //当前页赋值给全局的当前页变量
                limit=obj.limit;
                //首次不执行，因为在初始化的时候就已经执行分页加载
                if(!first){
                    loadPageRoomType();  //分页数据加载
                }
            }
        });
    }

    //给页面中的删除修改按钮绑定点击事件
    $("#collapseDiv").on('click','button',function () {
        //获取按钮的操作类型
        var event = $(this).attr("event");
        if(event=='del'){
            //1.获取房型id
            var id = $(this).val();
            //2.进行房型的删除之前的验证
            checkRoomsOfRoomType(id);   //验证该房型下有没有客房数据，有则不能删除，否则可以删除
            if(checkRoomsOfRoomTypeIf){
                layer.confirm('真的删除此房型数据吗？', function (index) {
                    delRoomTypeById(id);  //执行房型数据删除
                    layer.close(index);  //关闭当前的询问框
                });
            }else {
                layer.msg("存在客房数据不能删除！！", {icon: 3, time: 2000, anim: 6, shade: 0.5});
            }
        }else {
            //layer.msg("执行修改操作")
            //1、回显
            var roomTypeArr =$(this).val().split(",");
            form.val("updRoomTypeFromFilter",{
                "id":roomTypeArr[0]
                ,"roomTypeName":roomTypeArr[1]
                ,"roomPrice":roomTypeArr[2]
            });
            //2、弹框
            layer.open({
                type:1,  //弹出类型
                title:"房型修改操作界面",  //弹框标题
                area:['380px','280px'],  //弹框款高度
                anim: 4,  //弹出的动画效果
                shade:0.5,  //阴影遮罩
                content:$("#updRoomTypeDiv")  //弹出的内容
            });
            //3、表单提交
            form.on('submit(demo4)',function (data) {
                var updJsonRoomType=data.field;
                updRoomType(updJsonRoomType);
                layer.closeAll();  //关闭所有弹框
                return false;  //阻止表单跳转提交
            });
        }
    })

    //添加房型数据
    $("#saveRoomTypeBtn").click(function () {
        //1、清空之前显示的数据 jqueny方式清空
        $("#saveRoomTypeBtnForm").find("input").val("");
        //2、弹框
        layer.open({
            type:1,  //弹出类型
            title:"房型添加操作界面",  //弹框标题
            area:['380px','280px'],  //弹框款高度
            anim: 3,  //弹出的动画效果
            shade:0.5,  //阴影遮罩
            content:$("#saveRoomTypeDiv")  //弹出的内容
        });
    });

    //3.监听提交按钮，执行添加
    form.on('submit(demo3)', function (data) {
        var saveJsonRoomType = data.field;
        saveRoomType(saveJsonRoomType);  //执行添加
        layer.closeAll();  //关闭所有弹框
        return false;  //阻止表单跳转提交
    });

    //监听折叠面板
    element.on('collapse(test)',function (data) {
        if (data.show){
            var roomTypeId=$(this).attr("roomTypeId");
            loadRoomsByRoomTypeId(roomTypeId);
        }
    });

    //添加表单验证
    form.verify({//value：表单的值、item：表单的DOM对象
        //效验房间类型
        roomTypeName: function (value, item) {
            checkRoomTypeName(value);
            if(!checkRoomTypeNameIf){
                return "房型名称有重复";
            }
        },
        //效验房间价格
        roomPrice: function (value, item) {
            if (value<120||value>8888){
                return "客房价格只能在120 ~ 8888之间";
            }
        }
    });

    /*************************加载数据自定义函数*******************************/
    //分页加载客房类型数据
    function loadPageRoomType(){
        //在$.post()前把ajax设置为同步请求：
        $.ajaxSettings.async = false;
        $.post(
            "roomType/loadDataByParams", //调用的是base系列的方法，只需要改mapper.xml文件
            {"page":page,"limit":limit},
            function (data){
                console.log(data);
                //将数据总的条数赋值给全局变量
                count = data.count;
                var roomTypeStr = ``;
                $.each(data.data,function (i,roomType){
                    roomTypeStr += `
                        <div class="layui-colla-item" id="item${roomType.id}" style="margin-top: 10px;">
                            <button type="button" class="layui-btn layui-btn-sm layui-btn-danger" event="del" value="${roomType.id}" style="float: right;">删除</button>
                            <button type="button" class="layui-btn layui-btn-sm layui-btn-warm" event="upd" value="${roomType.id},${roomType.roomTypeName},${roomType.roomPrice}" style="float: right;">修改</button>
                            <h2 class="layui-colla-title" roomTypeId="${roomType.id}">${roomType.roomTypeName} -- ${roomType.roomPrice}元/天</h2>
                            <div class="layui-colla-content">
                                <p id="p${roomType.id}"></p>
                            </div>
                        </div>
                    `;
                })
                $("#collapseDiv").html(roomTypeStr);
                //将面板渲染
                element.render("collapse")
            },"json"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }


    //验证该房型下有没有客房数据，有则不能删除，否则可以删除
    function checkRoomsOfRoomType(id){
        //在$.post()前把ajax设置为同步请求：
        $.ajaxSettings.async = false;
        $.post(
            "rooms/getCountByParams", //调用的是base系列的方法，只需要改mapper.xml文件
            {"roomTypeId":id},
            function (data){
                console.log(data);
                if(data == 0){
                    checkRoomsOfRoomTypeIf = true;
                }else{
                    checkRoomsOfRoomTypeIf = false;
                }
            },"json"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }

    //根据id删除单个房型数据
    function delRoomTypeById(id) {
        $.ajax({
            type: 'POST',
            url: 'roomType/delById',  //调用的是base系列的方法，只需要改mapper.xml文件
            data:{"id":id},
            success: function (data) {  //data为服务器端的map集合数据
                if(data=='delSuccess'){
                    layer.msg("房型数据删除成功", {icon: 1, time: 2000, anim:4, shade: 0.5});
                    console.log("page="+page);
                    loadPageRoomType();  //先执行分页数据加载
                    loadPage();  //更新一下总的数据条数
                }else {
                    layer.msg("房型数据删除成功失败！！", {icon: 2, time: 2000, anim: 3, shade: 0.5});
                }
            },
            error: function () {
                layer.msg("服务器异常！！！", {icon: 6, time: 2000, anim: 6, shade: 0.5});
            }
        });
    }

    //执行修改操作
    function updRoomType(updJsonRoomType){
        $.post(
            "roomType/updT", //调用的是base系列的方法，只需要改mapper.xml文件
            updJsonRoomType,
            function (data){
                if(data === 'success'){
                    loadPageRoomType(); //重新加载当前页
                    layer.msg("房型数据修改成功！",{icon: 1,time:2000,anim: 4,shade:0.5});
                }else{
                    layer.msg("房型数据修改失败！",{icon: 2,time:2000,anim: 4,shade:0.5});
                }
            },"text"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }

    //验证其唯一性，根据房型名称查询房型数据条数
    function checkRoomTypeName(roomTypeName) {
        //在$.post()前把ajax设置为同步请求：
        $.ajaxSettings.async = false;
        $.post(
            "roomType/getCountByParams", //调用的是base系列的方法，只需要改mapper.xml文件
            {"roomTypeName":roomTypeName},
            function (data){
                console.log(data);
                if(data == 0){
                    checkRoomTypeNameIf = true;
                    layer.tips('没有重复的房型名称，验证通过','#roomTypeName', {tips: [2,'green'],time:2000});
                }else{
                    checkRoomTypeNameIf = false;
                    layer.tips('有重复的房型名称，验证不通过','#roomTypeName', {tips: [2,'red'],time:2000});
                }

            },"json"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }

    //添加房型数据
    function saveRoomType(saveJsonRoomType){
        $.post(
            "roomType/saveT", //调用的是base系列的方法，只需要改mapper.xml文件
            saveJsonRoomType,
            function (data){
                if(data === 'success'){
                    page = 1;  //使页面当前页为1
                    loadPageRoomType(); //重新加载当前页
                    loadPage(); //由于数据的条数发送变化，作用重新加载layui的分页组件
                    layer.msg("房型数据添加成功！",{icon: 1,time:2000,anim: 4,shade:0.5});
                }else{
                    layer.msg("房型数据添加失败！",{icon: 2,time:2000,anim: 4,shade:0.5});
                }
            },"text"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }

    //根据此房型id数据查询多个客房数据
    function loadRoomsByRoomTypeId(roomTypeId){
        $.post(
            "rooms/loadManyByParams", //调用的是base系列的方法，只需要改mapper.xml文件
            {"roomTypeId":roomTypeId},
            function (data){
                console.log(data);
                if(!$.isEmptyObject(data)){ //此房型有客房数据
                    var rooms = `<ul class="site-doc-icon site-doc-anim">`;
                    $.each(data,function (i,room){
                        if(room.roomStatus == '0'){
                            rooms += `<li style="background-color: #009688;">`;
                        }else if(room.roomStatus == '1'){
                            rooms += `<li style="background-color: red;">`;
                        }else {
                            rooms += `<li style="background-color: blueviolet;">`;
                        }
                        rooms += `<img class="layui-anim" id="demo1" src="${qnyName}/${room.roomPic}" width="135px" height="135px"/>
                            <div class="code">
                            <span style="display: block;color: #0C0C0C;">${room.roomNum} - ${room.roomType.roomTypeName} - ${room.roomType.roomPrice}元/天</span>
                            </div>
                            </li>    
                            `;
                    })
                    rooms += `</ul>`;
                    $("#p"+roomTypeId).html(rooms);
                }else{ //此房型没有客房数据
                    layer.msg("此房型没有客房数据！",{icon: 7,time: 2000,anim:6,shade:0.5})
                }
            },"json"
        ).error(function (){
            layer.msg("服务器异常！！！",{icon: 3,time:2000,anim: 6,shade:0.5});
        })
    }

});