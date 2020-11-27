package com.xbank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(description = "Login Response model")
public class LoginResponse {

    @ApiModelProperty(value = "accessToken")
    @JsonProperty("access_token")
    private String accessToken;

    @ApiModelProperty(value = "type")
    private String type;

    @ApiModelProperty(value = "expiresIn")
    @JsonProperty("expires_in")
    private Long expiresIn;

    //TODO
}
