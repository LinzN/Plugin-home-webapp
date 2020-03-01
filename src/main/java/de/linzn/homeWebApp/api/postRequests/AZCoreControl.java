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
import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.EmptyTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;
import org.json.JSONObject;

import java.util.List;

public class AZCoreControl implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {

        JSONObject jsonObject = new JSONObject();

        String command = inputList.get(1);
        AppLogger.debug(Color.GREEN + "[WEBAPP_API-SERVER] Post Request: AZCoreControl::" + command);

        boolean success = executeCommand(command);
        jsonObject.put("status", success);

        EmptyTemplate emptyPage = new EmptyTemplate();
        emptyPage.setCode(jsonObject.toString());
        return emptyPage;
    }

    private boolean executeCommand(String command) {
        //AZCoreRuntimeApp.getInstance().getScheduler().runTaskSingleOperation(OperationRegister.getOperation("evy_restart"));
        //todo fix restart
        return command.equalsIgnoreCase("restart");
    }
}