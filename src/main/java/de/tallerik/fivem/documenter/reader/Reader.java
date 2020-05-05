package de.tallerik.fivem.documenter.reader;

import de.tallerik.fivem.documenter.reader.types.ReaderPackage;
import de.tallerik.fivem.documenter.reader.types.ReaderResource;

import java.io.File;

public class Reader {
    File file;

    public Reader(String path) {
        file = new File(path);
        if(!file.exists()) {
            System.out.println("File: " + file.getAbsolutePath() + " not found.");
            System.exit(1);
        }
        if(!file.isDirectory()) {
            System.out.println("File: " + file.getAbsolutePath() + " is not a folder.");
            System.exit(1);
        }
        ReaderPackage pack = new ReaderPackage();
        pack.setFile(file);
        pack.setName("root");
        pack = loopThrow(file, pack);
        // Pack is finished generating here!
    }

    public ReaderPackage loopThrow(final File folder, ReaderPackage addto) {
        for (final File fileEntry : folder.listFiles()) {
            if(fileEntry.isDirectory()) {
                if(fileEntry.getName().startsWith("[") && fileEntry.getName().endsWith("]")) {
                    ReaderPackage pack = new ReaderPackage();
                    pack.setName(fileEntry.getName());
                    pack.setFile(fileEntry);
                    pack = loopThrow(fileEntry, pack);
                    addto.addPackage(pack);
                } else {
                    ReaderResource res = new ReaderResource();
                    res.setName(fileEntry.getName());
                    res.setPath(fileEntry.getAbsolutePath());
                    addto.addResource(res);
                }
            }
        }
        return addto;
    }


}
