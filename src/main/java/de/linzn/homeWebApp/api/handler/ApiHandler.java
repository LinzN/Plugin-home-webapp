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
import de.linzn.homeWebApp.api.jsonapi.get.*;
import de.linzn.homeWebApp.api.jsonapi.post.ChangeDeviceJSON;
import de.linzn.homeWebApp.api.legacyjquery.info.DivHeaterTemperature;
import de.linzn.homeWebApp.api.legacyjquery.info.DivNetworkPing;
import de.linzn.homeWebApp.api.legacyjquery.info.DivTrash;
import de.linzn.homeWebApp.api.legacyjquery.info.DivWeather;
import de.linzn.homeWebApp.api.legacyjquery.light.DivLightAquarium;
import de.linzn.homeWebApp.api.legacyjquery.light.DivLightKitchen;
import de.linzn.homeWebApp.api.legacyjquery.light.DivLightNiklas;
import de.linzn.homeWebApp.api.legacyjquery.light.DivLightTreppeOben;
import de.linzn.homeWebApp.api.legacyjquery.postRequests.PostLightControl;
import de.linzn.homeWebApp.api.legacyjquery.postRequests.StemControl;
import de.linzn.homeWebApp.api.legacyjquery.system.DivCPU;
import de.linzn.homeWebApp.api.legacyjquery.system.DivServer;
import de.linzn.homeWebApp.api.legacyjquery.system.DivStem;
import de.linzn.homeWebApp.api.legacyjquery.system.DivTerminal;
import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.EmptyTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;
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

public class ApiHandler implements HttpHandler {

    private final Map<String, IResponseHandler> subHandlers;

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
        String requestingAddress = he.getRemoteAddress().getAddress().getHostName();

        boolean matched = false;
        for (String ip : whitelist) {
            if (new IPAddressMatcher(ip).matches(requestingAddress)) {
                matched = true;
                break;
            }
        }

        if (!matched) {
            AppLogger.debug(Color.RED + "[WEBAPP_API-SERVER] Access deny for " + requestingAddress);
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
        this.subHandlers.put("div_server.html", new DivServer());
        this.subHandlers.put("div_stem.html", new DivStem());
        this.subHandlers.put("div_cpu.html", new DivCPU());
        this.subHandlers.put("div_terminal.html", new DivTerminal());

        /* Post requests*/
        this.subHandlers.put("post_light-control", new PostLightControl());
        this.subHandlers.put("post_stem-control", new StemControl());

        /* JSON GET API */
        this.subHandlers.put("json_terminal", new TerminalJSON());
        this.subHandlers.put("json_weather", new WeatherJSON());
        this.subHandlers.put("json_resources", new ResourcesJSON());
        this.subHandlers.put("json_device-status", new DeviceStatusJSON());
        this.subHandlers.put("json_reminder", new ReminderJSON());
        this.subHandlers.put("json_notification", new NotificationJSON());
        this.subHandlers.put("json_heater-data", new HeaterDataJSON());
        this.subHandlers.put("json_trash-calendar", new TrashCalendarJSON());

        /* JSON PUSH API */
        this.subHandlers.put("post_change-device-status", new ChangeDeviceJSON());
    }

}

