package com.lebrwcd.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.eduservice.entity.EduTeacher;
import com.lebrwcd.commonutils.R;
import com.lebrwcd.eduservice.entity.vo.TeacherQuery;
import com.lebrwcd.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lebrwcd
 * @since 2022-04-24
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")   //讲师模块
@Slf4j
public class EduTeacherController {

    @Resource
    private EduTeacherService eduTeacherService;

    /**
     * 查询所有讲师
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public R findAll(){
        List<EduTeacher> list = eduTeacherService.list(null);
        log.info("共有 " + list.size() +"条记录");

        return R.ok().data("item",list);
    }

    /**
     * 根据讲师id逻辑删除
     * @param id
     * @return
     */
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("/{id}")
    public R deleteById(@ApiParam(required = true,value = "讲师ID") @PathVariable("id") String id){

        log.info("删除的讲师id值为:" + id);
        boolean flag = eduTeacherService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     *
     * @param current  当前页码
     * @param limit    每页显示多少条记录
     * @return
     */
    @ApiOperation(value = "分页查询")
    @PostMapping("page/{current}/{limit}")
    public R pageList(@PathVariable("current") long current,
                      @PathVariable("limit") long limit,
                      @RequestBody(required = false) TeacherQuery teacherQuery){

        Page<EduTeacher> eduTeacherPage = new Page<>(current,limit);

        //根据条件查询
        //page方法返回的分页数据封装在 分页对象eduTeacherPage中
        eduTeacherService.pageQuery(eduTeacherPage,teacherQuery);

        //总记录数
        long total = eduTeacherPage.getTotal();
        //每页显示的记录
        List<EduTeacher> records = eduTeacherPage.getRecords();

        //返回数据
        return R.ok().data("total",total).data("raws",records);

    }

    /**
     * 添加讲师
     * @param eduTeacher
     * @return
     */
    @ApiOperation("添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);

        //int a = 10 / 0;
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    /**
     * 根据讲师id查询该讲师信息
     * @param id
     * @return
     */
    @ApiOperation("根据id查询讲师")
    @GetMapping("/getTeacher/{id}")
    public R getById(@PathVariable("id") String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    /**
     * 更新讲师信息
     * @param eduTeacher
     * @return
     */
    @ApiOperation("更新讲师信息")
    @PostMapping("update")
    public R update(@RequestBody EduTeacher eduTeacher){
        boolean update = eduTeacherService.updateById(eduTeacher);
        if(update){
            return R.ok();
        }else{
            return R.error();
        }
    }

}

