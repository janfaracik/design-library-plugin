package io.jenkins.plugins.designlibrary.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SearchIndexer {

    private static final Logger LOGGER = Logger.getLogger(SearchIndexer.class.getName());

    public static void main(String[] args) throws Exception {
        LOGGER.info("🕵️  Generating search index");

        String basePath = "src/main/resources/io/jenkins/plugins/designlibrary/";
        List<LanguageThing> languageThings = generateLanguageThings(basePath);

        List<Bodyguard> bodyguards =
                languageThings.stream().map(SearchIndexer::convertToBodyguard).toList();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String output = objectMapper.writeValueAsString(bodyguards);

        File file = new File("src/main/resources/search-index.json");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(output);
        }
    }

    private static Bodyguard convertToBodyguard(LanguageThing languageThing) {
        // Transform into the required format
        List<Map<String, String>> output = new ArrayList<>();

        for (String heading : languageThing.headings) {

            if (!heading.startsWith("${%")) {
                Map<String, String> transformedProperties = new HashMap<>();
                transformedProperties.put("default", heading);
                output.add(transformedProperties);
                continue;
            }

            Map<String, String> translations = new HashMap<>();
            String transformedHeading = heading.replace("${%", "").replace("}", "");
            for (Map.Entry<String, Properties> entry :
                    languageThing.properties().entrySet()) {
                String language = entry.getKey();
                if (entry.getKey().equals("default") || entry.getValue().getProperty(transformedHeading) != null) {
                    translations.put(language, entry.getValue().getProperty(transformedHeading, transformedHeading));
                }
            }
            output.add(translations);
        }

        // Create and return the Bodyguard
        return new Bodyguard(languageThing.name(), output);
    }

    private static List<LanguageThing> generateLanguageThings(String basePath) throws Exception {
        List<LanguageThing> languageThings = new ArrayList<>();

        // Traverse the base directory
        File baseDir = new File(basePath);
        if (!baseDir.isDirectory()) {
            throw new IllegalArgumentException("Base path is not a directory: " + basePath);
        }

        // Process each subfolder
        for (File folder : Objects.requireNonNull(baseDir.listFiles(File::isDirectory))) {
            String folderName = folder.getName();

            // Read the index.jelly file
            File indexFile = new File(folder, "index.jelly");
            String indexContents = indexFile.exists() ? readFileContents(indexFile) : "";
            List<String> headings = extractHeadingsFromJelly(indexContents);

            // Load all properties files in the folder
            Map<String, Properties> propertiesMap = Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                    .filter(file ->
                            file.getName().startsWith("index") && file.getName().endsWith(".properties"))
                    .collect(Collectors.toMap(
                            file -> file.getName()
                                    .replace(".properties", "")
                                    .replace("index_", "")
                                    .replace("index", "default"), // Country name
                            SearchIndexer::readPropertiesFile // File contents
                            ));

            // Create a LanguageThing and add it to the list
            languageThings.add(new LanguageThing(folderName, headings, propertiesMap));
        }

        return languageThings;
    }

    private static List<String> extractHeadingsFromJelly(String contents) throws Exception {
        List<String> headings = new ArrayList<>();

        // Parse the XML content from the string
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // Handle namespaces in <s:section>
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Parse the XML document from the string
        try (InputStream inputStream = new ByteArrayInputStream(contents.getBytes())) {
            Document document = builder.parse(inputStream);

            // Find all <s:section> elements
            NodeList sectionNodes = document.getElementsByTagNameNS("*", "section"); // Handle namespace wildcard
            for (int i = 0; i < sectionNodes.getLength(); i++) {
                Element section = (Element) sectionNodes.item(i);
                String title = section.getAttribute("title");
                if (!title.isBlank()) {
                    headings.add(title);
                }
            }
        }

        return headings;
    }

    private static Properties readPropertiesFile(File file) {
        Properties properties = new Properties();
        try (FileReader reader = new FileReader(file)) {
            properties.load(reader);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load properties file: " + file.getPath(), e);
        }
        return properties;
    }

    // Helper method to read file contents
    private static String readFileContents(File file) {
        try {
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read file: " + file.getPath(), e);
        }
    }

    // Name is the name of the folder
    // Index contents is the contents of the .jelly file
    // properties is a map of country + contents of the properties file
    private record LanguageThing(String name, List<String> headings, Map<String, Properties> properties) {}
}
