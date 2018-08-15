package SpacialDelivery;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {

    public static void main(String[] args) {

        SolarSystem solarSystem = new SolarSystem();
        ExecutorService threadPool = Executors.newCachedThreadPool();

        // Create 1 runnable for packager ("producer").
        threadPool.submit(new Packager(solarSystem, 1500));

        // Create 25 runnables for rockets.
        for (int i = 0; i < 20; i++) {
            Station location =
                    solarSystem.stations.get(ThreadLocalRandom.current()
                            .nextInt(2,  solarSystem.stations.size()));
            Rocket rocket = new Rocket("Rocket" + (i + 1), location);
            System.out.println(rocket.getName() + " stationed in "
                    + rocket.getLocation().getName());
            threadPool.submit(rocket);
        }

        for (int i = 0; i < 5; i++) {
            Station location =
                    solarSystem.stations.get(ThreadLocalRandom.current()
                            .nextInt(0, 2));
            XtremeHeatRocket xtremeHeatRocket =
                    new XtremeHeatRocket("XtremeRocket" + (i + 1), location);
            System.out.println(xtremeHeatRocket.getName() + " stationed in "
                    + xtremeHeatRocket.getLocation().getName());
            threadPool.submit(xtremeHeatRocket);
        }
        System.out.println("All rockets in stations, ready for another day of deliveries!");
    }
}
