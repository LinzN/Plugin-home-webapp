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

package de.linzn.homeWebApp.mainServer.handler;

import de.linzn.homeWebApp.HomeWebAppPlugin;
import de.linzn.homeWebApp.core.IResponseHandler;
import de.linzn.homeWebApp.core.htmlTemplates.EmptyTemplate;
import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;
import de.linzn.homeWebApp.mainServer.htmlTemplates.ResourceTemplate;

import java.io.File;
import java.util.List;

public class ResourceHandler implements IResponseHandler {

    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {
        File webResourceFolder = HomeWebAppPlugin.homeWebAppPlugin.mainWebserver.getWebResourcesFolder();
        IHtmlTemplate htmlPage = new EmptyTemplate();

        StringBuilder filePath = new StringBuilder();
        for (int i = 0; i < inputList.size(); i++) {
            filePath.append(inputList.get(i));
            if (i < inputList.size() - 1) {
                filePath.append("/");
            }
        }

        File requestedFile = new File(webResourceFolder, filePath.toString());
        if (requestedFile.exists()) {
            if (!requestedFile.isDirectory()) {
                htmlPage = new ResourceTemplate();
                ((ResourceTemplate) htmlPage).setFile(requestedFile);
            }
        }
        return htmlPage;
    }
}
