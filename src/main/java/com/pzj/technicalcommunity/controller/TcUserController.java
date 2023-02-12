package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcUser;
import com.pzj.technicalcommunity.service.ITcUserService;
import com.pzj.technicalcommunity.util.ResultPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    //列出用户信息
    @GetMapping ("/list")
    public ResultPackage list(){
        return ResultPackage.pack(iTcUserService.list());
    }

    //分页查询用户信息
    @PostMapping("/page")
    public ResultPackage listPage(@RequestBody HashMap hashMap){
        Page<TcUser> page = new Page<>();
        page.setCurrent((int)hashMap.get("pageNum"));
        page.setSize((int)hashMap.get("pageSize"));
        IPage<TcUser> iPage = iTcUserService.page(page);
        return ResultPackage.pack(iPage.getRecords(),iPage.getTotal());
    }

    //模糊查询用户信息
    @PostMapping ("/search")
    public ResultPackage search(@RequestBody TcUser tcUser){
        //设置查询条件
        LambdaQueryWrapper<TcUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(TcUser::getUserName,tcUser.getUserName());
        //执行查询
        List<TcUser> list = iTcUserService.list(lambdaQueryWrapper);
        return ResultPackage.pack(list);
    }

    /**
     * Description 用户注册
     * Param 用户实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/save")
    public boolean save(@RequestBody TcUser tcUser){
        return iTcUserService.save(tcUser);
    }

    /**
     * Description 用户信息修改
     * Param 用户实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/update")
    public boolean update(@RequestBody TcUser tcUser){
        return iTcUserService.updateById(tcUser);
    }

    /**
     * Description 新增或修改用户
     * Param 用户实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/mod")
    public boolean mod(@RequestBody TcUser tcUser){
        return iTcUserService.saveOrUpdate(tcUser);
    }

    /**
     * Description 删除用户
     * Param 用户ID(url)
     * Return 执行结果(bool)
     */
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return iTcUserService.removeById(id);
    }
}
