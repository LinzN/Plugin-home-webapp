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
import de.stem.stemSystem.STEMSystemApp;
import de.stem.stemSystem.modules.notificationModule.archive.NotificationArchiveObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NotificationArchiveJSON implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {

        Format dateFormat = new SimpleDateFormat("EEEE d MMMMM yyyy", Locale.GERMANY);

        List<NotificationArchiveObject> list = STEMSystemApp.getInstance().getNotificationModule().getNotificationArchive().getLastNotifications();

        JSONArray jsonArray = new JSONArray();
        int i = 1;
        for (NotificationArchiveObject notificationArchiveObject : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", i);
            jsonObject.put("source", notificationArchiveObject.source);
            jsonObject.put("notification", notificationArchiveObject.notification);
            jsonObject.put("date", dateFormat.format(notificationArchiveObject.date));
            jsonArray.put(jsonObject);
            i++;
        }

        JSONTemplate emptyPage = new JSONTemplate();

        emptyPage.setCode(jsonArray);
        return emptyPage;
    }
}
