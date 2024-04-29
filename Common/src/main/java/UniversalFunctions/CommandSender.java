package UniversalFunctions;



public interface CommandSender {

    boolean isConsole();
    void sendMessage(final String message);
    boolean hasPermission(final String permission);

    String getName();

}
