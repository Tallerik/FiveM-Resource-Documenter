package de.tallerik.fivem.documenter.reader.types;

import de.tallerik.fivem.documenter.jsongenerator.JsonGenerator;
import org.json.JSONArray;
import org.json.JSONObject;

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

    public void removePackage(ReaderPackage res) {
        obj.remove(res);
    }

    public JSONObject getJson() {
        JSONObject json = new JSONObject();
        json.append("name", name);
        json.append("file", getFile());
        JSONArray resources = new JSONArray();
        JSONArray packages = new JSONArray();

        for (ReaderObject object: obj) {
            if(object.isResource()) {
                ReaderResource resource = (ReaderResource) object;
                JSONObject ress = new JSONObject();
                JSONArray streamFiles = new JSONArray();
                resource.streamFiles.forEach((streamName, f) -> streamFiles.put(streamName));
                ress.append("name", resource.name);
                ress.append("properties", resource.properties);
                ress.append("clientLua", resource.clientLua);
                ress.append("serverLua", resource.serverLua);
                ress.append("sharedLua", resource.sharedLua);
                ress.append("dataFiles", resource.dataFiles);
                ress.append("serverOnly", resource.serverOnly);
                ress.append("streamFiles", streamFiles);
                ress.append("path", resource.getFullPath());
                resources.put(ress);
            } else {
                ReaderPackage pack = (ReaderPackage) object;
                packages.put(pack.getJson());
            }
        }
        json.append("packages", packages);
        json.append("resources", resources);
        return json;
    }
}
