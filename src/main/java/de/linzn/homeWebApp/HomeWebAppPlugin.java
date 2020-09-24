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

package de.linzn.homeWebApp;


import de.linzn.homeWebApp.api.APIWebserver;
import de.stem.stemSystem.modules.pluginModule.STEMPlugin;

import java.util.ArrayList;
import java.util.List;


public class HomeWebAppPlugin extends STEMPlugin {

    public static HomeWebAppPlugin homeWebAppPlugin;
    public APIWebserver apiWebserver;


    public HomeWebAppPlugin() {
        homeWebAppPlugin = this;
    }

    @Override
    public void onEnable() {
        this.setupConfig();
        this.apiWebserver = new APIWebserver(this.getDefaultConfig().getString("apiServer.hostname"), this.getDefaultConfig().getInt("apiServer.port"));
        this.apiWebserver.start();
    }

    @Override
    public void onDisable() {
        this.apiWebserver.stop();
    }

    private void setupConfig() {
        List<String> list1 = new ArrayList<>();
        list1.add("127.0.0.1");

        List<String> list2 = new ArrayList<>();
        list2.add("127.0.0.1");

        if (!this.getDefaultConfig().contains("apiServer.whitelist")) {
            this.getDefaultConfig().set("apiServer.whitelist", list1);
        }

        if (!this.getDefaultConfig().contains("mainServer.whitelist")) {
            this.getDefaultConfig().set("mainServer.whitelist", list2);
        }
        this.getDefaultConfig().save();
    }

}
