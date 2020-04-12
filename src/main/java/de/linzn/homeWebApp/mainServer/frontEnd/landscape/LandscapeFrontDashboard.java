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

package de.linzn.homeWebApp.mainServer.frontEnd.landscape;

import de.linzn.homeWebApp.HomeWebAppPlugin;
import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;
import de.linzn.homeWebApp.mainServer.htmlTemplates.WebAppTemplate;
import de.linzn.homeWebApp.mainServer.htmlTemplates.frames.DefaultFrame;
import de.linzn.homeWebApp.mainServer.htmlTemplates.frames.HalfFrame;
import de.linzn.homeWebApp.mainServer.htmlTemplates.frames.MenuFrame;

import java.util.List;

public class LandscapeFrontDashboard implements IResponseHandler {

    private WebAppTemplate webAppHTML;
    private String apiServer = "http://" + HomeWebAppPlugin.homeWebAppPlugin.apiWebserver.getHostname() + ":" + HomeWebAppPlugin.homeWebAppPlugin.apiWebserver.getPort();

    public LandscapeFrontDashboard() {
        staticBuild();
    }

    private void staticBuild() {
        webAppHTML = new WebAppTemplate(2, 4);
        webAppHTML.setTitle("Dashboard");

        webAppHTML.addEntry(new DefaultFrame("Heizung", "heater-temp", true, apiServer + "/div_heater-temp.html", 5000), 0, 0);
        webAppHTML.addEntry(new DefaultFrame("Wetter", "weather", true, apiServer + "/div_weather.html", 15000), 0, 1);
        String customCode = "<div id=\"clock\">\n" +
                "    <div class=\"clockcenter\">\n" +
                "        <h5><div id=\"date\"></div></h5>\n" +
                "        <digiclock>\n" +
                "            <div id=\"time\"></div>\n" +
                "        </digiclock>\n" +
                "        <script type=\"text/javascript\" src=\"js/clock.js\"></script>\n" +
                "    </div>\n" +
                "</div>";

        HalfFrame halfFrame1 = new HalfFrame("Uhrzeit", customCode);
        HalfFrame halfFrame2 = new HalfFrame("Internet", "network-ping", true, apiServer + "/div_network-ping.html", 5000);
        webAppHTML.addEntry(new DefaultFrame(halfFrame1, halfFrame2), 0, 3);

        webAppHTML.addEntry(new DefaultFrame("Müllabfur", "trash", true, apiServer + "/div_trash.html", 20000), 1, 0);

        halfFrame1 = new HalfFrame("Küche", "light-kitchen", true, apiServer + "/div_light-kitchen.html", 500);
        halfFrame2 = new HalfFrame("Aquarium", "light-aquarium", true, apiServer + "/div_light-aquarium.html", 500);
        webAppHTML.addEntry(new DefaultFrame(halfFrame1, halfFrame2), 1, 1);

        halfFrame1 = new HalfFrame("Treppe Oben", "light-topstairs", true, apiServer + "/div_light-topstairs.html", 500);
        webAppHTML.addEntry(new DefaultFrame(halfFrame1, null), 1, 2);


        webAppHTML.addEntry(new DefaultFrame(new MenuFrame()), 1, 3);

    }

    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {
        return this.webAppHTML;
    }
}
