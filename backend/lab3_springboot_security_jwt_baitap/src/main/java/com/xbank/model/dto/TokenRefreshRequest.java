package com.xbank.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "Token refresh Request", description = "The jwt token refresh request payload")
public class TokenRefreshRequest {

    @NotBlank(message = "Refresh token cannot be blank")
    @ApiModelProperty(value = "Valid refresh token passed during earlier successful authentications", required = true,
            allowableValues = "NonEmpty String")
    private String refreshToken;

}
