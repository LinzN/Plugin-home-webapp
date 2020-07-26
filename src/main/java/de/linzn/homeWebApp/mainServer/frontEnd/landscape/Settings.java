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
import de.linzn.homeWebApp.mainServer.htmlTemplates.frames.MenuFrame;

import java.util.List;

public class Settings implements IResponseHandler {
    private final String apiServer = "http://" + HomeWebAppPlugin.homeWebAppPlugin.apiWebserver.getHostname() + ":" + HomeWebAppPlugin.homeWebAppPlugin.apiWebserver.getPort();
    private WebAppTemplate webAppHTML;

    public Settings() {
        staticBuild();
    }

    private void staticBuild() {
        webAppHTML = new WebAppTemplate(2, 4);
        webAppHTML.setTitle("Settings");

        webAppHTML.addEntry(new DefaultFrame("STEM", "stem", true, apiServer + "/div_stem.html", 120000), 0, 0);
        webAppHTML.addEntry(new DefaultFrame("CPU Load", "cpu", true, apiServer + "/div_cpu.html", 2000), 0, 1);

        webAppHTML.addEntry(new DefaultFrame(new MenuFrame()), 1, 3);

    }

    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {
        return this.webAppHTML;
    }
}
