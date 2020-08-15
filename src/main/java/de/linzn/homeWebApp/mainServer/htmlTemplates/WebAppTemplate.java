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
import de.linzn.homeWebApp.mainServer.htmlTemplates.frames.DefaultFrame;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class WebAppTemplate implements IHtmlTemplate {


    private String generatedPage;
    private String title = "WebApp";
    private final String[][] matrix;
    private final int frameSize;


    public WebAppTemplate(int rows, int columns) {
        this.frameSize = (12 / columns);
        this.matrix = new String[rows][columns];
        this.generatedPage = "";

        for (int i = 0; i < matrix.length; i++) {
            Arrays.fill(matrix[i], setFrame(""));
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void addEntry(DefaultFrame frame, int row, int column) {
        this.matrix[row][column] = setFrame(frame.getFrame());
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
                "</head>";
    }

    private String getBody() {
        StringBuilder body = new StringBuilder();

        body.append("<body>\n" +
                "    <div class=\"container\">\n");

        for (String[] rowField : this.matrix) {
            StringBuilder row = new StringBuilder();
            row.append("<div class=\"row\">");

            for (String column : rowField) {
                row.append(column);
            }

            row.append("</div>");
            body.append(row.toString());
        }

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


    private String setFrame(String entry) {
        return "<div class=\"col-sm-" + frameSize + " col-lg-" + frameSize + "\"> " + entry + "</div>";
    }
}
