package com.dmarcu.layered.application;

import com.dmarcu.layered.application.commands.Command;
import com.dmarcu.layered.application.commands.CommandHandler;
import com.dmarcu.layered.application.queries.Query;
import com.dmarcu.layered.application.queries.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class BusImpl implements Bus {

    private final QueryCommandRegistry registry;

    public BusImpl(QueryCommandRegistry queryCommandRegistry) {
        this.registry = queryCommandRegistry;
    }
    @Override
    public <R, C extends Command<R>> R executeCommand(C command) {
        CommandHandler<R, C> commandHandler = (CommandHandler<R, C>) registry.getCommandHandler(command.getClass());
        return commandHandler.handle(command);
    }

    @Override
    public <R, Q extends Query<R>> R executeQuery(Q query) {
        QueryHandler<R, Q> queryHandler = (QueryHandler<R, Q>) registry.getQueryHandler(query.getClass());
        return queryHandler.handle(query);
    }
}
