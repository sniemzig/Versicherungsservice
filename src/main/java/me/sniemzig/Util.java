package me.sniemzig;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class Util {
    public static String readResource(String fileName) {
        try (InputStream in = Util.class.getClassLoader().getResourceAsStream(fileName)) {
            if (in == null)
                throw new IllegalArgumentException("Resource not found: " + fileName);
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
