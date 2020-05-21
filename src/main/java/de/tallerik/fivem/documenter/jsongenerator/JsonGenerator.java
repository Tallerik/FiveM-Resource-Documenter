package de.tallerik.fivem.documenter.jsongenerator;

import de.tallerik.fivem.documenter.Launcher;
import de.tallerik.fivem.documenter.reader.types.ReaderPackage;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class JsonGenerator {

    public JsonGenerator(ReaderPackage pack) {
        JSONObject json = pack.getJson();


        if(Launcher.outputJson.exists()) {
            System.out.println("Output file exists. Deleting...");
            if(Launcher.outputJson.delete()) {
                System.out.println("File deleted.");
            } else {
                System.out.println("Cant delete file: " + Launcher.outputJson.getAbsolutePath());
            }
        }
        try {
            Launcher.outputJson.createNewFile();
            FileWriter writer = new FileWriter(Launcher.outputJson);
            writer.write(json.toString());
            writer.close();
            System.out.println("Saving completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
