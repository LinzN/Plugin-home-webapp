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
import de.linzn.openJL.math.FloatingPoint;
import de.linzn.systemChain.callbacks.NetworkScheduler;
import org.json.JSONObject;

import java.util.List;

public class GenericDataJSON implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("network", getNetworkData());
        jsonObject.put("heater", getHeaterData());
        jsonObject.put("stem", getStemData());

        JSONTemplate emptyPage = new JSONTemplate();
        emptyPage.setCode(jsonObject);
        return emptyPage;
    }

    private JSONObject getNetworkData() {
        JSONObject networkObject = new JSONObject();
        float ping = NetworkScheduler.getLastPing();
        networkObject.put("ping", ping);
        return networkObject;
    }

    private JSONObject getStemData() {
        JSONObject stemObject = new JSONObject();
        stemObject.put("status", "OK");
        return stemObject;
    }

    private JSONObject getHeaterData() {
        JSONObject heaterObject = new JSONObject();
        List<Outlet> outlets = HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getOutletsList();
        List<Notify> notifies = HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getNotifiesList();
        List<Inlet> inlets = HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getInletsList();

        String status = "SLEEP";
        double temp = 0;
        for (Outlet outlet : outlets) {
            if (outlet.getIndex() == 1) {
                if (outlet.isActive()) {
                    status = "HEATING";
                }
                break;
            }
        }

        for (Notify notify : notifies) {
            if (notify.isActive()) {
                status = "ERROR";
                break;
            }
        }

        for (Inlet inlet : inlets) {
            if (inlet.getIndex() == 3) {
                temp = FloatingPoint.round(inlet.getValue(), 2);
                break;
            }
        }

        heaterObject.put("status", status);
        heaterObject.put("temperature", temp);
        return heaterObject;
    }
}
