package io.jenkins.plugins.designlibrary;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.search.SearchItem;
import java.util.Collection;
import jenkins.search.SearchGroup;
import jenkins.search.SearchItemProvider;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;

@Extension
@Restricted(NoExternalUse.class)
public class SampleSearchItemProvider implements SearchItemProvider {

    @NonNull
    @Override
    public Collection<? extends SearchItem> getSearchItems() {
        SearchGroup group = SearchGroup.get(DesignLibrarySearchGroup.class);
        return UISample.getAll().stream()
                .map(sample -> new SampleSearchItem(sample, "design-library/" + sample.getUrlName(), group))
                .toList();
    }

    @Extension
    @Restricted(NoExternalUse.class)
    public static class DesignLibrarySearchGroup implements SearchGroup {

        @Override
        public String getDisplayName() {
            return "Design Library";
        }
    }
}
