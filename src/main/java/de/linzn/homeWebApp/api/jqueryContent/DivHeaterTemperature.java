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


import de.linzn.heatingstatus.HeatingStatusPlugin;
import de.linzn.heatingstatus.objects.Inlet;
import de.linzn.heatingstatus.objects.Outlet;
import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.EmptyTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;

import java.text.DecimalFormat;
import java.util.List;

public class DivHeaterTemperature implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {
        List<Inlet> inlets = HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getInletsList();
        List<Outlet> outlets = HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getOutletsList();
        String value = "0.0";
        boolean isBurning = false;
        for (Inlet inlet : inlets) {
            if (inlet.getIndex() == 3) {
                value = new DecimalFormat("#.#").format(inlet.getValue());
                break;
            }
        }

        for (Outlet outlet : outlets) {
            if (outlet.getIndex() == 1) {
                isBurning = outlet.isActive();
            }
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<div id=\"heater-temp\">" +
                "<h2><span aria-hidden=\"true\" class=\"fas fa-thermometer-half fs2\"></span>" + value + " °C</h2>" +
                "<div class=\"cont\">" +
                "<p>Wasser Temp.</p>");

        if (isBurning) {
            stringBuilder.append("<span aria-hidden=\"true\" class=\"fas fa-burn fs2\" style=\"color: orange;\"></span>");
        }
        stringBuilder.append(
                "</div>" +
                        "</div>");

        EmptyTemplate emptyPage = new EmptyTemplate();

        emptyPage.setCode(stringBuilder.toString());
        return emptyPage;
    }
}
