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
    private Object rows;

    private static ResultPackage set(int code,String msg,Long total,Object rows){
        ResultPackage resultPackage = new ResultPackage();
        resultPackage.setCode(code);
        resultPackage.setMsg(msg);
        resultPackage.setTotal(total);
        resultPackage.setRows(rows);
        return resultPackage;
    }

    public static ResultPackage message(Integer code, String msg){
        return set(code,msg,null,null);
    }

    public static ResultPackage pack(){
        return set(200,"成功",0L,null);
    }

    public static ResultPackage pack(Object rows){
        return set(200,"成功",1L,rows);
    }

    public static ResultPackage pack(Object rows, Long total){
        return set(200,"成功",total,rows);
    }
}
