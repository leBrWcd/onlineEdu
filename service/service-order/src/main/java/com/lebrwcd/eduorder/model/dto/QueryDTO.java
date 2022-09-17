package com.lebrwcd.eduorder.model.dto;/**
 * @author lebrwcd
 * @date 2022/9/17
 * @note
 */

/**
 * ClassName QueryDTO
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/9/17
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName QueryDTO
 * Description Banner 查询对象
 *
 * @author lebr7wcd
 * @version 1
 * @date 2022/9/17
 */
@Data
@ApiModel("QueryDTO")
public class QueryDTO {

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单状态（0：未支付 1：已支付）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;

}

