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

import de.linzn.calender.CalenderPlugin;
import de.linzn.calender.objects.ICalendarType;
import de.linzn.calender.objects.TrashType;
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

public class ReminderJSON implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {
        JSONArray jsonArray = new JSONArray();

        List<ICalendarType> iCalendarTypes = CalenderPlugin.calenderPlugin.getCalendarManager().getCalenderEntriesList(new Date());

        for (ICalendarType iCalendarType : iCalendarTypes) {
            Format dateFormat = new SimpleDateFormat("EEEE d MMMMM yyyy", Locale.GERMANY);

            String reminderType = "trash";

            if (iCalendarType.getType() == TrashType.OTHER) {
                reminderType = "other";
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("reminderType", reminderType);
            jsonObject.put("name", iCalendarType.getName());
            jsonObject.put("date", dateFormat.format(iCalendarType.getDate()));
            jsonObject.put("type", iCalendarType.getType().name());
            jsonArray.put(jsonObject);
        }
        JSONTemplate emptyPage = new JSONTemplate();

        emptyPage.setCode(jsonArray);
        return emptyPage;
    }
}
