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

package de.linzn.homeWebApp.api.jsonapi.get;

import de.linzn.homeDevices.DeviceCategory;
import de.linzn.homeDevices.HomeDevicesPlugin;
import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.JSONTemplate;
import org.json.JSONObject;

import java.util.List;

public class AutoModeJSON implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {

        JSONObject jsonObject = new JSONObject();

        for (DeviceCategory deviceCategory : DeviceCategory.values()) {
            boolean status = HomeDevicesPlugin.homeDevicesPlugin.isAutoMode(deviceCategory);
            jsonObject.put(deviceCategory.name(), status);
        }

        JSONTemplate emptyPage = new JSONTemplate();

        emptyPage.setCode(jsonObject);
        return emptyPage;
    }
}
