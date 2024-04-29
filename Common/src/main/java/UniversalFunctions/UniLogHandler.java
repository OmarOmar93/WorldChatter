package UniversalFunctions;

public final class UniLogHandler {

    private final UniLog log;
    public static UniLogHandler INSTANCE;

    public UniLogHandler(final UniLog log) {
        INSTANCE = this;
        this.log = log;
    }

    public void sendMessage(final String message) {
        log.sendmessage(message);
    }
}
