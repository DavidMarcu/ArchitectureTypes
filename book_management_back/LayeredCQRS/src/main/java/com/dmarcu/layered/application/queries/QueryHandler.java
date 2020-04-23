package com.dmarcu.layered.application.queries;

public interface QueryHandler<R, Q extends Query<R>> {
    R handle(Q query);
}
