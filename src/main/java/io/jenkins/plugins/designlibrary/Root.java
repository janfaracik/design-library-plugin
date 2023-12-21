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
import java.util.List;

@Extension
public class Root implements RootAction, ModelObject {

    public String getIconFileName() {
        return "symbol-design-library plugin-design-library";
    }

    public String getDisplayName() {
        return "Design Library";
    }

    public String getUrlName() {
        return "design-library";
    }

    public void doDynamic(StaplerRequest request, StaplerResponse response) throws IOException, ServletException {
        String[] paths = request.getRestOfPath().substring(1).split("/");

        Category category;
        UISample uiSample;

        switch (paths.length) {
            case 0:
                category = DesignLibrary.getInstance().getCategories().get(0);
                uiSample = category.getItems().get(0);
                break;
            case 1:
                category = DesignLibrary.getInstance().getCategory(paths[0]);
                uiSample = category.getItems().get(0);
                break;
            case 2:
                category = DesignLibrary.getInstance().getCategory(paths[0]);
                uiSample = category.getDynamic(paths[1]);
                break;
            default:
                throw new RuntimeException("Page not found");
        }

        request.getView(this, "index.jelly").forward(request, response);
    }

    public List<UISample> getAll() {
        return UISample.getAll();
    }

    public List<Category> getCategories() {
        return new ArrayList<>(Jenkins.get().getExtensionList(Category.class));
    }
}
