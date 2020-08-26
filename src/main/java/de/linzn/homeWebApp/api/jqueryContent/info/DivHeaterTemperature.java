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

package de.linzn.homeWebApp.api.jqueryContent.info;


import de.linzn.heatingstatus.HeatingStatusPlugin;
import de.linzn.heatingstatus.objects.Inlet;
import de.linzn.heatingstatus.objects.Notify;
import de.linzn.heatingstatus.objects.Outlet;
import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.EmptyTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DivHeaterTemperature implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {
        List<Inlet> inlets = HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getInletsList();
        List<Outlet> outlets = HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getOutletsList();
        List<Notify> notifies = HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getNotifiesList();

        String dateString = "N.A";
        String value = "0.0";

        Date date = HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getDate();
        if (date != null) {
            int dateInt = (int) ((new Date().getTime() - date.getTime()) / (1000 * 60));

            if (dateInt == 0) {
                dateInt = (int) ((new Date().getTime() - date.getTime()) / (1000));
                dateString = "Last sync: " + dateInt + " Seconds ago";
            } else {
                dateString = "Last sync: " + dateInt + " Minutes ago";
            }
        }


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

        List<Notify> activeNotifies = new ArrayList<>();

        for (Notify notify : notifies) {
            if (notify.isActive()) {
                activeNotifies.add(notify);
            }
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<div id=\"heater-temp\">" +
                "<div class=\"cont\">" +
                "<h2><span aria-hidden=\"true\" class=\"fas fa-thermometer-half fs2\"></span>" + value + " °C</h2>" +
                "<p>Warm-Wasser</p></div>");

        if (!activeNotifies.isEmpty()) {
            for (Notify notify : activeNotifies) {
                stringBuilder.append("<div class=\"cont\">");
                stringBuilder.append("<span aria-hidden=\"true\" class=\"fas fa-exclamation-triangle fs2\" style=\"color: red; font-size: 35px;\"></span>");
                stringBuilder.append("<p><h1><bad><b>" + notify.getName() + "</b></bad></h1></p>");
            }
        } else if (isBurning) {
            stringBuilder.append("<div class=\"cont\">");
            stringBuilder.append("<span aria-hidden=\"true\" class=\"fas fa-burn fs2\" style=\"color: orange; font-size: 45px;\"></span>");
            stringBuilder.append("<h4>Aufheiz-Phase</h4>");
        } else {
            stringBuilder.append("<div class=\"cont\">");
            stringBuilder.append("<ok><span aria-hidden=\"true\" class=\"fas fa-check-circle fs2\" style=\"font-size: 45px;\"></span></ok>");
            stringBuilder.append("<h4>Schlaf-Modus</h4>");
        }
        stringBuilder.append("<h5>" + dateString + "</h5>");
        stringBuilder.append(
                "</div>" +
                        "</div>");

        EmptyTemplate emptyPage = new EmptyTemplate();

        emptyPage.setCode(stringBuilder.toString());
        return emptyPage;
    }
}
