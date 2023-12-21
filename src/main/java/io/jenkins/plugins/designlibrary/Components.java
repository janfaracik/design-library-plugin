package io.jenkins.plugins.designlibrary;

import hudson.Extension;

@Extension
public class Components extends Category {

    @Override
    public String getIcon() {
        return "symbol-star";
    }

    @Override
    public String getDisplayName() {
        return "Components";
    }

    @Override
    public String getUrl() {
        return "components";
    }
}
