package com.dmarcu.layered.application.commands.user;

import lombok.Data;

@Data
public class LoginUserResult {
    private String authorizationHeader;
}
