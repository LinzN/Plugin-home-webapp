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

package de.linzn.homeWebApp.api.legacyjquery.postRequests;


import de.linzn.homeDevices.HomeDevicesPlugin;
import de.linzn.homeDevices.devices.TasmotaDevice;
import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.EmptyTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;
import de.stem.stemSystem.AppLogger;
import de.stem.stemSystem.utils.Color;
import org.json.JSONObject;

import java.util.List;

public class PostLightControl implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {

        JSONObject jsonObject = new JSONObject();

        String deviceName = inputList.get(1);
        AppLogger.debug(Color.GREEN + "[WEBAPP_API-SERVER] Post Request: LightControl::" + deviceName);

        TasmotaDevice tasmotaDevice = HomeDevicesPlugin.homeDevicesPlugin.getTasmotaDevice(deviceName);
        if (inputList.size() < 3) {
            tasmotaDevice.toggleDevice();
        } else {
            boolean setStatus = Integer.parseInt(inputList.get(2)) == 1;
            tasmotaDevice.setDeviceStatus(setStatus);
        }

        jsonObject.put("status", true);

        EmptyTemplate emptyPage = new EmptyTemplate();
        emptyPage.setCode(jsonObject.toString());
        return emptyPage;
    }
}