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

package de.linzn.homeWebApp.api.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.linzn.homeWebApp.HomeWebAppPlugin;
import de.linzn.homeWebApp.api.jqueryContent.*;
import de.linzn.homeWebApp.api.postRequests.PostLightControl;
import de.linzn.homeWebApp.api.postRequests.StemControl;
import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.EmptyTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;
import de.stem.stemSystem.AppLogger;
import de.stem.stemSystem.utils.Color;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiHandler implements HttpHandler {

    private Map<String, IResponseHandler> subHandlers;

    public ApiHandler() {
        this.subHandlers = new LinkedHashMap<>();
        this.registerSubHandlers();
    }


    @Override
    public void handle(HttpExchange he) throws IOException {
        handleRequests(he);
    }

    private void handleRequests(final HttpExchange he) throws IOException {
        List<String> whitelist = HomeWebAppPlugin.homeWebAppPlugin.getDefaultConfig().getStringList("apiServer.whitelist");

        if (!whitelist.contains(he.getRemoteAddress().getAddress().getHostName())) {
            AppLogger.debug(Color.RED + "[WEBAPP_API-SERVER] Access deny for " + he.getRemoteAddress().getAddress().getHostName());
            he.close();
            return;
        }

        String url = he.getRequestURI().getRawPath();

        List<String> argsList = Arrays.stream(url.split("/")).filter(arg -> !arg.isEmpty()).collect(Collectors.toList());
        IHtmlTemplate iHtmlPage = new EmptyTemplate();

        if (!argsList.isEmpty()) {
            String command = argsList.get(0);
            if (this.subHandlers.containsKey(command.toLowerCase())) {
                iHtmlPage = this.subHandlers.get(command.toLowerCase()).buildResponse(argsList);
            }
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
        /* JQuery requests */
        this.subHandlers.put("div_heater-temp.html", new DivHeaterTemperature());
        this.subHandlers.put("div_network-ping.html", new DivNetworkPing());
        this.subHandlers.put("div_weather.html", new DivWeather());
        this.subHandlers.put("div_trash.html", new DivTrash());
        this.subHandlers.put("div_light-kitchen.html", new DivLightKitchen());
        this.subHandlers.put("div_light-aquarium.html", new DivLightAquarium());
        this.subHandlers.put("div_light-niklas.html", new DivLightNiklas());
        this.subHandlers.put("div_light-topstairs.html", new DivLightTreppeOben());
        this.subHandlers.put("div_stem.html", new DivStem());

        /* Post requests*/
        this.subHandlers.put("post_light-control", new PostLightControl());
        this.subHandlers.put("post_stem-control", new StemControl());

    }

}

