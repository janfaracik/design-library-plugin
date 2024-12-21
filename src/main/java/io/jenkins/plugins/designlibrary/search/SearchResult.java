package io.jenkins.plugins.designlibrary.search;

import java.util.List;

public record SearchResult(String displayName, String iconFileName, List<String> anchors) {}
