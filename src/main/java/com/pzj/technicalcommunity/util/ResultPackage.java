package com.pzj.technicalcommunity.util;

import lombok.Data;

@Data
//封装后端返回前端数据
public class ResultPackage {
    //编码
    private int code;
    //消息
    private String msg;
    //总记录数
    private Long total;
    //数据
    private Object data;

    public static ResultPackage result(int code,String msg,Long total,Object data){
        ResultPackage resultPackage = new ResultPackage();
        resultPackage.setCode(code);
        resultPackage.setMsg(msg);
        resultPackage.setTotal(total);
        resultPackage.setData(data);
        return resultPackage;
    }

    //查询成功调用
    public static ResultPackage success(Object data){
        return result(200,"成功",null,data);
    }
    public static ResultPackage success(Object data,Long total){
        return result(200,"成功",total,data);
    }

    //无数据时调用
    public static ResultPackage fail(){
        return result(204,"失败",null,null);
    }
}
