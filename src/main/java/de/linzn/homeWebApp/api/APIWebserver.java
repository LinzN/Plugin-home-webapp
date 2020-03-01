/*
 * Copyright (C) 2020. Niklas Linz - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.homeWebApp.api;


import com.sun.net.httpserver.HttpServer;
import de.linzn.homeWebApp.api.handler.ApiHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class APIWebserver {

    private HttpServer apiServer;
    private String hostname;
    private int port;

    public APIWebserver(String host, int port) {
        this.hostname = host;
        this.port = port;
        try {
            apiServer = HttpServer.create(new InetSocketAddress(host, port), 0);
            apiServer.createContext("/", new ApiHandler());
            apiServer.setExecutor(Executors.newSingleThreadExecutor());
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void start() {
        this.apiServer.start();
    }

    public void stop() {
        this.apiServer.stop(0);
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }
}
