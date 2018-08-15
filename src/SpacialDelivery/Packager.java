package SpacialDelivery;

import java.util.concurrent.ThreadLocalRandom;

public class Packager implements Runnable {

    private SolarSystem solarSystem;
    private int amountOfParcels;

    Packager(SolarSystem solarSystem, int amountOfParcels) {
        this.solarSystem = solarSystem;
        this.amountOfParcels = amountOfParcels;
    }

    @Override
    public void run() {
        for (int i = 0; i < amountOfParcels; i++) {
            int weight = ThreadLocalRandom.current()
                    .nextInt(2, 80);
            int destinationIndex;
            int locationIndex = ThreadLocalRandom.current()
                    .nextInt(0, solarSystem.stations.size());
            if (solarSystem.stations.get(locationIndex).getName().equals("Mercury")) {
                destinationIndex = 1;
            } else if (solarSystem.stations.get(locationIndex).getName().equals("Venus")) {
                destinationIndex = 0;
            } else {
                destinationIndex = ThreadLocalRandom.current()
                        .nextInt(2, solarSystem.stations.size());
            }

            Parcel parcel = new Parcel(solarSystem.stations.get(locationIndex),
                    solarSystem.stations.get(destinationIndex),
                    weight, "Parcel" + (i + 1));

            solarSystem.stations.get(locationIndex).getPostOffice()
                    .addParcel(parcel);

            System.out.println(parcel.getName() + " was created and to be set on "
                    + parcel.getOrigination().getName() + ", for delivery to "
                    + parcel.getDestination().getName() + ". There are currently " +
                    parcel.getOrigination().getPostOffice().getParcelsSize() +
                    " parcels in that station.");

        }
    }
}
