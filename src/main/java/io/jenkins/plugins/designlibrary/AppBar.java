package io.jenkins.plugins.designlibrary;

import hudson.Extension;

@Extension
public class AppBar extends UISample {

    @Override
    public String getDisplayName() {
        return "App bars";
    }

    @Override
    public String getIcon() {
        return "symbol-app-bar";
    }
}
