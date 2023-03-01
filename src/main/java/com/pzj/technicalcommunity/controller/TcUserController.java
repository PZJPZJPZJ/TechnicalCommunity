package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcUser;
import com.pzj.technicalcommunity.service.ITcUserService;
import com.pzj.technicalcommunity.util.JwtUtils;
import com.pzj.technicalcommunity.util.PasswordEncoder;
import com.pzj.technicalcommunity.util.ResultPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pzj
 * @since 2023-02-06
 */
@RestController
@RequestMapping("/user")
public class TcUserController {
    @Autowired
    private ITcUserService iTcUserService;

    /**
     * Description 分页查询用户信息
     * Param 分页信息(json)
     * Return 分页用户数据(bool)
     */
    @PostMapping("/page")
    public ResultPackage listPage(@RequestBody HashMap hashMap){
        Page<TcUser> page = new Page<>();
        page.setCurrent((int)hashMap.get("pageNum"));
        page.setSize((int)hashMap.get("pageSize"));
        //设置随机查询热门条件
        QueryWrapper<TcUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id").select("user_admin").select("user_name").select("user_sign").select("user_sex").select("user_birth");
        //执行查询
        IPage<TcUser> iPage = iTcUserService.page(page,queryWrapper);
        return ResultPackage.pack(iPage.getRecords(),iPage.getTotal());
    }

    //模糊查询用户信息
    @PostMapping ("/search")
    public ResultPackage search(@RequestBody TcUser tcUser){
        //设置查询条件
        //LambdaQueryWrapper<TcUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //lambdaQueryWrapper.like(TcUser::getUserName,tcUser.getUserName());
        QueryWrapper<TcUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name",tcUser.getUserName()).select("user_id").select("user_name");
        //执行查询
        List<TcUser> list = iTcUserService.list(queryWrapper);
        return ResultPackage.pack(list);
    }

    /**
     * Description 用户注册
     * Param 用户实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/save")
    public boolean save(@RequestBody TcUser tcUser){
        tcUser.setUserPassword(PasswordEncoder.encode(tcUser.getUserPassword()));
        return iTcUserService.save(tcUser);
    }

    /**
     * Description 用户信息修改
     * Param 用户实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/update")
    public boolean update(@RequestBody TcUser tcUser){
        if(tcUser.getUserPassword() != null){
            tcUser.setUserPassword(PasswordEncoder.encode(tcUser.getUserPassword()));
        }
        return iTcUserService.updateById(tcUser);
    }

    /**
     * 查询用户登录Token是否过期
     * @param hashMap
     * @return HTTP状态码
     */
    @PostMapping("/token")
    public ResponseEntity<String> token(@RequestBody HashMap hashMap){
        if(JwtUtils.isTokenExpired((String) hashMap.get("token"))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }
}
