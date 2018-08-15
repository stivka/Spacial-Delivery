package SpacialDelivery;

public class Parcel {

    private String name;
    private Station origination;
    private Station destination;
    private Station location;
    private int weight;

    Parcel(Station location, Station destination, int weight, String name) {
        this.origination = location;
        this.destination = destination;
        this.weight = weight;
        this.name = name;
    }

    void setLocation(Station location) {
        this.location = location;
    }

    public Station getOrigination() {
        return this.origination;
    }

    public Station getDestination() {
        return this.destination;
    }

    public String getName() {
        return this.name;
    }

    public int getWeight() {
        return weight;
    }
}
