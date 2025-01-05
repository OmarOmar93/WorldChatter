package WorldChatterCore.Channels;

import WorldChatterCore.Others.debugMode;
import WorldChatterCore.Players.Player;

import java.util.List;

public final class Channel {

    private List<String> playerList;
    private List<String> placeList;
    private final String name;


    public Channel(final String name, final List<String> placeList, final List<String> playerList) {
        this.name = name;
        this.playerList = playerList;
        this.placeList = placeList;
        debugMode.INSTANCE.println("Created new Channel \"" + name + "\", Places: " + placeList + " Players: " + playerList, debugMode.printType.INFO);
    }


    public List<String> getPlayerList() {
        return playerList;
    }

    public List<String> getPlaceList() {
        return placeList;
    }

    public Channel setPlayerList(final List<String> playerList) {
        this.playerList = playerList;
        return this;
    }

    public Channel addPlayer(final Player player) {
        this.playerList.add(player.getName());
        return this;
    }

    public Channel removePlayer(final Player player) {
        this.playerList.remove(player.getName());
        return this;
    }

    public Channel addPlace(final String place) {
        this.placeList.add(place);
        return this;
    }

    public Channel removePlace(final String place) {
        this.placeList.remove(place);
        return this;
    }

    public Channel setPlaceList(final List<String> placeList) {
        this.placeList = placeList;
        return this;
    }

    public String getName() {
        return name;
    }
}