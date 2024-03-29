package com.erlis.servlet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

class Util {

    /**
     * @param is inputStream
     * @return String
     */
    public static String readStream(InputStream is) {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
        return buffer.lines().collect(Collectors.joining(System.lineSeparator()));
    }

}
