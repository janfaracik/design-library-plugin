package io.jenkins.plugins.designlibrary;

import jenkins.model.Jenkins;

import java.util.ArrayList;
import java.util.List;

public final class DesignLibrary {

    private static DesignLibrary instance;

    private DesignLibrary() {}

    public static DesignLibrary getInstance() {
        if (instance == null) {
            instance = new DesignLibrary();
        }

        return instance;
    }

    public List<Category> getCategories() {
        return new ArrayList<>(Jenkins.get().getExtensionList(Category.class));
    }

    public Category getCategory(String url) {
        return getCategories().stream()
                .filter(e -> e.getUrl().equals(url))
                .findFirst()
                .orElseThrow();
    }
}
