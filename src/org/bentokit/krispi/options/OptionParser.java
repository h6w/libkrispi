package org.bentokit.krispi.options;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class OptionParser {
    public static Option parse(Node node) {
        for(Node childNode = node.getFirstChild();
            childNode!=null;){
            Node nextChild = childNode.getNextSibling();
            if (childNode instanceof Element) {
                Element childElement = (Element) childNode;
                if (childElement.getNodeName().equals("option")) {
                    String nameStr = childElement.getAttribute("name");
                } else
                    System.err.println("Unknown xml element found: "+childElement.getNodeName());
            }
            childNode = nextChild;
        }
		return null; //FIXME
    }
}
