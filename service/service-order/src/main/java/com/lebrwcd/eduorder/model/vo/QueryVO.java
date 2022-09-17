package com.lebrwcd.eduorder.model.vo;/**
 * @author lebrwcd
 * @date 2022/9/17
 * @note
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName QueryVO
 * Description 查询返回对象
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/9/17
 */
@Data
@ApiModel("QueryVo")
public class QueryVO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "会员昵称")
    private String nickname;

    @ApiModelProperty(value = "课程名称")
    private String courseTitle;

    @ApiModelProperty(value = "订单金额（分）")
    private BigDecimal totalFee;

    @ApiModelProperty(value = "支付类型（1：微信 2：支付宝）")
    private Integer payType;

    @ApiModelProperty(value = "订单状态（0：未支付 1：已支付）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;

}
