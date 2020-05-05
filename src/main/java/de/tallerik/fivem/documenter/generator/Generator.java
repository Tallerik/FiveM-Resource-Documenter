package de.tallerik.fivem.documenter.generator;

import de.tallerik.fivem.documenter.reader.types.ReaderObject;
import de.tallerik.fivem.documenter.reader.types.ReaderPackage;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Generator {

    String mainhtml, doc, nav, nav_sub;

    public Generator(ReaderPackage pack) {
        System.out.println("Generator started.\nReading Files:");
        mainhtml = read(getClass().getResourceAsStream("/resources/start.html"));
        doc = read(getClass().getResourceAsStream("/resources/doc.html"));
        nav = read(getClass().getResourceAsStream("/resources/nav.html"));
        nav_sub = read(getClass().getResourceAsStream("/resources/nav_sub.html"));
        System.out.println("Reading completed.");
        System.out.println("Generating HTML");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");
        LocalDateTime dateTime = LocalDateTime.now();
        String date = dateTime.format(format);
        mainhtml.replaceAll("{{LASTUPDATE}}", date);



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
