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
import de.linzn.homeWebApp.mainServer.htmlTemplates.TerminalTemplate;
import de.linzn.homeWebApp.mainServer.htmlTemplates.frames.TerminalFrame;

import java.util.List;

public class LandscapeFrontTerminal implements IResponseHandler {
    private final String apiServer = "http://" + HomeWebAppPlugin.homeWebAppPlugin.apiWebserver.getHostname() + ":" + HomeWebAppPlugin.homeWebAppPlugin.apiWebserver.getPort();
    private TerminalTemplate webAppHTML;

    public LandscapeFrontTerminal() {
        staticBuild();
    }

    private void staticBuild() {
        webAppHTML = new TerminalTemplate();
        webAppHTML.setTitle("Terminal");

        webAppHTML.setFrame(new TerminalFrame("TERMINAL", "terminal", true, apiServer + "/div_terminal.html", 2000));

    }

    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {
        return this.webAppHTML;
    }
}
