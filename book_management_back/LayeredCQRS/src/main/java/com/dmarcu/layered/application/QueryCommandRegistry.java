package com.dmarcu.layered.application;

import com.dmarcu.layered.application.commands.Command;
import com.dmarcu.layered.application.commands.CommandHandler;
import com.dmarcu.layered.application.queries.Query;
import com.dmarcu.layered.application.queries.QueryHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@SuppressWarnings("unchecked")
public class QueryCommandRegistry {

    private final Map<Class<? extends Query<?>>, QueryHandler<?, ?>> queryHandlerMap;
    private final Map<Class<? extends Command<?>>, CommandHandler<?, ?>> commandHandlerMap;

    public QueryCommandRegistry(ApplicationContext applicationContext) {
        queryHandlerMap = new HashMap<>();
        commandHandlerMap = new HashMap<>();
        String[] classNames = applicationContext.getBeanNamesForType(QueryHandler.class);
        for(String className : classNames) {
            registerQuery(applicationContext, className);
        }
        classNames = applicationContext.getBeanNamesForType(CommandHandler.class);
        for(String className : classNames) {
            registerCommand(applicationContext, className);
        }
    }

    private void registerQuery(ApplicationContext applicationContext, String queryHandlerName) {
        Class<QueryHandler<?,?>> handlerClass = (Class<QueryHandler<?,?>>) applicationContext.getType(queryHandlerName);
        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass, QueryHandler.class);
        assert generics != null && handlerClass != null;
        Class<? extends Query<?>> queryType = (Class<? extends Query<?>>) generics[1];
        queryHandlerMap.put(queryType, applicationContext.getBean(handlerClass));
    }

    private void registerCommand(ApplicationContext applicationContext, String commandHandlerName) {
        Class<CommandHandler<?,?>> handlerClass = (Class<CommandHandler<?,?>>) applicationContext.getType(commandHandlerName);
        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass, CommandHandler.class);
        assert generics != null && handlerClass != null;
        Class<? extends Command<?>> commandType = (Class<? extends Command<?>>) generics[1];
        commandHandlerMap.put(commandType, applicationContext.getBean(handlerClass));
    }

    QueryHandler<?, ?> getQueryHandler(Class<?> queryClass) {
        return queryHandlerMap.get(queryClass);
    }

    CommandHandler<?, ?> getCommandHandler(Class<?> commandClass) {
        return commandHandlerMap.get(commandClass);
    }


}
