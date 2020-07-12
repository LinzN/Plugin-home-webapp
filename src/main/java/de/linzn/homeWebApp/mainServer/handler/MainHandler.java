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

package de.linzn.homeWebApp.mainServer.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.linzn.homeWebApp.HomeWebAppPlugin;
import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;
import de.linzn.homeWebApp.mainServer.frontEnd.landscape.LandscapeFrontDashboard;
import de.linzn.homeWebApp.mainServer.frontEnd.landscape.LandscapeFrontSetting;
import de.linzn.homeWebApp.mainServer.frontEnd.landscape.LandscapeFrontTerminal;
import de.linzn.homeWebApp.mainServer.frontEnd.landscape.SmartPhoneNiklas;
import de.linzn.openJL.network.IPAddressMatcher;
import de.stem.stemSystem.AppLogger;
import de.stem.stemSystem.utils.Color;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainHandler implements HttpHandler {

    private final Map<String, IResponseHandler> subHandlers;

    public MainHandler() {
        this.subHandlers = new LinkedHashMap<>();
        this.registerSubHandlers();
    }


    @Override
    public void handle(HttpExchange he) throws IOException {
        handleRequests(he);
    }

    private void handleRequests(final HttpExchange he) throws IOException {
        List<String> whitelist = HomeWebAppPlugin.homeWebAppPlugin.getDefaultConfig().getStringList("mainServer.whitelist");
        String requestingAddress = he.getRemoteAddress().getAddress().getHostName();

        boolean matched = false;
        for (String ip : whitelist) {
            if (new IPAddressMatcher(ip).matches(requestingAddress)) {
                matched = true;
                break;
            }
        }

        if (!matched) {
            AppLogger.debug(Color.RED + "[WEBAPP_MAIN-SERVER] Access deny for " + requestingAddress);
            he.close();
            return;
        }


        String url = he.getRequestURI().getRawPath();

        List<String> argsList = Arrays.stream(url.split("/")).filter(arg -> !arg.isEmpty()).collect(Collectors.toList());
        IHtmlTemplate iHtmlPage = null;

        if (!argsList.isEmpty()) {
            String command = argsList.get(0);
            if (this.subHandlers.containsKey(command.toLowerCase())) {
                iHtmlPage = this.subHandlers.get(command.toLowerCase()).buildResponse(argsList);
            }
        } else {
            iHtmlPage = this.subHandlers.get("dashboard.html").buildResponse(argsList);
        }

        if (iHtmlPage == null) {
            iHtmlPage = this.subHandlers.get("__resource_dir__").buildResponse(argsList);
        }

        iHtmlPage.generate();

        Headers h = he.getResponseHeaders();

        for (String key : iHtmlPage.headerList().keySet()) {
            h.set(key, iHtmlPage.headerList().get(key));
        }

        he.sendResponseHeaders(200, iHtmlPage.length());
        OutputStream os = he.getResponseBody();
        os.write(iHtmlPage.getBytes());
        os.close();
    }

    private void registerSubHandlers() {
        this.subHandlers.put("__resource_dir__", new ResourceHandler());

        /* Frontend  LS*/
        this.subHandlers.put("dashboard.html", new LandscapeFrontDashboard());
        this.subHandlers.put("niklas.html", new SmartPhoneNiklas());
        this.subHandlers.put("settings.html", new LandscapeFrontSetting());
        this.subHandlers.put("terminal.html", new LandscapeFrontTerminal());
    }

}

