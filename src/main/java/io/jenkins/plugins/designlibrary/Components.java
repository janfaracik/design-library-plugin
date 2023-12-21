package io.jenkins.plugins.designlibrary;

import hudson.Extension;
import hudson.model.Descriptor;

@Extension
public class Components extends Category {

    @Override
    public String getIcon() {
        return "symbol-close";
    }

    @Override
    public String getDisplayName() {
        return "Components";
    }

    @Override
    public String getUrl() {
        return "components";
    }

    @Extension
    public static final class DescriptorImpl extends CategoryDescriptor {
    }
}
