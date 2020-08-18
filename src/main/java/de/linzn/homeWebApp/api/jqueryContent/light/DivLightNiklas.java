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

package de.linzn.homeWebApp.api.jqueryContent.light;


import de.linzn.homeDevices.DeviceStatus;
import de.linzn.homeDevices.HomeDevicesPlugin;
import de.linzn.homeDevices.devices.TasmotaDevice;
import de.linzn.homeWebApp.HomeWebAppPlugin;
import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.EmptyTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;

import java.util.List;

public class DivLightNiklas implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {


        StringBuilder stringBuilder = new StringBuilder();


        TasmotaDevice tasmotaDevice = HomeDevicesPlugin.homeDevicesPlugin.getTasmotaDevice("niklas");
        String url = "http://" + HomeWebAppPlugin.homeWebAppPlugin.apiWebserver.getHostname() + ":" + HomeWebAppPlugin.homeWebAppPlugin.apiWebserver.getPort();

        stringBuilder.append("<div id=\"light-niklas\">" +
                "    <div class=\"cont\">");

        if (tasmotaDevice.getDeviceStatus() == DeviceStatus.ENABLED) {
            stringBuilder.append("<ok><button onclick=\"$.post('" + url + "/post_light-control/" + tasmotaDevice.getDeviceName() + "/0')\" class=\"fas fa-lightbulb tbutton\"></button></ok>");
        } else if (tasmotaDevice.getDeviceStatus() == DeviceStatus.DISABLED) {
            stringBuilder.append("<button onclick=\"$.post('" + url + "/post_light-control/" + tasmotaDevice.getDeviceName() + "/1')\" class=\"far fa-lightbulb tbutton\"></button>");
        } else {
            stringBuilder.append("<button class=\"far fa-lightbulb tbutton\" style=\" color: #808080; \"></button>");
        }

        stringBuilder.append("    </div>" +
                "</div>");

        EmptyTemplate emptyPage = new EmptyTemplate();

        emptyPage.setCode(stringBuilder.toString());
        return emptyPage;
    }
}
