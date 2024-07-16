package com.rocketseat.planner.controller;

import java.util.Random;
import java.util.concurrent.*;

public class Teste {


    public static int getValor() {
        Integer i = new Random().nextInt(1000);
        System.out.println("valor original = " + i);
        return i;
    }

    public static void main(String... args) throws InterruptedException {
        CompletableFuture
                .supplyAsync(()->getValor())
                .thenApply(i -> String.valueOf(i))
                .exceptionally(ex -> {
                    System.out.println("Erro = " + ex.getMessage());
                    return "Erro";
                }).thenAccept(str -> System.out.println("String = " + str));
        Thread.sleep(1000);

    }
}
