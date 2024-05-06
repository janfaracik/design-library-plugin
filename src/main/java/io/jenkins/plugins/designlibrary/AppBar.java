package io.jenkins.plugins.designlibrary;

import hudson.Extension;
import lib.LayoutTagLib;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Extension
public class AppBar extends UISample {
    @Override
    public String getDisplayName() {
        return "App bars";
    }

    public static class JellyAttribute {
        private final String name;
        private final String description;
        private final boolean required;

        public JellyAttribute(String name, String description, boolean required) {
            this.name = name;
            this.description = description;
            this.required = required;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public boolean isRequired() {
            return required;
        }

        @Override
        public String toString() {
            return "JellyAttribute{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", required=" + required +
                    '}';
        }
    }

    private String getComponent() {
        ClassLoader classLoader = LayoutTagLib.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("lib/layout/app-bar.jelly");

        if (inputStream != null) {
            try {
                // Read the InputStream
                return readInputStreamToString(inputStream);
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("Could not find the Jelly file");
        }
        return null;
    }

    private List<JellyAttribute> parseJellyFile(String xml) {
        List<JellyAttribute> attributes = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document doc;
        try {
            InputSource is = new InputSource(new StringReader(xml));
            doc = builder.parse(is);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        NodeList attributeNodes = doc.getElementsByTagName("st:attribute");
        for (int i = 0; i < attributeNodes.getLength(); i++) {
            Node attributeNode = attributeNodes.item(i);
            if (attributeNode.getNodeType() == Node.ELEMENT_NODE) {
                Element attributeElement = (Element) attributeNode;
                String name = attributeElement.getAttribute("name");
                boolean required = attributeElement.getAttribute("use").equals("required");
                String description = attributeElement.getTextContent().trim();
                attributes.add(new JellyAttribute(name, description, required));
            }
        }

        return attributes;
    }

    public List<JellyAttribute> getRoar() {
        return parseJellyFile(getComponent());
    }

    @Override
    public String getIconFileName() {
        return "symbol-app-bar";
    }

    private static String readInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n"); // Append newline character
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        return stringBuilder.toString();
    }

    @Extension
    public static final class DescriptorImpl extends UISampleDescriptor {
    }
}

