package ru.croc.task9;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PasswordCracking {
    public static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public static void main(String[] args) {

        int threadsNumber = Integer.parseInt(args[0]);
        String hash = args[1];

        //String hash = hashPassword("dog");
        //int threadsNumber = alphabet.length;
        int passwordLength = 3;

        int lengthSet = alphabet.length / threadsNumber; // размер области алфавита, в которой каждый поток ищет пароль

        // если количество потоков превышает размерность алфавита, то устанавливаем дефолтные значения
        if (lengthSet == 0) {
            threadsNumber = alphabet.length;
            lengthSet = 1;
            System.out.println("Threads number were decreased to the length of the alphabet.");
        }

        int end = 0;
        try{
            ExecutorService executorService = Executors.newFixedThreadPool(threadsNumber);
            // для каждой области алфавита создаем свой поток
            while (end < alphabet.length) {
                int start = end;
                end = start + lengthSet;
                if (end > alphabet.length) {
                    end = alphabet.length;
                }
                executorService.submit(new PasswordCrackerRunnable(hash, passwordLength, start, end));
            }
            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
