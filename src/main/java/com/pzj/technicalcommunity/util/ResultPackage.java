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

    private static ResultPackage set(int code,String msg,Long total,Object data){
        ResultPackage resultPackage = new ResultPackage();
        resultPackage.setCode(code);
        resultPackage.setMsg(msg);
        resultPackage.setTotal(total);
        resultPackage.setData(data);
        return resultPackage;
    }

    public static ResultPackage pack(){
        return set(204,"成功",0L,null);
    }

    public static ResultPackage pack(Object data){
        return set(200,"成功",1L,data);
    }

    public static ResultPackage pack(Object data,Long total){
        return set(200,"成功",total,data);
    }
}
