package com.lebrwcd.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.commonutils.JwtUtils;
import com.lebrwcd.commonutils.R;
import com.lebrwcd.eduservice.client.MemberClient;
import com.lebrwcd.eduservice.entity.EduComment;
import com.lebrwcd.eduservice.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author lebrwcd
 * @since 2022-05-29
 */
@RestController
@RequestMapping("/eduservice/comment")
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;


    /**
     * 课程评论分页
     * @param pageSize
     * @param limit
     * @return
     */
    @GetMapping("/{courseId}/{pageSize}/{limit}")
    public R commentPage(@PathVariable String courseId,
                         @PathVariable long pageSize,
                         @PathVariable long limit) {

        Page<EduComment> page = new Page<>(pageSize,limit);

        //根据课程ud查询该课程的所有评论
        QueryWrapper<EduComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        //按照时间降序
        queryWrapper.orderByDesc("gmt_create");
        commentService.page(page, queryWrapper);

        HashMap<String, Object> map = new HashMap<>();

        map.put("pages", page.getPages());
        map.put("records", page.getRecords());
        map.put("size", page.getSize());
        map.put("current", page.getCurrent());
        map.put("total", page.getTotal());
        map.put("hasNext", page.hasNext());
        map.put("hasPrevious", page.hasPrevious());

        return R.ok().data(map);
    }


    /**
     * 添加评论要添加的数据有：
     *          课程评论内容
     *          课程id
     *          讲师id
     *          用户id
     *          用户昵称
     *          用户头像
     *
     * @return
     */
    @PostMapping("save")
    public R saveComment(@RequestBody EduComment eduComment,
                         HttpServletRequest request) {

        boolean save = commentService.saveComment(eduComment,request);

        if(save) {
            return R.ok();
        }

        return R.error();
    }

}

