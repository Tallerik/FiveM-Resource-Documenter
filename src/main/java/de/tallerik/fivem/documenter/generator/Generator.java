package de.tallerik.fivem.documenter.generator;

import de.tallerik.fivem.documenter.Launcher;
import de.tallerik.fivem.documenter.generator.types.DocsTemplate;
import de.tallerik.fivem.documenter.reader.types.ReaderObject;
import de.tallerik.fivem.documenter.reader.types.ReaderPackage;
import javafx.util.Pair;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Generator {

    String mainhtml, doc, nav, nav_sub;

    public Generator(ReaderPackage pack) {
        System.out.println("Generator started.\nReading Files:");
        mainhtml = read(getClass().getResourceAsStream("/start.html"));
        doc = read(getClass().getResourceAsStream("/doc.html"));
        nav = read(getClass().getResourceAsStream("/nav.html"));
        nav_sub = read(getClass().getResourceAsStream("/nav_sub.html"));
        DocsTemplate template = new DocsTemplate(doc, nav, nav_sub);
        System.out.println("Reading completed.");

        System.out.println("Generating HTML");

        // Time
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");
        LocalDateTime dateTime = LocalDateTime.now();
        String date = dateTime.format(format);
        mainhtml = mainhtml.replace("[[LASTUPDATE]]", date);

        // Runner     returns Sidebar and Content
        Pair<String, String> runner = GeneratorRunner.generate(pack, template);
        String sidebar = runner.getKey();
        String content = runner.getValue();
        mainhtml = mainhtml.replace("[[NAV_BODY]]", sidebar);
        mainhtml = mainhtml.replace("[[DOC_BODY]]", content);

        System.out.println("HTML generated successful.\nSaving...");
        if(Launcher.output.exists()) {
            System.out.println("Output file exists. Deleting...");
            if(Launcher.output.delete()) {
                System.out.println("File deleted.");
            } else {
                System.out.println("Cant delete file: " + Launcher.output.getAbsolutePath());
            }
        }
        try {
            Launcher.output.createNewFile();
            FileWriter writer = new FileWriter(Launcher.output);
            writer.write(mainhtml);
            writer.close();
            System.out.println("Saving completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Generator finished.");
    }


    // https://stackoverflow.com/a/35446009
    public String read(InputStream s) {
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = s.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }

            return result.toString("UTF-8");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
