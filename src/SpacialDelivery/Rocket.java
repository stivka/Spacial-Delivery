package SpacialDelivery;

import java.util.ArrayList;
import java.util.Arrays;

public class Rocket implements Runnable {

    private String name;
    private PostOffice postOffice;
    private Station location;
    private Station destination;
    private int fuel = 100;
    private ArrayList<String> unreachableStations = new ArrayList<>(Arrays.asList("Mercury", "Venus"));
    private ArrayList<Parcel> parcel;
    private int refuelTimes;

    Rocket(String name, Station location) {
        this.name = name;
        this.location = location;
        this.destination = location;
        this.postOffice = location.getPostOffice();
        this.parcel = new ArrayList<>();
    }

    String getName() {
        return this.name;
    }

    Station getLocation() {
        return this.location;
    }

    ArrayList<String> getUnreachableStations() {
        return unreachableStations;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                unload();
                load();
                setDestination();
                travel();
                waitForService();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void waitForService() throws InterruptedException {
        synchronized (postOffice) {
            while (postOffice.getRocket() != null
                    && !Thread.currentThread().isInterrupted()) {
                System.out.println("Post station " + location.getName()
                        + " is currently serving another rocket. "
                        + name + " is waiting in queue.");
                postOffice.wait();
            }
            System.out.println("Post station " + location.getName() + " takes in  " +
                    name);
            postOffice.setRocket(this);
        }
    }

    private void travel() throws InterruptedException {
        System.out.println(name + " is traveling.");
        postOffice.setRocket(null);
        this.fuel -= this.getFuelNeeded();
        this.location = null;
        this.postOffice = null;
        Thread.sleep(15);
        this.location = destination;
        this.postOffice = this.location.getPostOffice();
        System.out.println(name + " arrived on planet " + location.getName());
    }

    public int getFuelNeeded() {
        return 20;
    }

    private void setDestination() {
        System.out.println(name + " setting course from " + location.getName() + " to "
                + parcel.get(0).getDestination().getName() + ".");
        this.fuel = 100;
        System.out.println("Rocket refueled.");
        this.refuelTimes++;
        if (parcel.size() > 0 && destination != location) {
            this.destination = parcel.get(0).getDestination();
            System.out.println(name + " is set to go to " + destination.getName());
        } else if (parcel.size() == 0) {
            System.out.println("There is no parcel on " + name);
        }
    }

    private void load() throws InterruptedException {
        System.out.println("Loading parcel on " + name);
        /*
        Grabs parcels from that post office, which have not been delivered there,
        but need to be delivered.
         */
        parcel.add(postOffice.getParcel(parcel1 -> parcel1.getDestination() != location));
    }

    private void unload() {
        if (parcel.size() > 0) {
            Parcel unloadedParcel = parcel.remove(0);
            unloadedParcel.setLocation(this.location);
            postOffice.addParcel(unloadedParcel);
            System.out.println(name + " delivered parcel to "
                    + unloadedParcel.getDestination().getName() + ".");
            postOffice.toString();
        } else {
            System.out.println("There is no parcel to unload on " + name);
        }
    }
}
