package io.jenkins.plugins.designlibrary;

import hudson.Extension;

@Extension
public class Alerts extends UISample {
    @Override
    public String getDisplayName() {
        return "Alerts";
    }

    @Override
    public String getIconFileName() {
        return "symbol-alerts";
    }

    @Extension
    public static final class DescriptorImpl extends UISampleDescriptor {
    }
}

