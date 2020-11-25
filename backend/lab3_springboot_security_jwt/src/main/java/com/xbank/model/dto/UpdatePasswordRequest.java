package com.xbank.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "Update password Request", description = "The update password request payload")
public class UpdatePasswordRequest {

    @NotBlank(message = "Old password must not be blank")
    @ApiModelProperty(value = "Valid current user password", required = true, allowableValues = "NonEmpty String")
    private String oldPassword;

    @NotBlank(message = "New password must not be blank")
    @ApiModelProperty(value = "Valid new password string", required = true, allowableValues = "NonEmpty String")
    private String newPassword;

}
