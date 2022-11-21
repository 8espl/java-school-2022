package ru.croc.task10;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Auction {
    public static void main(String[] args) {
        LocalDateTime auctionEndTime = LocalDateTime.now().plus(15, ChronoUnit.SECONDS);
        int usersNumber = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(usersNumber);
        AuctionLot auctionLot = new AuctionLot(0, auctionEndTime);

        // создаем имитацию участников
        for (int i = 0; i < usersNumber; ++i) {
            executorService.submit(new Bidder(String.valueOf(i), auctionLot));
        }
        executorService.shutdown();

        // ждем, пока участники завершат торги
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            ;
        }

        try {
            System.out.println("The winner is user #" + auctionLot.getWinner(LocalDateTime.now()) + "!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
