package io.jenkins.plugins.designlibrary;

import hudson.Extension;

@Extension
public class Patterns extends Category {

    @Override
    public String getIcon() {
        return "symbol-close";
    }

    @Override
    public String getDisplayName() {
        return "Patterns";
    }

    @Override
    public String getUrl() {
        return "patterns";
    }
}
