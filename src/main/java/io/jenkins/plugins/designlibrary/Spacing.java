package io.jenkins.plugins.designlibrary;

import hudson.Extension;

@Extension
public class Spacing extends UISample {

    @Override
    public String getIcon() {
        return "symbol-filter-outline plugin-ionicons-api";
    }

    @Override
    public Class<? extends Category> getCategory() {
        return Patterns.class;
    }
}
