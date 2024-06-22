package com.rahul.quizApplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.Name;

@Data
@RequiredArgsConstructor
public class Response {

    @JsonProperty("id")
    private int questionId;
    private String response;
}
