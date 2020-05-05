package de.tallerik.fivem.documenter.generator;

import de.tallerik.fivem.documenter.generator.types.DocsTemplate;
import de.tallerik.fivem.documenter.reader.types.ReaderObject;
import de.tallerik.fivem.documenter.reader.types.ReaderPackage;
import de.tallerik.fivem.documenter.reader.types.ReaderResource;
import javafx.util.Pair;

public class GeneratorRunner {
    // Returns Sidebar and Content
    public static Pair<String, String> generate(ReaderPackage pack, DocsTemplate template) {
        String sidebar = "";
        String content = "";
        Pair<String, String> output = new Pair<String, String>(sidebar, content);






        return output;
    }

    public Pair<String, String> loopThrow(ReaderPackage pack, Pair<String, String> data) {
        for (ReaderObject object : pack.getObj()) {
            if(object instanceof ReaderResource) {

            } else {

            }
        }


        return data;
    }
}
