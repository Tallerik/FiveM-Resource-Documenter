package de.tallerik.fivem.documenter;

import de.tallerik.fivem.documenter.reader.Reader;

public class Launcher {

    public static void main(String[] args) {
        String path = "D:\\fiveM\\server\\resources";
        new Reader(path);
    }
}
