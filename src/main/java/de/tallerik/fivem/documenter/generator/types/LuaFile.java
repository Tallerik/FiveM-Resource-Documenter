package de.tallerik.fivem.documenter.generator.types;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LuaFile {
    ScriptType type;
    File file;
    String name;
    Map<String, Integer> addedEventHandlers = new HashMap<>();
    Map<String, Integer> triggeredServerEvents = new HashMap<>();
    Map<String, Integer> triggeredClientEvents = new HashMap<>();

    public LuaFile(File file, ScriptType type) {
        this.file = file;
        this.type = type;
    }

    public LuaFile(String file, ScriptType type) {
        this.file = new File(file);
        this.type = type;
    }

    public void process() {
        name = file.getName();
    }

    public File getFile() {
        return file;
    }

    public Map<String, Integer> getAddedEventHandlers() {
        return addedEventHandlers;
    }

    public Map<String, Integer> getTriggeredClientEvents() {
        return triggeredClientEvents;
    }

    public Map<String, Integer> getTriggeredServerEvents() {
        return triggeredServerEvents;
    }

    public ScriptType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
