package io.jenkins.plugins.designlibrary;

import hudson.ExtensionPoint;
import hudson.model.Describable;
import jenkins.model.Jenkins;

import java.util.List;

public abstract class Category implements ExtensionPoint, Describable<Category> {

    public abstract String getIcon();

    public abstract String getDisplayName();

    public abstract String getUrl();

    public List<UISample> getItems() {
        return List.of(
                new Buttons()
        );
    }

    public UISample getDynamic(String url) {
        System.out.println("Name 3 is");
        System.out.println(url);
        return getItems().stream()
                .filter(e -> e.getUrlName().equals(url))
                .findFirst()
                .orElseThrow();
    }

    public CategoryDescriptor getDescriptor() {
        return (CategoryDescriptor) Jenkins.get().getDescriptorOrDie(getClass());
    }
}
