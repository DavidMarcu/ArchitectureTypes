package com.dmarcu.layered.application;

import com.dmarcu.layered.application.commands.Command;
import com.dmarcu.layered.application.queries.Query;

public interface Bus {
    <R,C extends Command<R>> R executeCommand(C command);
    <R,Q extends Query<R>> R executeQuery(Q query);
}
