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
import de.linzn.localWeather.LocalWeatherPlugin;
import de.linzn.localWeather.engine.WeatherContainer;

import java.util.Date;
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
        double humidity = 0;
        double pressure = 0;
        String dateString = "N.A";


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
            pressure = weatherContainer.getPressure();
            humidity = weatherContainer.getHumidity();

            int dateInt = (int) ((new Date().getTime() - weatherContainer.getDate().getTime()) / (1000 * 60));

            if (dateInt == 0) {
                dateInt = (int) ((new Date().getTime() - weatherContainer.getDate().getTime()) / (1000));
                dateString = "Last sync: " + dateInt + " Seconds ago";
            } else {
                dateString = "Last sync: " + dateInt + " Minutes ago";
            }
        }


        EmptyTemplate emptyPage = new EmptyTemplate();

        stringBuilder.append("<div id=\"weather\">" +
                "<div class=\"cont\">" +
                "        <h2><span aria-hidden=\"true\" class=\"" + icon + "\"></span><ok> " + current + " °C</ok></h2>" +
                "        <p>" + description + "</p>" +
                "        <p>" + min + " °C | " + max + " °C</p>" +
                "        <p>" + pressure + " hPa | " + humidity + " %</p>" +
                "    </div>" +
                "    <div class=\"cont\">" +
                "            <h1>" + location + "</h1>" +
                "            <h5>" + dateString + "</h5>" +
                "</div>" +
                "</div>");

        emptyPage.setCode(stringBuilder.toString());
        return emptyPage;
    }
}
