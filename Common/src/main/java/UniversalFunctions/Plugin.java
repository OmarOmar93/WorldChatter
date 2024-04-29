package UniversalFunctions;

import java.util.List;

public final class Plugin {

    private final String name, authors, description;

    public Plugin(final String name, final String description, final List<String> authors) {
        this.name = name;
        this.description = description;
        this.authors = String.join(", ", authors);

    }

    public String getName() {
        return name;
    }

    public String getAuthors() {
        return authors;
    }

    public String getDescription() {
        return description;
    }
}
