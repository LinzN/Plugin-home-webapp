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

package de.linzn.homeWebApp.api.jqueryContent;

import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.EmptyTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;
import de.linzn.serviceStatus.callbacks.PlexServerStatus;

import java.util.List;

public class DivServer implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {


        StringBuilder stringBuilder = new StringBuilder();

        boolean plexServer = PlexServerStatus.getPlexStatus();
        float serverTemp = 0f;

        EmptyTemplate emptyPage = new EmptyTemplate();

        stringBuilder.append("<div id=\"server\">" +
                "    <div class=\"cont\">" +
                "        <p>" +
                "            <h1>PLEX: " + (plexServer ? "<ok>ONLINE</ok>" : "<bad>OFFLINE</bad>") + "</h1></p>" +
                "    </div>" +
                "    <div class=\"cont\">" +
                "        <p>" +
                "            <h1>TEMP.: " + serverTemp + "°C</h1></p>" +
                "    </div>" +
                "</div>");

        emptyPage.setCode(stringBuilder.toString());
        return emptyPage;
    }
}
