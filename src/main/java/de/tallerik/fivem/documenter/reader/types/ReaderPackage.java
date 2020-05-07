package de.tallerik.fivem.documenter.reader.types;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReaderPackage implements ReaderObject {
    File file;
    boolean res = false;
    String name;
    List<ReaderObject> obj;

    public ReaderPackage() {
        obj = new ArrayList<ReaderObject>();
    }

    public String getFullPath() {
        return file.getAbsolutePath();
    }

    public String getFile() {
        return file.getAbsolutePath();
    }

    public String getName() {
        return name;
    }

    public boolean isResource() {
        return res;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ReaderObject> getObj() {
        return obj;
    }

    public void addResource(ReaderResource res) {
        obj.add(res);
    }

    public void addPackage(ReaderPackage res) {
        obj.add(res);
    }
}
