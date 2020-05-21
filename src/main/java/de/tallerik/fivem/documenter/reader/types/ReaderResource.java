package de.tallerik.fivem.documenter.reader.types;

import de.tallerik.fivem.documenter.generator.types.LuaFile;
import de.tallerik.fivem.documenter.generator.types.ScriptType;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReaderResource implements ReaderObject {
    String path;
    String name;
    boolean res = true;
    File file;
    File manifest = null;
    List<LuaFile> clientLua = new ArrayList<>();
    List<LuaFile> serverLua = new ArrayList<>();
    List<LuaFile> sharedLua = new ArrayList<>();
    Map<String, File> dataFiles = new HashMap<>();
    Map<String, File> streamFiles = new HashMap<>();
    Map<String, String> properties = new HashMap<>();
    boolean serverOnly = false;


    public String getFullPath() {
        return path;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getManifest() {
        return manifest;
    }

    public List<LuaFile> getClientLua() {
        return clientLua;
    }

    public List<LuaFile> getServerLua() {
        return serverLua;
    }

    public List<LuaFile> getSharedLua() {
        return sharedLua;
    }

    public Map<String, File> getdataFiles() {
        return dataFiles;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public boolean propertiesHasKey(String key) {
        return getProperties().containsKey(key);
    }

    public boolean isServerOnly() {
        return serverOnly;
    }

    public Map<String, File> getStreamFiles() {
        return streamFiles;
    }

    public void process() {
        for (File f : file.listFiles()) {
            if(f.getName().equals("fxmanifest.lua") || f.getName().equals("__resource.lua")) {
                manifest = f;
                break;
            }
        }
        if(manifest != null) {
            readManifest(manifest);
        }
        File streamfolder = new File(file.getAbsolutePath() + File.separator + "stream");
        if(streamfolder.exists() && streamfolder.isDirectory()) {
            for (File f : streamfolder.listFiles()) {
                streamFiles.put(f.getName(), f);
            }
        }
    }

    private void readManifest(File manifest) {
        String content = readFile(manifest.getAbsolutePath(), Charset.defaultCharset());
        String[] lines = content.split("\\r?\\n");
        String bracket = null;
        String basePath = manifest.getParentFile().getAbsolutePath();
        String sep = File.separator;
        for (String line : lines) {

            // fx_version
            if(line.startsWith("fx_version")) {
                if(line.split("'").length > 1) {
                    properties.put("manifest_version", line.split("'")[1]);
                } else if(line.split("\"").length > 1) {
                    properties.put("manifest_version", line.split("\"")[1]);
                }
            }

            // resource_manifest_version
            if(line.startsWith("resource_manifest_version")) {
                if(line.split("'").length > 1) {
                    properties.put("manifest_version", line.split("'")[1]);
                } else if(line.split("\"").length > 1) {
                    properties.put("manifest_version", line.split("\"")[1]);
                }
            }

            // author
            if(line.startsWith("author")) {
                if(line.split("'").length > 1) {
                    properties.put("author", line.split("'")[1]);
                } else if(line.split("\"").length > 1) {
                    properties.put("author", line.split("\"")[1]);
                }
            }

            // description
            if(line.startsWith("description")) {
                if(line.split("'").length > 1) {
                    properties.put("description", line.split("'")[1]);
                } else if(line.split("\"").length > 1) {
                    properties.put("description", line.split("\"")[1]);
                }
            }

            // data file
            if(line.startsWith("data_file")) {
                if(line.split("'").length > 1) {
                    dataFiles.put(line.split("'")[1], new File(basePath + sep + line.split("'")[3]));
                } else if(line.split("\"").length > 1) {
                    dataFiles.put(line.split("\"")[1], new File(basePath + sep + line.split("'")[3]));
                }
            }

            // client_script
            if(line.startsWith("client_scripts ")) {
                if(line.contains("{")) {
                    bracket = "client";
                } else {
                    if(line.contains("*") || !line.contains(".lua")) {
                        continue;  // TODO: Workaround for this
                    }
                    if(line.split("'").length > 1) {
                        clientLua.add(new LuaFile(basePath + sep + line.split("'")[1], ScriptType.CLIENT_SCRIPT));
                    } else if(line.split("\"").length > 1) {
                        clientLua.add(new LuaFile(basePath + sep + line.split("\"")[1], ScriptType.CLIENT_SCRIPT));
                    }
                }
            }

            // server_script
            if(line.startsWith("server_script ")) {
                if(line.contains("{")) {
                    bracket = "server";
                } else {
                    if(line.contains("*") || !line.contains(".lua")) {
                        continue;  // TODO: Workaround for this
                    }
                    if(line.split("'").length > 1) {
                        serverLua.add(new LuaFile(basePath + sep + line.split("'")[1], ScriptType.SERVER_SCRIPT));
                    } else if(line.split("\"").length > 1) {
                        serverLua.add(new LuaFile(basePath + sep + line.split("\"")[1], ScriptType.SERVER_SCRIPT));
                    }
                }
            }

            // shared_script
            if(line.startsWith("shared_script ")) {
                if(line.contains("{")) {
                    bracket = "shared";
                } else {
                    if(line.contains("*") || !line.contains(".lua")) {
                        continue;  // TODO: Workaround for this
                    }
                    if(line.split("'").length > 1) {
                        sharedLua.add(new LuaFile(basePath + sep + line.split("'")[1], ScriptType.SHARED_SCRIPT));
                    } else if(line.split("\"").length > 1) {
                        sharedLua.add(new LuaFile(basePath + sep + line.split("\"")[1], ScriptType.SHARED_SCRIPT));
                    }
                }
            }

            // In bracket
            if(line.startsWith("'") || line.startsWith("\"")) {
                if(bracket == null) {
                    continue;
                }
                if(!line.contains(".lua")) {
                    continue;
                }
                String name = "";
                if(line.split("'").length > 1) {
                    name = line.split("'")[1];
                } else if(line.split("\"").length > 1) {
                    name = line.split("\"")[1];
                }
                if(name.equals("") || name.contains("*")) {
                    continue;
                }
                File file = new File(basePath + sep + name);
                if(bracket.equals("client")) {
                    clientLua.add(new LuaFile(file, ScriptType.CLIENT_SCRIPT));
                } else if(bracket.equals("server")) {
                    serverLua.add(new LuaFile(file, ScriptType.SERVER_SCRIPT));
                } else if(bracket.equals("shared")) {
                    sharedLua.add(new LuaFile(file, ScriptType.SHARED_SCRIPT));
                }
            }

            // }
            if(line.startsWith("}")) {
                bracket = null;
                continue;
            }

            if(line.contains("server_only")) {
                serverOnly = true;
                continue;
            }
        }
    }

    // Adapted from https://stackoverflow.com/a/326440
    static String readFile(String path, Charset encoding) {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded, encoding);
    }



}
