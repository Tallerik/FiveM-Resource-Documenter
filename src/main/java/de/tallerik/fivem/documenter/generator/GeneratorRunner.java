package de.tallerik.fivem.documenter.generator;

import de.tallerik.fivem.documenter.generator.types.DocsTemplate;
import de.tallerik.fivem.documenter.generator.types.LuaFile;
import de.tallerik.fivem.documenter.reader.types.ReaderObject;
import de.tallerik.fivem.documenter.reader.types.ReaderPackage;
import de.tallerik.fivem.documenter.reader.types.ReaderResource;
import javafx.util.Pair;

import java.io.File;
import java.util.Map;

public class GeneratorRunner {
    // Returns Sidebar and Content
    public static Pair<String, String> generate(ReaderPackage pack, DocsTemplate template) {
        String sidebar = "";
        String content = "";
        Pair<String, String> init = new Pair<String, String>(sidebar, content);

        return loopThrow(pack, init, template);
    }

    public static Pair<String, String> loopThrow(ReaderPackage pack, Pair<String, String> data, DocsTemplate template) {
        String sidebar = data.getKey(), content = data.getValue();
        for (ReaderObject object : pack.getObj()) {
            if(object instanceof ReaderResource) {
                String doc = template.getDoc();
                ReaderResource res = (ReaderResource)object;
                doc = doc.replace("[[DOC_TITLE]]", object.getName());  // Title
                doc = doc.replace("[[DOC_ID]]", object.getName());  // ID
                String documentation = "";

                // Properties
                if(res.propertiesHasKey("manifest_version"))
                    documentation = documentation + "<h3><strong>Description:</strong> " + res.getProperties().get("description") + "</h3>";
                if(res.propertiesHasKey("author"))
                    documentation = documentation + "<h3><strong>Author:</strong> " + res.getProperties().get("author") + "</h3>";
                if(res.propertiesHasKey("description"))
                    documentation = documentation + "<h3><strong>Manifest Version:</strong> " + res.getProperties().get("manifest_version") + "</h3>";
                documentation = documentation + "<h4><strong>Pfad:</strong> " + res.getFullPath() + "</h4>";
                if(res.isServerOnly())
                    documentation = documentation + "<small> " + res.getProperties().get("manifest_version") + "</small><br>";

                // Lua Files
                if(res.getClientLua().size() > 0) {
                    documentation = documentation + "<h4><strong> ClientLua Dateien: " + res.getClientLua().size() + "</strong></h4>";
                    documentation = documentation + "<ul>";
                    for(LuaFile file : res.getClientLua()) {
                        documentation  = documentation + "<li>" + file.getName() + "</li>";
                    }
                    documentation = documentation + "</ul>";
                }
                if(res.getServerLua().size() > 0) {
                    documentation = documentation + "<h4><strong> ServerLua Dateien: " + res.getServerLua().size() + "</strong></h4>";
                    documentation = documentation + "<ul>";
                    for(LuaFile file : res.getServerLua()) {
                        documentation  = documentation + "<li>" + file.getName() + "</li>";
                    }
                    documentation = documentation + "</ul>";
                }
                if(res.getSharedLua().size() > 0) {
                    documentation = documentation + "<h4><strong> SharedLua Dateien: " + res.getSharedLua().size() + "</strong></h4>";
                    documentation = documentation + "<ul>";
                    for(LuaFile file : res.getSharedLua()) {
                        documentation  = documentation + "<li>" + file.getName() + "</li>";
                    }
                    documentation = documentation + "</ul>";
                }
                if(res.getdataFiles().size() > 0) {
                    documentation = documentation + "<h4><strong> Meta-Dateien: " + res.getdataFiles().size() + "</strong></h4>";
                    documentation = documentation + "<ul>";
                    for (Map.Entry<String, File> entry : res.getdataFiles().entrySet()) {
                        documentation  = documentation + "<li>" + entry.getKey() + "</li>";
                    }
                    documentation = documentation + "</ul>";
                }
                doc = doc.replace("[[DOC_BODY]]", "<div>" + documentation + "</div>");
                content = content + doc;

            } else { // ReaderPackage
                ReaderPackage readerPackage = (ReaderPackage) object;
                content = content + "<h1>" + readerPackage.getName() + "</h1><small>" + readerPackage.getFullPath() + "</small>";
                Pair<String, String> init = loopThrow(readerPackage, new Pair<>(sidebar, content), template);
                sidebar = sidebar + init.getKey();
                content = content + init.getValue();
            }
        }


        return new Pair<>(sidebar, content);
    }
}
