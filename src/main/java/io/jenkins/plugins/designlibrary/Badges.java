package io.jenkins.plugins.designlibrary;

import hudson.Extension;

@Extension
public class Badges extends UISample {
    @Override
    public String getIconFileName() {
        return "symbol-badge";
    }

    @Override
    public String getDescription() {
        return "TODO";
    }

    @Extension
    public static final class DescriptorImpl extends UISampleDescriptor {}
}
