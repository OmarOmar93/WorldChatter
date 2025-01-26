package WorldChatterCore.Channels;

import WorldChatterCore.Others.debugMode;
import WorldChatterCore.Players.Player;

import java.util.List;

public final class Channel {

    private List<String> playerList;
    private List<String> placeList;
    private final String name;


    /**
     * A Channel shall be born
     * @param name Channel's name
     * @param placeList Channel's place list
     * @param playerList Channel's player list
     */
    public Channel(final String name, final List<String> placeList, final List<String> playerList) {
        this.name = name;
        this.playerList = playerList;
        this.placeList = placeList;
        debugMode.INSTANCE.println("Created new Channel \"" + name + "\", Places: " + placeList + " Players: " + playerList, debugMode.printType.INFO);
    }


    /**
     * Return the channel's player list
     * @return the {@link List}
     */
    public List<String> getPlayerList() {
        return playerList;
    }

    /**
     * Return the channel's place list
     * @return the {@link List}
     */
    public List<String> getPlaceList() {
        return placeList;
    }

    /**
     * Set the list of eligible list of players in the channel
     * @param playerList the player's list
     * @return the {@link Channel}
     */
    public Channel setPlayerList(final List<String> playerList) {
        this.playerList = playerList;
        return this;
    }

    /**
     * Add the player to the channel's player list
     * @param player the player to add
     * @return the {@link Channel}
     */
    public Channel addPlayer(final Player player) {
        this.playerList.add(player.getName());
        return this;
    }

    /**
     * Remove the player to the channel's player list
     * @param player the player to add
     * @return the {@link Channel}
     */
    public Channel removePlayer(final Player player) {
        this.playerList.remove(player.getName());
        return this;
    }

    /**
     * Add the place to the channel's place list
     * @param place the place to add
     * @return the {@link Channel}
     */
    public Channel addPlace(final String place) {
        this.placeList.add(place);
        return this;
    }

    /**
     * Remove the place to the channel's place list
     * @param place the place to add
     * @return the {@link Channel}
     */
    public Channel removePlace(final String place) {
        this.placeList.remove(place);
        return this;
    }

    /**
     * Set the list of eligible list of places in the channel
     * @param placeList the place's list
     * @return the {@link Channel}
     */
    public Channel setPlaceList(final List<String> placeList) {
        this.placeList = placeList;
        return this;
    }

    /**
     * Return the channel's name
     * @return the name based on {@link String}
     */
    public String getName() {
        return name;
    }
}