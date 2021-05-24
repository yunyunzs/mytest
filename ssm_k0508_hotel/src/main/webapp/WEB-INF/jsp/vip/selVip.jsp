<%--
  Created by IntelliJ IDEA.
  User: Klaus
  Date: 2021/5/13
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查看vip页面</title>
</head>
<body>
<div align="center" style="display: none;margin-top: 20px;" id="showVipDiv" style="margin-left: 80px;">
    <form class="layui-form layui-form-pane" action=""  lay-filter="showVipForm" >
        <input type="hidden" id="id" name="id"/>
        <div class="layui-form-item">
            <label class="layui-form-label">身份证号：</label>
            <div class="layui-input-inline" style="width: 300px;">
                <!--identity为身份证号验证-->
                <input type="text" name="idcard" id="idcard" lay-verify="required" placeholder="请输入身份证号" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">客人姓名：</label>
            <div class="layui-input-inline" style="width: 300px;">
                <input type="text" name="customerName" lay-verify="required" placeholder="请输入客人姓名" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">会员卡号：</label>
            <div class="layui-input-inline" style="width: 300px;">
                <input type="text"  name="vipNum" id="vipNum" placeholder="自动生成会员卡号" autocomplete="off" class="layui-input" >
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">联系方式：</label>
            <div class="layui-input-inline" style="width: 300px;">
                <input type="text" id="phone" name="phone" placeholder="请输入联系方式" lay-verify="required " autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">创建会员时间</label>
            <div class="layui-input-inline" style="width: 300px">
                <input type="text" name="createDate" id="createDate" lay-verify="required" placeholder="yyyy/MM/dd HH:mm:ss" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">会员类型：</label>
            <div class="layui-input-inline" style="width: 300px;">
                <select name="vipRate" lay-filter="vipRate"  lay-verify="required">
                    <option value="">---请选择会员类型---</option>
                    <option value="0.9">普通会员</option>
                    <option value="0.8">超级会员</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" >选择性别：</label>
            <div class="layui-input-block">
                <input type="radio" name="gender" value="1" title="男" checked="checked"/>
                <input type="radio" name="gender" value="0" title="女"/>
                <input type="radio" name="gender" value="2" title="未识别" disabled="">
            </div>
        </div>
        <div class="layui-form-item" style="margin-left: 100px;margin-top: 40px;">
<%--            <button class="layui-btn" lay-submit="" lay-filter="demo4">关闭窗口</button>--%>
    <%--        <button type="reset" class="layui-btn layui-btn-primary">重置</button>--%>
        </div>
    </form>
    </div>
</body>
</html>
