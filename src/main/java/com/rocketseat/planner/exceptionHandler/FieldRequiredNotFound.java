package com.rocketseat.planner.exceptionHandler;

public class FieldRequiredNotFound extends RuntimeException{

    public FieldRequiredNotFound(String field) {
        super("O campo " + field + " não pode ser nulo.");
    }
}
