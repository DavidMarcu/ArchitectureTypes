package com.dmarcu.layered.application.commands;

public interface CommandHandler<R, C extends Command<R>> {
    R handle(C command);
}
