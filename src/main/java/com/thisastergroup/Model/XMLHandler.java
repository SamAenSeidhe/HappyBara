package com.thisastergroup.Model;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;

public class XMLHandler {
    /*
     * The constructor gets the document from the file path specified and provides
     * the information required
     * 
     */

    private DocumentBuilderFactory factory;
    private String ID;
    private String Question;
    private String Tip;

    public XMLHandler() {
        factory = DocumentBuilderFactory.newInstance();
    }

    /**
     * This method gets the activities from the XML file by iterating each Node
     * 
     * @param ActivityCategory - Finds the specified category by iterating through
     *                         the XML file
     * 
     */
    private void getActivities(String ActivityCategory) {
        try {
            // Get the document from the file path and normalize it
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File("src//main//resources//Activities.xml"));
            doc.getDocumentElement().normalize();

            // Get the list of all <category>
            NodeList CategoriesList = doc.getElementsByTagName("category");

            for (int i = 0; i < CategoriesList.getLength(); i++) {

                // Get the category node and cast them into an iterable NodeList
                NodeList category = (NodeList) CategoriesList.item(i);
                // The conditional verifies if the node is an element
                if (CategoriesList.item(i).getNodeType() == Node.ELEMENT_NODE) {

                    Element categoryElement = (Element) CategoriesList.item(i);

                    if (categoryElement.getAttribute("name").equals(ActivityCategory)) {

                        // Iterate through the category node to get the <question> node
                        // TODO change category.getLength() to a random number between 0 and
                        // category.getLength()
                        for (int j = 0; j < category.getLength(); j++) {

                            if (category.item(j).getNodeType() == Node.ELEMENT_NODE) {

                                // Lastly get the text and tip from the <question> node
                                NodeList a = category.item(j).getChildNodes();
                                for (int k = 0; k < a.getLength(); k++) {

                                    if (a.item(k).getNodeName().equals("text"))
                                        Question = a.item(k).getTextContent();
                                    if (a.item(k).getNodeName().equals("tip"))
                                        Tip = a.item(k).getTextContent();
                                }

                            }
                        }
                    }
                }

            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Interaction getRandomHygene() {
        getActivities("hygene");
        return new Interaction(ID, Question, Tip);
    }

    public Interaction getRandomEat() {
        getActivities("eating");

        return new Interaction(ID, Question, Tip);
    }

    public Interaction getRandomSleep() {
        getActivities("sleeping");

        return new Interaction(ID, Question, Tip);
    }

}
