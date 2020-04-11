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

import java.util.List;

public class SmartPhoneNiklas implements IResponseHandler {
    private String apiServer = "http://" + HomeWebAppPlugin.homeWebAppPlugin.apiWebserver.getHostname() + ":" + HomeWebAppPlugin.homeWebAppPlugin.apiWebserver.getPort();
    private WebAppTemplate webAppHTML;

    public SmartPhoneNiklas() {
        staticBuild();
    }

    private void staticBuild() {
        webAppHTML = new WebAppTemplate(1, 1);
        webAppHTML.setTitle("Smartphone Niklas");

        HalfFrame halfFrame1 = new HalfFrame("Niklas", "light-niklas", true, apiServer + "/div_light-niklas.html", 1500);
        HalfFrame halfFrame2 = new HalfFrame("Treppe Oben", "light-topstairs", true, apiServer + "/div_light-topstairs.html", 1500);
        webAppHTML.addEntry(new DefaultFrame(halfFrame1, halfFrame2), 0, 0);
    }

    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {
        return this.webAppHTML;
    }
}
