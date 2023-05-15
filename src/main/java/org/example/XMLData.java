package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLData {
    private String[] load;
    private String[] save;
    private String[] log;

    protected void readXML() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("shop.xml"));

        String[] load = null;
        String[] save = null;
        String[] log = null;

        NodeList nodeList = document.getChildNodes().item(0).getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;

                switch (i) {
                    case 0:
                        load = returnAttributes(element, 3);
                        break;
                    case 1:
                        save = returnAttributes(element, 3);
                        break;
                    case 2:
                        log = returnAttributes(element, 2);
                        break;
                }
            }
        }
        this.load = load;
        this.save = save;
        this.log = log;
    }

    protected void setLoadEnabled() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("shop.xml"));
        NodeList nodeList = document.getChildNodes().item(0).getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                element.getElementsByTagName("enabled").item(0).setTextContent("true");
            }
        }
    }

    private String[] returnAttributes(Element element, int attrs) {

        if (attrs == 3) {
            String[] attributes = {element.getElementsByTagName("enabled").item(0).getTextContent(),
                    element.getElementsByTagName("fileName").item(0).getTextContent(),
                    element.getElementsByTagName("fileName").item(0).getTextContent()};
            return attributes;
        } else {
            String[] attributes = {element.getElementsByTagName("enabled").item(0).getTextContent(),
                    element.getElementsByTagName("fileName").item(0).getTextContent()};
            return attributes;
        }
    }



    public String[] getLoad() {
        return load;
    }
    public String[] getSave() {
        return save;
    }
    public String[] getLog() {
        return log;
    }
}
