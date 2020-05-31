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
import de.linzn.serviceStatus.callbacks.ProxmoxStatus;
import de.linzn.serviceStatus.callbacks.SolarStatus;
import de.linzn.systemChain.callbacks.TemperatureScheduler;

import java.util.List;

public class DivServer implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {


        StringBuilder stringBuilder = new StringBuilder();

        boolean solarBerry = SolarStatus.getSolarStatus();
        boolean plexServer = PlexServerStatus.getPlexStatus();
        boolean proxmox = ProxmoxStatus.getProxmoxStatus();
        double serverTemp = TemperatureScheduler.getHottestCore();

        String solarStatus = "<bad>OFFLINE</bad>";
        if (solarBerry) {
            solarStatus = "<ok>ONLINE </ok>";
        }

        String plexStatus = "<bad>OFFLINE</bad>";
        if (plexServer) {
            plexStatus = "<ok>ONLINE </ok>";
        }

        String proxmoxStatus = "<bad>OFFLINE</bad>";
        if (proxmox) {
            proxmoxStatus = "<ok>ONLINE </ok>";
        }

        String tempStatus = "<ok>" + serverTemp + "°C</ok>";


        EmptyTemplate emptyPage = new EmptyTemplate();

        stringBuilder.append("<div id=\"server\">" +
                "    <div class=\"cont\">" +
                "        <h2 style=\"font-size: 22px;font-weight: normal;\">SOLAR | " + solarStatus + "</h2>" +
                "        <h2 style=\"font-size: 22px;font-weight: normal;\">PLEX | " + plexStatus + "</h2>" +
                "        <h2 style=\"font-size: 22px;font-weight: normal;\">PROX | " + proxmoxStatus + "</h2>" +
                "        <h2 style=\"font-size: 22px;font-weight: normal;\">CORE | " + tempStatus + "</h2>" +
                "    </div>" +
                "</div>");

        emptyPage.setCode(stringBuilder.toString());
        return emptyPage;
    }
}
