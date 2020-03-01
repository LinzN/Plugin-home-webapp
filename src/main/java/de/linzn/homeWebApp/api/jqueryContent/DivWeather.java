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
import de.linzn.localWeather.LocalWeatherPlugin;
import de.linzn.localWeather.engine.WeatherContainer;

import java.util.List;

public class DivWeather implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {


        StringBuilder stringBuilder = new StringBuilder();

        String icon = "fas fa-sun";
        String description = "N.A";
        double current = -1;
        double min = -1;
        double max = -1;
        String location = "None";


        WeatherContainer weatherContainer = LocalWeatherPlugin.localWeatherPlugin.getWeatherData();

        if (weatherContainer != null) {
            int iconData = weatherContainer.getICON();

            if (iconData == 0) {
                icon = "fas fa-sun";
            } else if (iconData == 3) {
                icon = "fas fa-cloud-showers-heavy";
            } else if (iconData == 5) {
                icon = "fas fa-snowflake";
            } else if (iconData == 1) {
                icon = "fas fa-cloud";
            }

            description = weatherContainer.getWeatherDescription();
            current = weatherContainer.getTemp();
            min = weatherContainer.getTemp_min();
            max = weatherContainer.getTemp_max();
            location = weatherContainer.getLocation();
        }


        EmptyTemplate emptyPage = new EmptyTemplate();

        stringBuilder.append("<div id=\"weather\">" +
                "<div class=\"cont\">" +
                "        <h2><span aria-hidden=\"true\" class=\"" + icon + "\"></span><ok> " + current + " °C</ok></h2>" +
                "        <p>" + description + "</p>" +
                "        <p>" + min + " °C | " + max + " °C</p>" +
                "    </div>" +
                "    <div class=\"cont\">" +
                "        <p>" +
                "            <h1>" + location + "</h1></p>" +
                "</div>" +
                "</div>");

        emptyPage.setCode(stringBuilder.toString());
        return emptyPage;
    }
}
