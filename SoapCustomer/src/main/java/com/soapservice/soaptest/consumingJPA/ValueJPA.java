package com.soapservice.soaptest.consumingJPA;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ValueJPA (String name, String lastname){ }
