package de.tallerik.fivem.documenter;

import de.tallerik.fivem.documenter.reader.Reader;
import de.tallerik.fivem.documenter.reader.types.ReaderPackage;

public class Launcher {
    public static ReaderPackage res;
    public static void main(String[] args) {
        String path = "D:\\fiveM\\server\\resources";
        new Reader(path);
    }
}
