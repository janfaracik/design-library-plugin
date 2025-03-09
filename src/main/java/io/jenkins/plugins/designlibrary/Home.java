package io.jenkins.plugins.designlibrary;

import hudson.Extension;
import hudson.PluginWrapper;
import hudson.model.AbstractModelObject;
import hudson.model.RootAction;
import hudson.search.SearchIndex;
import hudson.search.SearchIndexBuilder;
import hudson.search.SearchItem;
import java.util.List;
import java.util.Map;
import jenkins.model.Jenkins;
import jenkins.search.SearchGroup;

/**
 * Entry point to all the UI samples.
 *
 * @author Kohsuke Kawaguchi
 */
@Extension
public class Home extends AbstractModelObject implements RootAction {

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

    @Override
    public String getSearchUrl() {
        return "";
    }

    @Override
    protected SearchIndexBuilder makeSearchIndex() {
        SearchIndexBuilder searchIndexBuilder = new SearchIndexBuilder();

        for (UISample ui : getAll()) {
            searchIndexBuilder.add(new SearchItem() {

                @Override
                public String getSearchName() {
                    return ui.getDisplayName();
                }

                @Override
                public String getSearchUrl() {
                    return getUrlName() + "/" + ui.getUrlName();
                }

                @Override
                public String getSearchIcon() {
                    return ui.getIconFileName();
                }

                @Override
                public SearchGroup getSearchGroup() {
                    return SearchGroup.get(DesignLibrarySearchGroup.class);
                }

                @Override
                public SearchIndex getSearchIndex() {
                    return null;
                }
            });
        }

        return searchIndexBuilder;
    }
}
