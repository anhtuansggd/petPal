package com.petpal.backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Location {
    private double latitude;
    private double longitude;
}
