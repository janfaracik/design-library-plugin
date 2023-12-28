package io.jenkins.plugins.designlibrary;

import hudson.Extension;
import hudson.model.ModelObject;
import hudson.model.RootAction;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Extension
public class DesignLibrary implements RootAction, ModelObject {

    public String getIconFileName() {
        return "symbol-design-library plugin-design-library";
    }

    public String getDisplayName() {
        return "Design Library";
    }

    public String getUrlName() {
        return "design-library";
    }

    public void doIndex(StaplerRequest request, StaplerResponse response) throws ServletException, IOException {
        doDynamic(request, response);
    }

    public void doDynamic(StaplerRequest request, StaplerResponse response) throws IOException, ServletException {
        String path = request.getRestOfPath().replaceFirst("/", "");
        String[] paths = new String[] {};
        if (!path.isEmpty()) {
            paths = path.split("/");
        }

        Category category;
        UISample uiSample;

        switch (paths.length) {
            case 0:
                category = getCategories().get(0);
                uiSample = category.getItems().get(0);
                break;
            case 1:
                category = getCategory(paths[0]);
                uiSample = category.getItems().get(0);
                break;
            case 2:
                category = getCategory(paths[0]);
                uiSample = category.getCategory(paths[1]);
                break;
            default:
                throw new RuntimeException("Page not found");
        }

        request.setAttribute("category", category);
        request.setAttribute("sample", uiSample);
        request.getView(this, "index.jelly").forward(request, response);
    }

    public List<Category> getCategories() {
        var categories = new ArrayList<>(Jenkins.get().getExtensionList(Category.class));
        categories.sort(Comparator
                .comparingInt((Category c) -> c.getOrder() == -1 ? Integer.MAX_VALUE : c.getOrder())
                .thenComparing(Category::getDisplayName));
        return categories;
    }

    public Category getCategory(String url) {
        return getCategories().stream()
                .filter(e -> e.getUrl().equals(url))
                .findFirst()
                .orElseThrow();
    }
}
