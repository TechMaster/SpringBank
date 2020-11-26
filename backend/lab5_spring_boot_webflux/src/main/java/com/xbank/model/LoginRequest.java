package com.xbank.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Login request model")
public class LoginRequest {

    @ApiModelProperty(value = "username", required = true)
    private String username;

    @ApiModelProperty(value = "password", required = true, position = 1)
    private String password;

}
