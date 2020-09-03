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
import de.linzn.trashCalender.TrashCalenderPlugin;
import de.linzn.trashCalender.objects.ITrash;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReminderJSON implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {
        JSONArray jsonArray = new JSONArray();

        List<ITrash> iTrashList = TrashCalenderPlugin.trashCalenderPlugin.getTrashCalendar().getTrashList(new Date());

        for (ITrash iTrash : iTrashList) {
            Format dateFormat = new SimpleDateFormat("EEEE d MMMMM yyyy", Locale.GERMANY);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("reminderType", "trash");
            jsonObject.put("name", iTrash.getName());
            jsonObject.put("date", dateFormat.format(iTrash.getDate()));
            jsonArray.put(jsonObject);
        }
        JSONTemplate emptyPage = new JSONTemplate();

        emptyPage.setCode(jsonArray);
        return emptyPage;
    }
}
