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

import de.linzn.heatingstatus.HeatingStatusPlugin;
import de.linzn.heatingstatus.objects.Inlet;
import de.linzn.heatingstatus.objects.Notify;
import de.linzn.heatingstatus.objects.Outlet;
import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.JSONTemplate;
import org.json.JSONObject;

import java.util.List;

public class HeaterDataJSON implements IResponseHandler {

    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {
        List<Inlet> inlets = HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getInletsList();
        List<Outlet> outlets = HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getOutletsList();
        List<Notify> notifies = HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getNotifiesList();

        JSONObject jsonObject = new JSONObject();

        JSONObject inletsObject = new JSONObject();
        for (Inlet inlet : inlets) {
            inletsObject.put(inlet.getName(), inlet.getValue());
        }

        JSONObject outletsObject = new JSONObject();
        for (Outlet outlet : outlets) {
            outletsObject.put(outlet.getName(), outlet.isActive());
        }

        JSONObject notifiesObject = new JSONObject();
        for (Notify notify : notifies) {
            notifiesObject.put(notify.getName(), notify.isActive());
        }

        jsonObject.put("inlets", inletsObject);
        jsonObject.put("outlets", outletsObject);
        jsonObject.put("notifies", notifiesObject);

        JSONTemplate emptyPage = new JSONTemplate();

        emptyPage.setCode(jsonObject);
        return emptyPage;
    }
}
