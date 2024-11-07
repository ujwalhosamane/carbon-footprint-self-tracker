package com.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserForgotPasswordDTO {
	private String email;
	private String currentPassword;
	private String newPassword;
}
