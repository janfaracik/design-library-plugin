package io.jenkins.plugins.designlibrary;

public enum PageCategory {
    PATTERN("IDK"),
    COMPONENT("Components");

    PageCategory(String displayName) {
        this.displayName = displayName;
    }

    private final String displayName;

    public String getDisplayName() {
        return displayName;
    }
}
