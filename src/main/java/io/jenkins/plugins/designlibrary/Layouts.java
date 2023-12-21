package io.jenkins.plugins.designlibrary;

import hudson.Extension;
import jenkins.management.Badge;

@Extension
public class Layouts extends UISample {

    @Override
    public String getDisplayName() {
        return "Layouts";
    }

    @Override
    public String getIcon() {
        return "symbol-layouts";
    }

    public Badge getBadge() {
        return new Badge("123", "A tooltip describing the badge", Badge.Severity.INFO);
    }

    @Override
    public Class<? extends Category> getCategory() {
        return Patterns.class;
    }
}

