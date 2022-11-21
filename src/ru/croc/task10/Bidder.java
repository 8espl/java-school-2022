package ru.croc.task10;

import java.time.LocalDateTime;

public class Bidder implements Runnable {
    private String name;
    private AuctionLot lot;

    public Bidder(String userName, AuctionLot auctionLot) {
        this.name = userName;
        this.lot = auctionLot;
    }

    @Override
    public void run() {
        while (LocalDateTime.now().isBefore(lot.getEndTime())) {
            lot.makeABid((int) (Math.random() * 10000), name);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
