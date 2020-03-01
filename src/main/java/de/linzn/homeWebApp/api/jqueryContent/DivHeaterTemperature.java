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
import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.EmptyTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;

import java.text.DecimalFormat;
import java.util.List;

public class DivHeaterTemperature implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {
        List<Inlet> inlets = HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getInletsList();
        String value = "0.0";
        for (Inlet inlet : inlets) {
            if (inlet.getIndex() == 3) {
                value = new DecimalFormat("#.#").format(inlet.getValue());
                break;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<div id=\"heater-temp\">" +
                "<h2><span aria-hidden=\"true\" class=\"fas fa-thermometer-half fs2\"></span>" + value + " °C</h2>" +
                "<div class=\"cont\">" +
                "<p>Wasser Temp.</p>" +
                "</div>" +
                "</div>");

        EmptyTemplate emptyPage = new EmptyTemplate();

        emptyPage.setCode(stringBuilder.toString());
        return emptyPage;
    }
}
