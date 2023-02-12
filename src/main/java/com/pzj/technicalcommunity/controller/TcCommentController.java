package com.pzj.technicalcommunity.controller;


import com.pzj.technicalcommunity.service.ITcCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pzj
 * @since 2023-02-06
 */
@RestController
@RequestMapping("/comment")
public class TcCommentController {
    @Autowired
    private ITcCommentService iTcCommentService;
}
