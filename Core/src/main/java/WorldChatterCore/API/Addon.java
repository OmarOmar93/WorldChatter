package WorldChatterCore.API;

public class Addon {
    private final String name, author, description, signature, version;

    /**
     *
     * @param name Name of Addon
     * @param author Addon's Author
     * @param description Description of the Addon
     * @param signature Addon's Signature
     */
    public Addon(final String name, final String author, final String description, final String signature, final String version) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.signature = signature;
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getSignature() {
        return signature;
    }

    public String getAuthor() {
        return author;
    }

    public String getVersion() {
        return version;
    }
}
