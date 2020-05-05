package de.tallerik.fivem.documenter.reader;

import de.tallerik.fivem.documenter.Launcher;
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
        System.out.println("Start Reading.\n");
        System.out.println("Entering root");
        ReaderPackage pack = new ReaderPackage();
        pack.setFile(file);
        pack.setName("root");
        pack = loopThrow(file, pack);
        // Pack is finished generating here!
        Launcher.res = pack;
        System.out.println("Reading finished\n");
    }

    public ReaderPackage loopThrow(final File folder, ReaderPackage addto) {
        for (final File fileEntry : folder.listFiles()) {
            if(fileEntry.isDirectory()) {
                if(fileEntry.getName().startsWith("[") && fileEntry.getName().endsWith("]")) {
                    if(!Launcher.ignorePackages.contains(fileEntry.getName())) {
                        System.out.println("Entering " + fileEntry.getName());
                        ReaderPackage pack = new ReaderPackage();
                        pack.setName(fileEntry.getName());
                        pack.setFile(fileEntry);
                        pack = loopThrow(fileEntry, pack);
                        addto.addPackage(pack);
                    } else {
                        System.out.println("Not entering " + fileEntry.getName() + " because it is ignored");
                    }

                } else {
                    if(!Launcher.ignoreResources.contains(fileEntry.getName())) {
                        System.out.println("- " + fileEntry.getName());
                        ReaderResource res = new ReaderResource();
                        res.setName(fileEntry.getName());
                        res.setPath(fileEntry.getAbsolutePath());
                        res.setFile(fileEntry);
                        res.process();
                        addto.addResource(res);
                    } else {
                        System.out.println("- " + fileEntry.getName() + " (ignored)");
                    }

                }
            }
        }
        return addto;
    }


}
