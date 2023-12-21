package io.jenkins.plugins.designlibrary;

import hudson.ExtensionPoint;
import hudson.model.Action;
import hudson.model.Describable;
import io.jenkins.plugins.prism.PrismConfiguration;
import jenkins.model.Jenkins;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;

/**
 * @author Kohsuke Kawaguchi
 */
public abstract class UISample implements ExtensionPoint, Action, Describable<UISample> {
    public String getUrlName() {
        return getClass().getSimpleName();
    }

    public Class<? extends Category> getCategory() {
        return Components.class;
    }

    public String getDisplayName() {
        return getClass().getSimpleName();
    }

    public UISampleDescriptor getDescriptor() {
        return (UISampleDescriptor) Jenkins.get().getDescriptorOrDie(getClass());
    }

    @Restricted(NoExternalUse.class)
    public PrismConfiguration getPrismConfiguration() {
        return PrismConfiguration.getInstance();
    }

//    public static List<UISample> getAll() {
//        return new ArrayList<>(Jenkins.get().getExtensionList(UISample.class));
//    }

//    public UISample getDynamic(String name) {
//        System.out.println("Name 2 is");
//        System.out.println(name);
//        for (UISample ui : getAll()) {
//            String urlName = ui.getUrlName();
//            if (urlName != null && urlName.equals(name))
//                return ui;
//        }
//        return null;
//    }
//
//    public UISample getPreviousPage() {
//        try {
//            return getAll().get(getAll().indexOf(this) - 1);
//        } catch (IndexOutOfBoundsException e) {
//            return null;
//        }
//    }
//
//    public UISample getNextPage() {
//        try {
//            return getAll().get(getAll().indexOf(this) + 1);
//        } catch (IndexOutOfBoundsException e) {
//            return null;
//        }
//    }
}
