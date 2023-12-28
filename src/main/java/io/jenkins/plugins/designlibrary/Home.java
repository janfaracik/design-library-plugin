package io.jenkins.plugins.designlibrary;

import hudson.Extension;

@Extension
public class Home extends UISample {

    @Override
    public String getIcon() {
        return "symbol-home-outline plugin-ionicons-api";
    }

    @Override
    public Class<? extends Category> getCategory() {
        return HomeNew.class;
    }
}

