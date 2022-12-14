package ru.croc.task16;

import ru.croc.task16.entity.Location;
import ru.croc.task16.entity.TaxiDriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DriversDataReader {
    private String source;

    public DriversDataReader(String source) {
        this.source = source;
    }

    public List<TaxiDriver> read() throws IOException {
        List<TaxiDriver> drivers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            String line;
            String[] info;

            while ((line = reader.readLine()) != null) {
                info = line.split(",");
                EnumSet<SpecialRequest> specialRequests = EnumSet.noneOf(SpecialRequest.class);
                for (String request : info[4].split(";")) {
                    specialRequests.add(SpecialRequest.getSpecialRequest(request));
                }

                Location location = new Location(Double.parseDouble(info[1]), Double.parseDouble(info[2]));
                ComfortClass comfortClass = ComfortClass.getComfortClass(info[3]);
                TaxiDriver driver = new TaxiDriver(info[0], location, comfortClass, specialRequests);
                drivers.add(driver);
            }
        }

        return drivers;
    }
}
