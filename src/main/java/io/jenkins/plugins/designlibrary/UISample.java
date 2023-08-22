package io.jenkins.plugins.designlibrary;

import hudson.ExtensionPoint;
import hudson.model.Action;
import hudson.model.Describable;
import jenkins.model.Jenkins;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Kohsuke Kawaguchi
 */
public abstract class UISample implements ExtensionPoint, Action, Describable<UISample> {
    public String getIconFileName() {
        return "symbol-document-outline plugin-ionicons-api";
    }

    public String getUrlName() {
        return getClass().getSimpleName();
    }

    /**
     * Default display name.
     */
    public String getDisplayName() {
        return getClass().getSimpleName();
    }

    public PageCategory getCategory() {
        return PageCategory.COMPONENT;
    }

    public UISampleDescriptor getDescriptor() {
        return (UISampleDescriptor) Jenkins.get().getDescriptorOrDie(getClass());
    }

    public Map<PageCategory, List<UISample>> getCategorized() {
        return getAll().stream()
                .collect(
                        Collectors.groupingBy(
                                UISample::getCategory,
                                Collectors.mapping(
                                        Function.identity(),
                                        Collectors.toList()
                                )
                        )
                );
    }

    public static List<UISample> getAll() {
        return new ArrayList<>(Jenkins.get().getExtensionList(UISample.class));
    }

    public UISample getPreviousPage() {
        try {
            return getAll().get(getAll().indexOf(this) - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public UISample getNextPage() {
        try {
            return getAll().get(getAll().indexOf(this) + 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
