package com.indicafilmesai.backend.Utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Random;

public class GenerateHTMLMessage {
    public static String signup(String message) {
       return "<!DOCTYPE html>" +
                "<html lang = \"pt-br\">" +
                "<head>" +
                "<title>Mensagem HTML</title>" +
                "</head>" +
                "<body>" +
                "<p>" + message + "</p>" +
                "</body>" +
                "</html>";
    }

}
