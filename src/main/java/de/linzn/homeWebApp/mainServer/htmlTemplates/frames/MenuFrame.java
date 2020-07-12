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

package de.linzn.homeWebApp.mainServer.htmlTemplates.frames;

public class MenuFrame {
    private final StringBuilder frameBuilder = new StringBuilder();

    public MenuFrame() {
        frameBuilder.append("" +
                "<div class=\"dash-unit\">\n" +
                "   <dtitle>Menu</dtitle>\n" +
                "   <hr>\n");
        frameBuilder.append("" +
                "<div class=\"cont\">\n" +
                "   <a href=\"dashboard.html\" class=\"fas fa-home fs2\" style=\"color: grey; font-size: 56px;\"></a><br>" +
                "\n");
        //        "</div>");

        frameBuilder.append("" +
                //        "<div class=\"cont\">\n" +
                "   <a href=\"terminal.html\" class=\"fas fa-align-left fs2\" style=\"color: grey; font-size: 56px;\"></a><br>" +
                "\n");
        //        "</div>");

        frameBuilder.append("" +
                //    "<div class=\"cont\">\n" +
                "   <a href=\"settings.html\" class=\"fas fa-cogs fs2\" style=\"color: grey; font-size: 56px;\"></a><br>" +
                "</div>\n");

        frameBuilder.append("</div>\n");
    }


    public String getFrame() {
        return this.frameBuilder.toString();
    }
}
