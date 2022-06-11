package com.lebrwcd.eduservice.controller;/**
 * @author lebrwcd
 * @date 2022/5/1
 * @note
 */

import com.lebrwcd.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName EduLoginController
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/1
 */
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {

    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R getInfo(){
        return R.ok().data("roles","[administrator]").data("name","wcd")
                .data("avatar","https://lebrwcd-edu1010.oss-cn-guangzhou.aliyuncs.com/2022/05/02/68ce7996e84default_colleagues.jpg");
    }

}
