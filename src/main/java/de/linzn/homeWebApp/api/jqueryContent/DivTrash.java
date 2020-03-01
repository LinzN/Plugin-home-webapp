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
import de.linzn.trashCalender.TrashCalenderPlugin;
import de.linzn.trashCalender.objects.*;

import java.util.Date;
import java.util.List;

public class DivTrash implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {


        StringBuilder stringBuilder = new StringBuilder();


        List<ITrash> iTrashList = TrashCalenderPlugin.trashCalenderPlugin.getTrashCalendar().getTrashList(new Date());
        stringBuilder.append("<div id=\"trash\">");
        for (ITrash iTrash : iTrashList) {
            String color = "white";
            String name = iTrash.getName();
            String datum = iTrash.getDayName();

            if (iTrash instanceof BlackTrash) {
                color = "grey";
            } else if (iTrash instanceof GreenTrash) {
                color = "green";
            } else if (iTrash instanceof YellowTrash) {
                color = "yellow";
            } else if (iTrash instanceof BlueTrash) {
                color = "blue";
            }
            stringBuilder.append("" +
                    "<div class=\"cont\">" +
                    "        <i class=\"far fa-trash-alt fs2\" style=\"color: " + color + ";\"><h1>" + name + "</h1></i>" +
                    "        <p>" + datum + "</p>" +
                    "</div>\n");

        }

        stringBuilder.append("</div>\n");

        EmptyTemplate emptyPage = new EmptyTemplate();

        emptyPage.setCode(stringBuilder.toString());
        return emptyPage;
    }
}
