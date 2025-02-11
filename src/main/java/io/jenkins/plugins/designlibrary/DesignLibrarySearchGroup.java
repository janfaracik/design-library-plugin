package io.jenkins.plugins.designlibrary;

import hudson.Extension;
import jenkins.search.SearchGroup;

@Extension
public class DesignLibrarySearchGroup implements SearchGroup {

    @Override
    public String getDisplayName() {
        return "Design Library";
    }
}
