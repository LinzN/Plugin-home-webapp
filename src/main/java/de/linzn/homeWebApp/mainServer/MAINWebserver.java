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

package de.linzn.homeWebApp.mainServer;


import com.sun.net.httpserver.HttpServer;
import de.linzn.homeWebApp.HomeWebAppPlugin;
import de.linzn.homeWebApp.mainServer.handler.MainHandler;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class MAINWebserver {

    private HttpServer mainServer;
    private File webResourcesFolder;
    private String hostname;
    private int port;

    public MAINWebserver(String host, int port) {
        this.hostname = host;
        this.port = port;
        try {
            mainServer = HttpServer.create(new InetSocketAddress(host, port), 0);
            mainServer.createContext("/", new MainHandler());
            mainServer.setExecutor(Executors.newSingleThreadExecutor());
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void start() {
        webResourcesFolder = new File(HomeWebAppPlugin.homeWebAppPlugin.getDataFolder(), "www");
        if (!webResourcesFolder.exists()) {
            if (!HomeWebAppPlugin.homeWebAppPlugin.getDataFolder().exists()) {
                HomeWebAppPlugin.homeWebAppPlugin.getDataFolder().mkdir();
            }
            webResourcesFolder.mkdir();
        }
        this.mainServer.start();
    }

    public void stop() {
        this.mainServer.stop(0);
    }

    public File getWebResourcesFolder() {
        return this.webResourcesFolder;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }
}
