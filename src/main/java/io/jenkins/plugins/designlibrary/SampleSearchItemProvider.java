package io.jenkins.plugins.designlibrary;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.search.SearchIndex;
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
        return UISample.getAll().stream().map(SampleSearchItem::new).toList();
    }

    private record SampleSearchItem(UISample sample) implements SearchItem {

        @Override
        public String getSearchName() {
            return sample.getDisplayName();
        }

        @Override
        public String getSearchUrl() {
            return "design-library/" + sample.getUrlName();
        }

        @Override
        public String getSearchIcon() {
            return sample.getIconFileName();
        }

        @Override
        public SearchGroup getSearchGroup() {
            return SearchGroup.get(DesignLibrarySearchGroup.class);
        }

        @Override
        public SearchIndex getSearchIndex() {
            return SearchIndex.EMPTY;
        }
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
