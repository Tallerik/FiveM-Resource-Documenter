package de.tallerik.fivem.documenter.reader.types;

public class ReaderResource implements ReaderObject {
    String path;
    String name;
    boolean res = true;

    public String getFullPath() {
        return null;
    }

    public String getFile() {
        return null;
    }

    public String getName() {
        return null;
    }

    public boolean isResource() {
        return false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
