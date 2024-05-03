package com.ssafy.stellar.settings;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
public class ParameterProcessingAdvice {

    @ModelAttribute
    public void processParams(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        params.forEach((key, values) -> {
            for (int i = 0; i < values.length; i++) {
                values[i] = values[i].replace('+', ' ');
            }
        });
    }
}
