package de.tallerik.fivem.documenter;

import de.tallerik.fivem.documenter.generator.Generator;
import de.tallerik.fivem.documenter.jsongenerator.JsonGenerator;
import de.tallerik.fivem.documenter.reader.Reader;
import de.tallerik.fivem.documenter.reader.types.ReaderPackage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Launcher {
    public static ReaderPackage res;
    public static List<String> ignoreResources;
    public static List<String> ignorePackages;
    public static File output = new File("start.html");
    public static File outputJson = new File("start.json");

    public static GeneratorTypes generator = GeneratorTypes.JSON;

    /*

        Todos:
        -   TODO: Not all Lua files detected
        -   TODO: Fix HTML Generator
        -   TODO: Parse Lua Files
        -   Find out what to do with the json file

     */
    public static void main(String[] args) {

        // Init
        String path = "D:\\fiveM\\server\\resources";
        ignorePackages = new ArrayList<String>();
        ignorePackages.add("[gamemodes]"); // Example
        ignoreResources = new ArrayList<String>();
        ignoreResources.add("_default");   // Example


        // Start reading and generating
        new Reader(path);
        if(generator.equals(GeneratorTypes.HTML))
            new Generator(res);
        if(generator.equals(GeneratorTypes.JSON))
            new JsonGenerator(res);
        System.out.println("Done.");
        System.exit(0);
    }
}

enum GeneratorTypes {
    HTML, JSON
}
