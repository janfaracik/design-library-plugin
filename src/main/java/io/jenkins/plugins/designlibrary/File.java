package io.jenkins.plugins.designlibrary;

import hudson.Extension;

@Extension
public class File extends UISample {

    @Override
    public String getDisplayName() {
        return "File";
    }

    @Override
    public String getIcon() {
        return "symbol-cloud-upload-outline plugin-ionicons-api";
    }
}

