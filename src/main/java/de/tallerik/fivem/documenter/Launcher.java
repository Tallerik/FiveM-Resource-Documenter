package de.tallerik.fivem.documenter;

import de.tallerik.fivem.documenter.generator.Generator;
import de.tallerik.fivem.documenter.reader.Reader;
import de.tallerik.fivem.documenter.reader.types.ReaderPackage;

import java.util.ArrayList;
import java.util.List;

public class Launcher {
    public static ReaderPackage res;
    public static List<String> ignoreResources;
    public static List<String> ignorePackages;
    public static void main(String[] args) {

        // Init
        String path = "D:\\fiveM\\server\\resources";
        ignorePackages = new ArrayList<String>();
        ignorePackages.add("[gamemodes]"); // Example
        ignoreResources = new ArrayList<String>();
        ignoreResources.add("_default");   // Example


        // Start reading and generating
        new Reader(path);
        new Generator(res);
        System.out.println("Done.");
        System.exit(0);
    }
}
