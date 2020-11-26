package com.xbank.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(description = "User")
public class User {

    @ApiModelProperty(value = "id User", position = 0)
    private String id;

    @ApiModelProperty(value = "Username", position = 1)
    private String username;

    @ApiModelProperty(value = "password", position = 2)
    private String password;

    @ApiModelProperty(value = "email", position = 3)
    private String email;

}
