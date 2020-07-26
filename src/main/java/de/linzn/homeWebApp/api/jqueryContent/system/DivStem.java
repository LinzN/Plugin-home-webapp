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

package de.linzn.homeWebApp.api.jqueryContent.system;

import de.linzn.homeWebApp.HomeWebAppPlugin;
import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.EmptyTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;

import java.util.List;

public class DivStem implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {


        StringBuilder stringBuilder = new StringBuilder();

        String url = "http://" + HomeWebAppPlugin.homeWebAppPlugin.apiWebserver.getHostname() + ":" + HomeWebAppPlugin.homeWebAppPlugin.apiWebserver.getPort();

        stringBuilder.append("<div id=\"stem\">");
        stringBuilder.append("" +
                "<div class=\"cont\">" +
                "<button onclick=\"$.post('" + url + "/post_stem-control/restart')\" class=\"fas fa-fire-extinguisher tbutton\"></button>" +
                "        <p>Kill STEM</p>" +
                "</div>\n");

        stringBuilder.append("</div>\n");

        EmptyTemplate emptyPage = new EmptyTemplate();

        emptyPage.setCode(stringBuilder.toString());
        return emptyPage;
    }
}