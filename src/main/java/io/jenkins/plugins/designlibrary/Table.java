package io.jenkins.plugins.designlibrary;

import hudson.Extension;

/**
 * @author Kohsuke Kawaguchi
 */
@Extension
public class Table extends UISample {

    @Override
    public String getIcon() {
        return "symbol-table";
    }
}

