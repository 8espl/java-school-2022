package ru.croc.task16;

import ru.croc.task16.entities.Location;
import ru.croc.task16.entities.TaxiDriver;
import ru.croc.task16.entities.User;

import java.util.*;
import java.util.function.Predicate;

public class FindTaxiDriver {
    private static final Set<String> carType = new HashSet<>(List.of("эконом", "стандарт", "комфорт"));
    private static final Set<String> specialRequests = new HashSet<>(List.of("детское кресло", "с питомцем", "по платной дороге", "подходит для людей с ограниченными возможностями"));
    private static List<TaxiDriver> drivers = new ArrayList<>();

    public static void main(String[] args) {
        drivers.add(new TaxiDriver("U-SnezhanaDenisovna-79995553535", new Location(59.9370, 30.3156),
                "комфорт", new HashSet<>(List.of("детское кресло"))));
        drivers.add(new TaxiDriver("U-NikolayIvanov-79995554560", new Location(59.8349, 31.4876),
                "эконом", new HashSet<>(List.of("детское кресло", "с питомцем"))));
        drivers.add(new TaxiDriver("U-EvgenyDenisov-79987824545", new Location(58.0812, 32.6790),
                "эконом", new HashSet<>(List.of("по платной дороге", "с питомцем"))));
        drivers.add(new TaxiDriver("U-OlgaDobronravova-79114320512", new Location(57.1206, 32.6754),
                "стандарт", new HashSet<>(List.of("по платной дороге", "подходит для людей с ограниченными возможностями", "детское кресло"))));

        Scanner in = new Scanner(System.in);
        System.out.println("Чтобы найти машину, введите свое местоположение. <Широта>, <долгота>: ");
        String location = in.nextLine();

        // обработка ввода класса комфорта
        System.out.println("Какой класс комфорта Вы предпочитаете? " + carType);
        String carType = in.nextLine().toLowerCase();
        try {
            if (!FindTaxiDriver.carType.contains(carType.trim())) {
                throw new Exception("Неправильно введен класс комфорта!");
            }
        } catch (Exception wrongType) {
            System.out.println(wrongType.getMessage());
        }

        // обрвботка ввода особых пожеланий
        System.out.println("Если у Вас есть особые пожелания, перечислите их через запятую. Доступные: " + specialRequests.toString());
        List<String> requests = new ArrayList<>(List.of(in.nextLine().toLowerCase().split(",")));
        requests.replaceAll(String::trim);
        try {
            requests.forEach(request -> {
                        if (!specialRequests.contains(request)) {
                            try {
                                throw new Exception("Неправильно введены особые пожелания!");
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
            );
        } catch (Exception wrongRequest){
            System.out.println(wrongRequest.getMessage());
        }

        // пользователь, для которого ищем водителя
        User user = new User(location, carType, requests);

        try {
            System.out.println("Ваш водитель: " + findCar(user).toString());
        } catch (Exception noDriver) {
            System.out.println(noDriver.getMessage());
        }
    }

    public static TaxiDriver findCar(User user) throws Exception {
        List<TaxiDriver> suitableDrivers = new ArrayList<>();

        // функция, определяющая, все ли особые пожелания водитель может выполнить
        Predicate<HashSet<String>> hasNeededRequests = requests -> {
            for (String userRequest : user.getRequests()) {
                if (!requests.contains(userRequest.toLowerCase())) return false;
            }
            return true;
        };

        // если удовлетворяет нашим запросам, то водитель подходит
        drivers.forEach(driver -> {
            if (driver.getCarType().equals(user.getCarType()) && hasNeededRequests.test(driver.getSpecialRequests())) {
                suitableDrivers.add(driver);
            }
        });

        // ищем самого ближайшего водителя
        if (!drivers.isEmpty()) {
            if (suitableDrivers.size() > 1) {
                suitableDrivers.sort(Comparator.comparingDouble(d -> d.getLocation().distance(user.getLocation())));
            }
            return suitableDrivers.get(0);
        } else {
            throw new Exception("К сожалению, подходящих водителей сейчас нет. Измените свои пожелания и попробуйте снова или подождите.");
        }
    }
}
