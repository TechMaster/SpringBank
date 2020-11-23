package com.xbank.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "Password reset link request", description = "The password reset link payload")
public class PasswordResetLinkRequest {

    @NotBlank(message = "Email cannot be blank")
    @ApiModelProperty(value = "User registered email", required = true, allowableValues = "NonEmpty String")
    private String email;
}
