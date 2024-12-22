package io.jenkins.plugins.designlibrary;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.ExtensionPoint;
import hudson.model.Action;
import hudson.model.Describable;
import io.jenkins.plugins.designlibrary.search.Bodyguard;
import io.jenkins.plugins.designlibrary.search.SearchResult;
import io.jenkins.plugins.prism.PrismConfiguration;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import jenkins.model.Jenkins;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;

/**
 * @author Kohsuke Kawaguchi
 */
public abstract class UISample implements ExtensionPoint, Action, Describable<UISample> {

    private List<SearchResult> searchResults;

    /**
     * Gets the URL-friendly name of the UI sample.
     * Converts the display name to lowercase and replaces spaces with dashes.
     *
     * @return a URL-friendly string representation of the display name.
     */
    public String getUrlName() {
        return getDisplayName().replaceAll(" ", "-").toLowerCase();
    }

    /**
     * Gets the default display name for the UI sample.
     * This is typically the simple class name of the implementing class.
     *
     * @return the display name.
     */
    public @NonNull String getDisplayName() {
        return getClass().getSimpleName();
    }

    /**
     * Retrieves the category of this UI sample.
     * Defaults to {@link Category#COMPONENT}.
     *
     * @return the category of the UI sample.
     */
    public Category getCategory() {
        return Category.COMPONENT;
    }

    /**
     * Provides a description for the UI sample.
     * The description is displayed at the top of every UI page for the sample.
     *
     * @return the description of the UI sample.
     */
    public abstract String getDescription();

    /**
     * Gets the version of Jenkins where this UI sample was introduced.
     * By default, returns null if version information is irrelevant.
     *
     * @return the version as a string, or null if irrelevant.
     */
    public String getSince() {
        return null;
    }

    public UISampleDescriptor getDescriptor() {
        return (UISampleDescriptor) Jenkins.get().getDescriptorOrDie(getClass());
    }

    @Restricted(NoExternalUse.class)
    public PrismConfiguration getPrismConfiguration() {
        return PrismConfiguration.getInstance();
    }

    public static List<UISample> getAll() {
        return getGrouped().values().stream().flatMap(Collection::stream).toList();
    }

    public static Map<Category, List<UISample>> getGrouped() {
        List<UISample> uiSamples = new ArrayList<>(Jenkins.get().getExtensionList(UISample.class));

        uiSamples.sort(Comparator.comparing(UISample::getDisplayName));

        return new TreeMap<>(uiSamples.stream().collect(Collectors.groupingBy(UISample::getCategory)));
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

    public List<SearchResult> getSearchResults() {
        var samples = getAll();
        //        System.out.println(samples.stream().map(e -> e.getClass().getSimpleName()).toList());
        if (searchResults == null) {
            ClassLoader classLoader = UISample.class.getClassLoader();
            try (InputStream inputStream = classLoader.getResourceAsStream("index.json")) {
                if (inputStream == null) {
                    throw new IOException("File not found in resources: index.json - you might need to recompile");
                }

                // Map JSON content to Bodyguard object
                ObjectMapper objectMapper = new ObjectMapper();
                List<Bodyguard> bodyguards = objectMapper.readValue(inputStream, new TypeReference<>() {});

                //                System.out.println(bodyguard.name);
                searchResults = bodyguards.stream()
                        .map(bodyguard -> {
                            Optional<UISample> uiSample = samples.stream()
                                    .filter(e -> e.getClass().getSimpleName().equals(bodyguard.name()))
                                    .findFirst();

                            if (uiSample.isEmpty()) {
                                return new SearchResult("Home", "symbol-home", List.of());
                            }

                            return new SearchResult(
                                    uiSample.get().getDisplayName(),
                                    uiSample.get().getIconFileName(),
                                    bodyguard.headings().stream()
                                            .map(e -> e.get("default"))
                                            .toList());
                        })
                        .toList();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return searchResults;
    }
}
