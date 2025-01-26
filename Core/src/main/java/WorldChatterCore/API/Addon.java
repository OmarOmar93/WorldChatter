package WorldChatterCore.API;

public final class Addon {
    private final String name, author, description, signature, version, updater;
    private final Integer build;

    /**
     *
     * @param name Name of Addon
     * @param author Addon's Author
     * @param description Description of the Addon
     * @param signature Addon's Signature
     * @param version Addon's version
     */
    public Addon(final String name, final String author, final String description, final String signature, final String version) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.signature = signature;
        this.version = version;
        this.updater = null;
        this.build = null;
    }

    /**
     *
     * @param name Name of Addon
     * @param author Addon's Author
     * @param description Description of the Addon
     * @param signature Addon's Signature
     * @param updater Addon's Update URL
     * @param build Addon's build
     * @param version Addon's version
     */
    public Addon(final String name, final String author, final String description, final String signature, final String version, final String updater, final Integer build) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.signature = signature;
        this.version = version;
        this.updater = updater;
        this.build = build;
    }

    /**
     * @return returns the addon's version build
     */
    public Integer getBuild() {
        return build;
    }

    /**
     * @return returns the addon's update link
     */
    public String getUpdater() {
        return updater;
    }

    /**
     * @return returns the addon's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return returns the addon's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return returns the addon's signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @return returns the addon's author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return returns the addon's version
     */
    public String getVersion() {
        return version;
    }
}
