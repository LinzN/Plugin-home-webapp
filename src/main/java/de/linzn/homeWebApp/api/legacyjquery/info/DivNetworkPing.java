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

package de.linzn.homeWebApp.api.legacyjquery.info;


import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.EmptyTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;
import de.linzn.systemChain.callbacks.NetworkScheduler;

import java.text.DecimalFormat;
import java.util.List;

public class DivNetworkPing implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {


        StringBuilder stringBuilder = new StringBuilder();
        float ping = NetworkScheduler.getLastPing();

        String network;
        if (ping <= 0) {
            network = "<bad>Offline</bad>";
        } else {
            if (ping >= 100) {
                network = "<bad>" + new DecimalFormat("#.#").format(ping) + "ms</bad>";
            } else {
                network = "<ok>" + new DecimalFormat("#.#").format(ping) + "ms</ok>";
            }
        }

        stringBuilder.append("<div id=\"network-ping\">" +
                "    <div class=\"cont\">" +
                "        <p>\n" +
                "            <h2><span aria-hidden=\"true\" class=\"fas fa-wifi fs2\"></span> | " + network + "</h2></p>" +
                "    </div>" +
                "</div>");

        EmptyTemplate emptyPage = new EmptyTemplate();

        emptyPage.setCode(stringBuilder.toString());
        return emptyPage;
    }
}
