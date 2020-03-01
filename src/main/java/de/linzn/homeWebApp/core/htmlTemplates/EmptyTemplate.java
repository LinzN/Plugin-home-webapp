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

package de.linzn.homeWebApp.core.htmlTemplates;

import java.util.LinkedHashMap;
import java.util.Map;

public class EmptyTemplate implements IHtmlTemplate {

    private String code;

    private String generatedPage;

    public EmptyTemplate() {
        this.code = "No code";
        this.generatedPage = "";
    }


    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public Map<String, String> headerList() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Access-Control-Allow-Origin", "*");
        map.put("Content-Type", "text/html; charset=UTF-8");
        map.put("Accept-Ranges", "bytes");
        return map;
    }

    @Override
    public void generate() {
        generatedPage = "<html lang=\"en\">" + code + "</html>";
    }

    @Override
    public long length() {
        return this.generatedPage.getBytes().length;
    }

    @Override
    public byte[] getBytes() {
        return this.generatedPage.getBytes();
    }

}
