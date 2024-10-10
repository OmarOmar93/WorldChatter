package WorldChatterCore.API;

public final class Addon {
    private final String name, author, description, signature, version, updater;
    private final int build;

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
        this.updater = null;
        this.build = -1;
    }

    /**
     *
     * @param name Name of Addon
     * @param author Addon's Author
     * @param description Description of the Addon
     * @param signature Addon's Signature
     * @param updater Addon's Update URL
     * @param build Addon's build
     */
    public Addon(final String name, final String author, final String description, final String signature, final String version, final String updater, final int build) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.signature = signature;
        this.version = version;
        this.updater = updater;
        this.build = build;
    }

    public int getBuild() {
        return build;
    }

    public String getUpdater() {
        return updater;
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
