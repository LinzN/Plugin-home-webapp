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

package de.linzn.homeWebApp.mainServer.htmlTemplates;

import de.linzn.homeWebApp.core.htmlTemplates.IHtmlTemplate;
import de.linzn.homeWebApp.mainServer.htmlTemplates.frames.TerminalFrame;

import java.util.LinkedHashMap;
import java.util.Map;

public class TerminalTemplate implements IHtmlTemplate {


    private String generatedPage;
    private String title = "Terminal";
    private String frame;


    public TerminalTemplate() {

        this.generatedPage = "";
        this.frame = "";
    }

    public void setTitle(String title) {
        this.title = title;
    }


    private String getHead() {
        return "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>" + this.title + "</title>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">\n" +
                "    <meta name=\"description\" content=\"\">\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"bootstrap/css/bootstrap.min.css\" />\n" +
                "    <link href=\"css/main.css\" rel=\"stylesheet\">\n" +
                "    <script type=\"text/javascript\" src=\"js/jquery.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"bootstrap/js/bootstrap.min.js\"></script>\n" +
                "    <link href=\"http://fonts.googleapis.com/css?family=Raleway:400,300\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <link href=\"http://fonts.googleapis.com/css?family=Open+Sans\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "</head>";
    }

    private String getBody() {
        StringBuilder body = new StringBuilder();

        body.append("<body>\n" +
                "    <div class=\"container\">\n");
        body.append("<div class=\"col-sm-2 col-lg-2\"> " +
                "<div class=\"dash-unit\">\n" +
                "   <dtitle>Menu</dtitle>\n" +
                "   <hr>\n" +
                "<div class=\"cont\">\n" +
                "   <a href=\"dashboard.html\" class=\"fas fa-home fs2\" style=\"color: grey; font-size: 56px;\"></a></div>\n" +
                "</div>" +
                "" +
                "</div>");

        body.append("<div class=\"row\">");
        body.append(frame);
        body.append("</div>");

        body.append("    </div>\n" +
                "</body>");

        return body.toString();
    }


    @Override
    public Map<String, String> headerList() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Content-Type", "text/html; charset=UTF-8");
        map.put("Accept-Ranges", "bytes");
        return map;
    }

    @Override
    public void generate() {
        generatedPage = "<html lang=\"en\">" + getHead() + getBody() + "</html>";
    }

    @Override
    public long length() {
        return this.generatedPage.getBytes().length;
    }

    @Override
    public byte[] getBytes() {
        return this.generatedPage.getBytes();
    }

    public void setFrame(TerminalFrame terminalFrame) {
        frame = "<div class=\"col-sm-" + 9 + " col-lg-" + 9 + "\"> " + terminalFrame.getFrame() + "</div>";
    }
}
