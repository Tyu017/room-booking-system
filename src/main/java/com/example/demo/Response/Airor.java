package com.example.demo.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Airor {

    @JsonProperty("Error")
    private String Error;

    public Airor(String error) {
        this.Error = error;
    }

}
