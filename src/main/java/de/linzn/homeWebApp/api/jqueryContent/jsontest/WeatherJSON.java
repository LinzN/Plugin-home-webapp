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

package de.linzn.homeWebApp.api.jqueryContent.jsontest;

import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.JSONTemplate;
import de.linzn.localWeather.LocalWeatherPlugin;
import de.linzn.localWeather.engine.WeatherContainer;
import org.json.JSONObject;

import java.util.List;

public class WeatherJSON implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {

        int weatherID = -1;
        String description = "N.A";
        double current = -1;
        double minTemp = -1;
        double maxTemp = -1;
        String location = "None";
        double humidity = 0;
        double pressure = 0;


        WeatherContainer weatherContainer = LocalWeatherPlugin.localWeatherPlugin.getWeatherData();

        if (weatherContainer != null) {
            weatherID = weatherContainer.getICON();
            description = weatherContainer.getWeatherDescription();
            current = weatherContainer.getTemp();
            minTemp = weatherContainer.getTemp_min();
            maxTemp = weatherContainer.getTemp_max();
            location = weatherContainer.getLocation();
            pressure = weatherContainer.getPressure();
            humidity = weatherContainer.getHumidity();
        }

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("weatherID", weatherID);
        jsonObject.put("description", description);
        jsonObject.put("currentTemp", current);
        jsonObject.put("minTemp", minTemp);
        jsonObject.put("maxTemp", maxTemp);
        jsonObject.put("location", location);
        jsonObject.put("pressure", pressure);
        jsonObject.put("humidity", humidity);


        JSONTemplate emptyPage = new JSONTemplate();

        emptyPage.setCode(jsonObject);
        return emptyPage;
    }
}
