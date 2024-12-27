package io.jenkins.plugins.designlibrary;

import hudson.Extension;
import hudson.PluginWrapper;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.RootAction;
import java.util.List;
import java.util.Map;

import hudson.util.ComboBoxModel;
import jenkins.model.Jenkins;

/**
 * Entry point to all the UI samples.
 *
 * @author Kohsuke Kawaguchi
 */
@Extension
public class Home implements RootAction, Describable<Home> {

    public String getIconFileName() {
        return "symbol-design-library plugin-design-library";
    }

    public String getDisplayName() {
        return "Design Library";
    }

    public String getUrlName() {
        return "design-library";
    }

    public List<UISample> getAll() {
        return UISample.getAll();
    }

    public static Map<Category, List<UISample>> getGrouped() {
        return UISample.getGrouped();
    }

    /**
     * Dynamically retrieves the appropriate UI sample based on the current URL
     */
    public UISample getDynamic(String name) {
        for (UISample ui : getAll()) {
            String urlName = ui.getUrlName();
            if (urlName != null && urlName.equals(name)) {
                return ui;
            }
        }
        return null;
    }

    public String getPluginVersion() {
        Jenkins jenkins = Jenkins.get();
        PluginWrapper plugin = jenkins.getPluginManager().getPlugin("design-library");
        if (plugin != null) {
            return plugin.getVersion();
        }
        return "Version not available";
    }

    @Extension
    public static final class DescriptorImpl extends HomeDescriptor {
        public ComboBoxModel doFillStateItems() {
            return new ComboBoxModel("hello", "jello", "bello");
        }
    }

    @Override
    public Descriptor<Home> getDescriptor() {
        return (HomeDescriptor) Jenkins.get().getDescriptorOrDie(getClass());
    }
}
