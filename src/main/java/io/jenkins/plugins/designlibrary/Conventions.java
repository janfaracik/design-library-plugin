package io.jenkins.plugins.designlibrary;

import hudson.Extension;

@Extension
public class Conventions extends UISample {
    @Override
    public String getIconFileName() {
        return "symbol-school-outline plugin-ionicons-api";
    }

    @Override
    public Class<? extends Category> getCategory() {
        return Patterns.class;
    }
}

