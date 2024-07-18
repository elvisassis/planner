package com.rocketseat.planner.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Teste {


    public class CafeteriaSync {

        public void fazerPedido(String pedido, String atendente) throws InterruptedException {
            System.out.println("O atendente " + atendente + " está preparando o seu " + pedido);

            // O sleep substitui o processo de preparação do pedido.
            Thread.sleep(1000);

            System.out.println("O atendente " + atendente + " finalizou o seu " + pedido);
        }
    }

    public static void main(String... args) throws InterruptedException, ExecutionException {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6 , 7, 8, 9, 10);

        System.out.println("Number of cores available: "+ Runtime.getRuntime().availableProcessors());

        ForkJoinPool pool = new ForkJoinPool(4);
        pool.submit(() -> numbers.parallelStream().forEach(n -> System.out.println("Thread: " + Thread.currentThread().getName() + " - Numero: " + n))).get();

    }
}
