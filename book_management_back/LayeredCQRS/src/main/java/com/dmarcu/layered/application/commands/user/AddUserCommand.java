package com.dmarcu.layered.application.commands.user;

import com.dmarcu.layered.application.commands.Command;
import lombok.Data;

@Data
public class AddUserCommand implements Command<AddUserResult> {
    private String username;
    private String email;
    private String password;
}
