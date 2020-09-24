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

import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.JSONTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationArchiveJSON implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {

        Format dateFormat = new SimpleDateFormat("EEEE d MMMMM yyyy", Locale.GERMANY);

        JSONArray jsonArray = new JSONArray();

        // loop with notify start todo add notification module
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1);
        jsonObject.put("source", "Heizung");
        jsonObject.put("notification", "Test123");
        jsonObject.put("date", dateFormat.format(new Date()));

        jsonArray.put(jsonObject);
        // loop with notify stop

        JSONTemplate emptyPage = new JSONTemplate();

        emptyPage.setCode(jsonArray);
        return emptyPage;
    }
}
