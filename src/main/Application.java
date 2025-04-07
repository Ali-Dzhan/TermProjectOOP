package main;

import main.java.XmlParser;

public class Application {
    public static void main(String[] args) {
       XmlParser parser = new XmlParser();

       parser.parse("C:\\Users\\Ali\\IdeaProjects\\Term_Project_OOP\\src\\main\\recources\\sample.xml");
       parser.print();
    }
}