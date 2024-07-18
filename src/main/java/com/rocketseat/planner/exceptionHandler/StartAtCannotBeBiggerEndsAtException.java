package com.rocketseat.planner.exceptionHandler;

public class StartAtCannotBeBiggerEndsAtException extends RuntimeException{
    public StartAtCannotBeBiggerEndsAtException() {
        super("A data inicial da viagem não ser maior que a data final");
    }
}
