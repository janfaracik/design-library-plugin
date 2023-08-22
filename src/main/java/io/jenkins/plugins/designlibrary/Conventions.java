package io.jenkins.plugins.designlibrary;

import hudson.Extension;

@Extension
public class Conventions extends UISample {
    @Override
    public String getIconFileName() {
        return "symbol-school-outline plugin-ionicons-api";
    }

    @Override
    public PageCategory getCategory() {
        return PageCategory.PATTERN;
    }

    @Extension
    public static final class DescriptorImpl extends UISampleDescriptor {
    }
}

