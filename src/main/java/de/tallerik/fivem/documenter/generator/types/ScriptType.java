package de.tallerik.fivem.documenter.generator.types;

public enum ScriptType {
    SERVER_SCRIPT("server_script"), CLIENT_SCRIPT("client_scripts"), SHARED_SCRIPT("shared_script");
    private String fxname;
    ScriptType(String fxname) {
        this.fxname = fxname;
    }
    public String getFxname() {
        return fxname;
    }
}
