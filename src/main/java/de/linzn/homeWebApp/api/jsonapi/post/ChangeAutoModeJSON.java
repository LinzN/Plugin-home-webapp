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

package de.linzn.homeWebApp.api.jsonapi.post;


import de.linzn.homeDevices.DeviceCategory;
import de.linzn.homeDevices.HomeDevicesPlugin;
import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.JSONTemplate;
import de.stem.stemSystem.STEMSystemApp;
import de.stem.stemSystem.utils.Color;
import org.json.JSONObject;

import java.util.List;

public class ChangeAutoModeJSON implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {

        JSONObject jsonObject = new JSONObject();

        DeviceCategory deviceCategory = DeviceCategory.valueOf(inputList.get(1).toUpperCase());
        boolean value = Boolean.parseBoolean(inputList.get(2).toLowerCase());
        STEMSystemApp.LOGGER.INFO(Color.GREEN + "[API-SERVER] Post Request: ChangeAutoMode::" + deviceCategory.name() + ":::" + value);
        boolean newValue = HomeDevicesPlugin.homeDevicesPlugin.setAutoMode(deviceCategory, value);
        jsonObject.put("status", newValue);

        JSONTemplate emptyPage = new JSONTemplate();
        emptyPage.setCode(jsonObject);
        return emptyPage;
    }
}