package com.XZY.mallchat.common.user.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Stack;

/**
 * Description:
 * Author:戏中言
 *
 * @date: 2024/4/2 15:07
 */
@Data
public class ModifyName {
    @ApiModelProperty("用户名")
    @NotBlank
    @Length(max =  6,message = "用户名不可以去太长，不然记不住")
    private String name;



}
