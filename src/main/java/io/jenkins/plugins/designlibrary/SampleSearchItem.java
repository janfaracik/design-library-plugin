package io.jenkins.plugins.designlibrary;

import hudson.search.SearchIndex;
import hudson.search.SearchItem;
import jenkins.search.SearchGroup;

/**
 * A {@link UISample} as a search result.
 */
record SampleSearchItem(UISample sample, String url, SearchGroup group) implements SearchItem {

    @Override
    public String getSearchName() {
        return sample.getDisplayName();
    }

    @Override
    public String getSearchUrl() {
        return url;
    }

    @Override
    public String getSearchIcon() {
        return sample.getIconFileName();
    }

    @Override
    public SearchGroup getSearchGroup() {
        return group;
    }

    @Override
    public SearchIndex getSearchIndex() {
        return SearchIndex.EMPTY;
    }
}
