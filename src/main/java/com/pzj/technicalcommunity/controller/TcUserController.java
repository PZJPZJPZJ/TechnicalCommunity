package com.pzj.technicalcommunity.controller;


import com.pzj.technicalcommunity.entity.TcUser;
import com.pzj.technicalcommunity.service.ITcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pzj
 * @since 2023-02-04
 */
@RestController
@RequestMapping("/user")
public class TcUserController {
    @Autowired
    private ITcUserService iTcUserService;
    @GetMapping("/list")
    public List<TcUser> list(){
        return iTcUserService.list();
    }
}
