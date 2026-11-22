package io.jenkins.plugins.designlibrary;

import hudson.Extension;
import hudson.search.Search;
import hudson.search.SearchFactory;
import hudson.search.SearchIndex;
import hudson.search.SearchItem;
import hudson.search.SearchResult;
import hudson.search.SearchableModelObject;
import hudson.search.SuggestedItem;
import java.util.ArrayList;
import java.util.Locale;
import jenkins.search.SearchGroup;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;
import org.kohsuke.stapler.StaplerRequest2;

@Extension
@Restricted(NoExternalUse.class)
public class SampleSearchFactory extends SearchFactory {

    @Override
    public Search createFor(SearchableModelObject owner) {
        return new SampleSearch();
    }

    private static class SampleSearch extends Search {

        @Override
        public SearchResult getSuggestions(StaplerRequest2 req, String query) {
            SearchResult suggestions = super.getSuggestions(req, query);
            if (query == null || query.isBlank()) {
                return suggestions;
            }

            String token = query.trim().toLowerCase(Locale.ROOT);
            Results results = new Results(suggestions);
            for (UISample sample : UISample.getAll()) {
                if (sample.getDisplayName().toLowerCase(Locale.ROOT).contains(token)) {
                    results.add(new SuggestedItem(new SampleSearchItem(sample)));
                }
            }
            return results;
        }
    }

    private static class Results extends ArrayList<SuggestedItem> implements SearchResult {

        private final boolean hasMoreResults;

        Results(SearchResult suggestions) {
            super(suggestions);
            this.hasMoreResults = suggestions.hasMoreResults();
        }

        @Override
        public boolean hasMoreResults() {
            return hasMoreResults;
        }
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
