package ru.croc.task16;

import ru.croc.task16.entity.Location;
import ru.croc.task16.entity.Request;
import ru.croc.task16.entity.TaxiDriver;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class TaxiSearcher {

    public String processRequest(String requestLocation, String requestComfortClass, String requestSpecialRequests) throws IOException, IllegalArgumentException {
        Location location = new Location(requestLocation);
        ComfortClass comfortClass = ComfortClass.getComfortClass(requestComfortClass.trim());
        EnumSet<SpecialRequest> specialRequests = EnumSet.noneOf(SpecialRequest.class);
        for (String specialRequest : requestSpecialRequests.split(",")) {
            specialRequests.add(SpecialRequest.getSpecialRequest(specialRequest.toLowerCase().trim()));
        }

        Request request = new Request(location, comfortClass, specialRequests);

        TaxiDriver driver = findMostSuitableDriver(request);
        return driver.getId();
    }

    private TaxiDriver findMostSuitableDriver(Request request) throws IOException {
        DriversDataReader dataReader = new DriversDataReader("src/ru/croc/task16/drivers.csv");
        List<TaxiDriver> drivers = dataReader.read();

        // сортировка по приоритетности - класс комфорта, особые пожелания, расстояние
        drivers.sort((d1, d2) -> {
            if (d1.getComfortClass().equals(d2.getComfortClass())) {
                int sameRequests1 = d1.getSpecialRequests().stream().filter(
                        specialRequest -> request.getSpecialRequests().contains(specialRequest)).collect(Collectors.toSet()).size();
                int sameRequests2 = d2.getSpecialRequests().stream().filter(
                        specialRequest -> request.getSpecialRequests().contains(specialRequest)).collect(Collectors.toSet()).size();
                if (sameRequests1 == sameRequests2) {
                    double distance1 = d1.getLocation().distance(request.getLocation());
                    double distance2 = d2.getLocation().distance(request.getLocation());
                    return Double.compare(distance1, distance2);
                } else {
                    return Integer.compare(-sameRequests1, -sameRequests2);
                }
            }
            float comfortClassSimilarity1 = Math.abs(d1.getComfortClass().getIndex() - request.getComfortClass().getIndex());
            float comfortClassSimilarity2 = Math.abs(d2.getComfortClass().getIndex() - request.getComfortClass().getIndex());
            return Float.compare(comfortClassSimilarity1, comfortClassSimilarity2);
        });

        return drivers.get(0);
    }
}

