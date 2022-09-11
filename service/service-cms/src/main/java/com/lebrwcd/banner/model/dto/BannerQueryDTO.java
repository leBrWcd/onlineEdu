package com.lebrwcd.banner.model.dto;/**
 * @author lebrwcd
 * @date 2022/9/11
 * @note
 */

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.Date;

/**
 * ClassName BannerQueryDTO
 * Description Banner 查询对象
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/9/11
 */
@Data
@ApiModel("BannerQueryDTO")
public class BannerQueryDTO {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "图片地址")
    private String imageUrl;

    @ApiModelProperty(value = "链接地址")
    private String linkUrl;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtUpdate;


}
