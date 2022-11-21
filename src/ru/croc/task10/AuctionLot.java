package ru.croc.task10;

import java.time.LocalDateTime;

public class AuctionLot {
    private volatile int price;
    private volatile String name = "";
    private LocalDateTime endTime;
    private Object lock = new Object();

    public AuctionLot(int startingPrice, LocalDateTime endTime) {
        this.price = startingPrice;
        this.endTime = endTime;
    }

    public void makeABid(int bidPrice, String userName) {
        if (LocalDateTime.now().isBefore(endTime)) {
            synchronized (lock) {
                if (bidPrice > this.price) {
                    this.price = bidPrice;
                    this.name = userName;
                    System.out.println("New highest bid = " + price + " by user #" + name);
                }
            }
        }

    }

    public String getWinner(LocalDateTime time) throws Exception {
        if (time.isAfter(endTime)) {
            return this.name;
        } else {
            throw new Exception("An auction has not finished yet");
        }
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
