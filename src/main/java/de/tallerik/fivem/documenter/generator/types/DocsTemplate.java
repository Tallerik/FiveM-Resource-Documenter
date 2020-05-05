package de.tallerik.fivem.documenter.generator.types;

public class DocsTemplate {
    String doc;
    String nav;
    String navSub;
    public DocsTemplate(String doc, String nav, String navSub) {
        this.doc = doc;
        this.nav = nav;
        this.navSub = navSub;
    }
    public String getDoc() {
        return doc;
    }
    public String getNav() {
        return nav;
    }
    public String getNavSub() {
        return navSub;
    }
}
