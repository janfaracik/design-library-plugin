package io.jenkins.plugins.designlibrary;

import hudson.Extension;

@Extension
public class HomeNew extends Category {

    @Override
    public String getIcon()  {
        return "symbol-design-library plugin-design-library";
    }

    @Override
    public String getDisplayName() {
        return "Home";
    }

    @Override
    public String getUrl() {
        return "home";
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
