package WorldChatterCore.Features;

import WorldChatterCore.Systems.ConfigSystem;

public class PlaceBlacklist {

    public static PlaceBlacklist INSTANCE;

    public PlaceBlacklist() {
        INSTANCE = this;
    }


    public boolean isPlaceBlackListed(final String place) {
        return ConfigSystem.INSTANCE.getPlace().getStringList("BlackList.places").contains(place);
    }


}
