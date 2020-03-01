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

package de.linzn.homeWebApp.api.postRequests;

import de.azcore.azcoreRuntime.AppLogger;
import de.azcore.azcoreRuntime.utils.Color;
import de.linzn.homeDevices.devices.DeLockSwitch;
import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.EmptyTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;
import org.json.JSONObject;

import java.util.List;

public class PostLightControl implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {

        JSONObject jsonObject = new JSONObject();

        String deviceName = inputList.get(1);
        AppLogger.debug(Color.GREEN + "[WEBAPP_API-SERVER] Post Request: LightControl::" + deviceName);

        if (inputList.size() < 3) {
            new DeLockSwitch(deviceName).toggleDevice();
        } else {
            boolean setStatus = Integer.parseInt(inputList.get(2)) == 1;
            new DeLockSwitch(deviceName).setDeviceStatus(setStatus);
        }

        jsonObject.put("status", true);

        EmptyTemplate emptyPage = new EmptyTemplate();
        emptyPage.setCode(jsonObject.toString());
        return emptyPage;
    }
}