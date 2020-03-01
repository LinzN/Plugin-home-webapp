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

public class HalfFrame {
    private StringBuilder frameBuilder = new StringBuilder();

    public HalfFrame(String title, String divName, boolean script, String divRequestURL, int time) {
        frameBuilder.append("" +
                "<div class=\"half-unit\">\n" +
                "   <dtitle>" + title + "</dtitle>\n" +
                "   <hr>\n" +
                "   <div id=\"" + divName + "\">\n" +
                "       <div class=\"cont\">\n" +
                "           <p></p>\n" +
                "           <h1>Loading</h1>\n" +
                "           <i class=\"fas fa-spinner fs2\"></i>\n" +
                "           <p></p>\n" +
                "       </div>\n" +
                "   <!-- div update-->\n" +
                "   </div>\n");
        if (script) {
            frameBuilder.append("" +
                    "<script >\n" +
                    "    $(function() {\n" +
                    "       $(\"#" + divName + "\").load(\"" + divRequestURL + " #" + divName + ">*\", \"\");\n" +
                    "    });\n" +
                    "</script>");
            frameBuilder.append("" +
                    "<script>\n" +
                    "   setInterval(function() {\n" +
                    "       $(\"#" + divName + "\").load(\"" + divRequestURL + " #" + divName + ">*\", \"\");\n" +
                    "   }, " + time + ");\n" +
                    "</script>\n");
        }
        frameBuilder.append("</div>\n");
    }

    public HalfFrame(String title, String customCode) {
        frameBuilder.append("" +
                "<div class=\"half-unit\">\n" +
                "   <dtitle>" + title + "</dtitle>\n" +
                "   <hr>\n" +
                customCode +
                "</div>\n");
    }


    public String getFrame() {
        return this.frameBuilder.toString();
    }
}
