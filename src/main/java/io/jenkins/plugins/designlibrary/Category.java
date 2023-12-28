package io.jenkins.plugins.designlibrary;

import hudson.ExtensionPoint;
import jenkins.model.Jenkins;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Category implements ExtensionPoint {

    public abstract String getIcon();

    public abstract String getDisplayName();

    public abstract String getUrl();

    public int getOrder() {
        return -1;
    }

    public List<UISample> getItems() {
        return new ArrayList<>(Jenkins.get().getExtensionList(UISample.class)).stream()
                .filter(e -> e.getCategory().equals(this.getClass()))
                .collect(Collectors.toList());
    }

    public UISample getCategory(String url) {
        return getItems().stream()
                .filter(e -> e.getUrlName().equals(url))
                .findFirst()
                .orElseThrow();
    }
}
