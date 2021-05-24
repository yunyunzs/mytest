<%--
  Created by IntelliJ IDEA.
  User: Klaus
  Date: 2021/5/13
  Time: 19:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>Title</title>
</head>
<body>
<div style="display: none;margin-top: 20px;" id="roomSaleDiv">
    <form class="layui-form layui-form-pane" action="" lay-filter="roomSaleForm" style="margin-left: 50px;">
        <!--入住信息id-->
        <input type="hidden" name="id"/>
        <!--会员折扣-->
        <input type="hidden" name="vipRate" id="vipRate"/>
        <div class="layui-form-item">
            <label class="layui-form-label">房间号：</label>
            <div class="layui-input-inline">
                <input type="text" name="roomNum" id="roomsNum" lay-verify="required" autocomplete="off" class="layui-input" disabled>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">客人姓名：</label>
                <div class="layui-input-block">
                    <input type="text" name="customerName" autocomplete="off" class="layui-input" disabled>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">入住时间：</label>
                <div class="layui-input-block">
                    <input type="text" name="startDate" id="startDate" autocomplete="off" class="layui-input" disabled>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">退房时间：</label>
                <div class="layui-input-inline">
                    <input type="text" name="endDate" id="endDate" autocomplete="off" class="layui-input" disabled>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">房间单价：</label>
                <div class="layui-input-block">
                    <input type="text" name="roomPrice" id="onePrice" autocomplete="off" class="layui-input" disabled>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">住宿天数：</label>
                <div class="layui-input-inline">
                    <input type="text" name="days" autocomplete="off" class="layui-input" disabled>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">住宿金额：</label>
                <div class="layui-input-inline">
                    <input type="number" name="rentPrice" lay-verify="required" value="0" autocomplete="off" class="layui-input" placeholder="请输入金额" id="rentPrice" disabled>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">其它消费：</label>
                <div class="layui-input-inline">
                    <input type="number" name="otherPrice" lay-verify="required" value="0" autocomplete="off" class="layui-input" placeholder="请输入金额" id="otherPrice" disabled>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">支付金额：</label>
                <div class="layui-input-inline">
                    <input type="number" name="salePrice" lay-verify="required" value="0" autocomplete="off" class="layui-input" placeholder="请输入金额" id="salePrice" disabled>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">优惠金额：</label>
                <div class="layui-input-inline">
                    <input type="number" name="discountPrice" lay-verify="required" value="0" autocomplete="off" class="layui-input" placeholder="请输入金额" id="discountPrice" disabled>
                </div>
            </div>
        </div>
        <div class="layui-form-item" style="margin-left: 70px;">
            <button class="layui-btn layui-btn-lg" lay-submit="" lay-filter="demo3"><i class="layui-icon">&#xe605;</i>关闭</button>
        </div>
    </form>
    </body>
</html>
</html>
