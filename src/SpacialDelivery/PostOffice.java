package SpacialDelivery;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

public class PostOffice {
    /*
     */
    private final ArrayList<Parcel> parcels = new ArrayList<>();

    private Rocket rocket;
    private Station station;

    synchronized void setRocket(Rocket rocket) {
        this.rocket = rocket;
        if (rocket == null) {
            this.notifyAll();
            System.out.println("Post station " + station.getName() + " is ready to serve " +
                    "the next rocket!");
        } else {
            System.out.println("Post station " + station.getName() + " finished serving "
                    + rocket.getName() + ".");
        }
    }

    Rocket getRocket() {
        return rocket;
    }

    int getParcelsSize() {
        int parcelsSize;
        synchronized (parcels) {
            parcelsSize = parcels.size();
            parcels.notifyAll();
        }
        return parcelsSize;
    }

    void addParcel(Parcel parcel) {
        synchronized (parcels) {
            parcels.add(parcel);
            parcels.notifyAll();
        }
    }

    Parcel getParcel(Predicate<Parcel> predicate) throws InterruptedException {
        synchronized (parcels) {
            Optional<Parcel> parcel = Optional.empty();
            while (!parcel.isPresent()) {
                parcel = parcels.stream().filter(predicate).findFirst();
                if (!parcel.isPresent()) {
                    System.out.println("There are " + parcels.size() + " parcels in " +
                            " the station, waiting for parcels..");
                    parcels.wait();
                }
            }
            parcels.remove(parcel.get());
            System.out.println("Post station " + station.getName() + " handed over "
                    + parcel.get().getName() + ". There are " + parcels.size()
                    + " parcels in the station.");
            return parcel.get();
        }
    }

    @Override
    public String toString() {
        int parcelSumWeight = 0;
        ArrayList<String> originationList = new ArrayList<>();
        synchronized (parcels) {
            for (Parcel parcel : parcels) {
                /* for getting all different locations from which parcels have been
                delivered.
                 */
                if (!originationList.contains(parcel.getOrigination().getName())) {
                    originationList.add(parcel.getOrigination().getName());
                }
                parcelSumWeight += parcel.getWeight();
            }
        }
        return "Post station " + station.toString() + " currently holds " + parcels.size()
                + " parcels, with a total weight of " + parcelSumWeight
                + "and average weight of " + parcelSumWeight / parcels.size()
                + " . Parcels were sent from " + originationList.size() + " different " +
                "planets. " + ((rocket != null) ? " Serves " + rocket.getName() : " Serves" +
                " no rocket");
    }

    void setStation(Station station) {
        this.station = station;
    }

    Station getStation() {
        return station;
    }
}
