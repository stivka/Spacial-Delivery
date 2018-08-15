package SpacialDelivery;

import java.util.ArrayList;

class SolarSystem {

    ArrayList<Station> stations = new ArrayList<>();

    SolarSystem() {
        String[] allStations = {"Mercury", "Venus","Earth", "Moon", "Mars",
                "Jupiter", "Io", "Saturn", "Uranus", "Neptune", "Pluto"};
        for (String stationName : allStations) {
            stations.add(new Station(stationName));
        }
    }

    ArrayList<Station> getStations() {
        return stations;
    }
}
