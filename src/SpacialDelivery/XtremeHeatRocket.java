package SpacialDelivery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class XtremeHeatRocket extends Rocket {

    XtremeHeatRocket(String name, Station location) {
        super(name, location);
    }

    @Override
    public int getFuelNeeded() {
        if (Arrays.asList("Mercury", "Venus").contains(getLocation().getName())) {
            return 50;
        }
        return 25;
    }
}
