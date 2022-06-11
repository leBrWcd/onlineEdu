package com.lebrwcd.ucenter.entity.vo;/**
 * @author lebrwcd
 * @date 2022/5/14
 * @note
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName RegisterVo
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/14
 */
@Data
@ApiModel(value="注册对象", description="注册对象")
public class RegisterVo {

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;
}
