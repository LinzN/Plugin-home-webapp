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

import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.EmptyTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;
import de.stem.stemSystem.utils.JavaUtils;

import java.util.List;

public class DivCPU implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {


        StringBuilder stringBuilder = new StringBuilder();

        double load = JavaUtils.getSystemLoad();
        int cpuload = (int) (load * 100);


        String color = "green";
        if (cpuload > 40 && cpuload < 70) {
            color = "yellow";
        } else if (cpuload > 70) {
            color = "red";
        }

        stringBuilder.append("<div id=\"cpu\">" +
                "    <div class=\"cont\">" +
                "       <div class=\"progress\" style=\" margin: 5%;\">\n" +
                "        <div class=\"progress-bar\" role=\"progressbar\" aria-valuenow=\"" + cpuload + "\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: " + cpuload + "%;background-color: " + color + ";\">" + cpuload + "%</div>\n" +
                "      </div>" +
                "    </div>" +
                "</div>");

        EmptyTemplate emptyPage = new EmptyTemplate();

        emptyPage.setCode(stringBuilder.toString());
        return emptyPage;
    }
}
