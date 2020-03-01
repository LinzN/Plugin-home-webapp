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
import de.linzn.heatingstatus.objects.Notify;
import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.EmptyTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;

import java.util.ArrayList;
import java.util.List;

public class DivHeaterNotifies implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {
        List<Notify> notifies = HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getNotifiesList();


        StringBuilder stringBuilder = new StringBuilder();

        List<Notify> activeNotifies = new ArrayList<>();

        for (Notify notify : notifies) {
            if (notify.isActive()) {
                activeNotifies.add(notify);
            }
        }

        stringBuilder.append("<div id=\"heater-notifies\">");

        if (activeNotifies.isEmpty()) {
            stringBuilder.append("<h2><span aria-hidden=\"true\" class=\"fas fa-check-circle fs2\"></span></h2>" +
                    "<div class=\"cont\">" +
                    "<h2><ok>Keine</ok></h2>" +
                    "</div>");
        } else {
            stringBuilder.append("<h2><span aria-hidden=\"true\" class=\"fas fa-exclamation-triangle fs2\"></span></h2>\n" +
                    "    <div class=\"cont\">");
            for (Notify notify : activeNotifies) {
                stringBuilder.append("<h2><bad>").append(notify.getName()).append("</bad></h2>");
            }
            stringBuilder.append("</div>");
        }
        stringBuilder.append("</div>");

        EmptyTemplate emptyPage = new EmptyTemplate();

        emptyPage.setCode(stringBuilder.toString());
        return emptyPage;
    }
}
