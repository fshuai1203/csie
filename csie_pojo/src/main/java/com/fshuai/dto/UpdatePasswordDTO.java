package com.fshuai.dto;

import lombok.Data;

@Data
public class UpdatePasswordDTO {

    private String oldPassword;
    private String newPassword;
}
