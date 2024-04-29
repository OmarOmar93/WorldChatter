package UniversalFunctions;

import java.util.List;

public final class ChatEvent {

    private boolean cancelled;
    private final boolean isProxy, papi;
    private final Player player;
    private String format, message;
    private final List<Player> recipients;
    private final Object originEvent;

    public ChatEvent(final Object originEvent, final boolean papi , final boolean isProxy, final Player player, final String format, final String message, final List<Player> recipients) {
        cancelled = false;
        this.isProxy = isProxy;
        this.papi = papi;

        this.player = player;
        this.originEvent = originEvent;

        this.format = format;
        this.message = message;

        this.recipients = recipients;
    }

    public boolean isProxy() {
        return isProxy;
    }

    public boolean PAPI(){
        return papi;
    }

    public Object getOriginEvent(){
        return originEvent;
    }

    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void setFormat(final String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public Player getPlayer() {
        return player;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public List<Player> getRecipients() {
        return recipients;
    }
}
