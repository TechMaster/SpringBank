package com.xbank.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDTO {
    private String fullName;

    private String email;

    private String mobile;

    private String photo;
}
