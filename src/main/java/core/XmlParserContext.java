package main.java.core;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Shared data context used by all commands.
 * Holds the root XML element, element map, ID tracking,
 * and the XPath processor for queries.
 */
public class XmlParserContext {

    private XmlElement root;
    private final Map<String, XmlElement> idMap = new LinkedHashMap<>();
    private final Set<String> usedIds = new HashSet<>();
    private int generatedIdCounter = 1;
    private final XPathProcessor xpathProcessor = new XPathProcessor();
    private String openedFilePath;

    public String getOpenedFilePath() {
        return openedFilePath;
    }

    public void setOpenedFilePath(String openedFilePath) {
        this.openedFilePath = openedFilePath;
    }

    public XmlElement getRoot() {
        return root;
    }

    public void setRoot(XmlElement root) {
        this.root = root;
    }

    public Map<String, XmlElement> getIdMap() {
        return idMap;
    }

    public Set<String> getUsedIds() {
        return usedIds;
    }

    public int getNextGeneratedId() {
        return generatedIdCounter++;
    }

    public XPathProcessor getXpathProcessor() {
        return xpathProcessor;
    }
}
