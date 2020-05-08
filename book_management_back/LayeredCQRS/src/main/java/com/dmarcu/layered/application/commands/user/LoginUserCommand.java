package com.dmarcu.layered.application.commands.user;

import com.dmarcu.layered.application.commands.Command;
import lombok.Data;

@Data
public class LoginUserCommand implements Command<LoginUserResult> {
    private String username;
    private String password;
}
