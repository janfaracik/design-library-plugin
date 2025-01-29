package io.jenkins.plugins.designlibrary;

import hudson.Extension;

@Extension
public class Badge extends UISample {

    public final jenkins.management.Badge infoBadge = new jenkins.management.Badge("1", "1 notification", jenkins.management.Badge.Severity.INFO);

    public final jenkins.management.Badge warningBadge = new jenkins.management.Badge("2", "2 notifications", jenkins.management.Badge.Severity.WARNING);

    public final jenkins.management.Badge dangerBadge = new jenkins.management.Badge("3", "3 notifications", jenkins.management.Badge.Severity.DANGER);

    @Override
    public String getIconFileName() {
        return "symbol-badge";
    }

    @Override
    public String getDescription() {
        return "Use badges to indicate statuses, alerts, or additional metadata at a glance";
    }

    @Extension
    public static final class DescriptorImpl extends UISampleDescriptor {}
}
