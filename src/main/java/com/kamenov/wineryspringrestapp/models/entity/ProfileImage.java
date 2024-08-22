package com.kamenov.wineryspringrestapp.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "profile_images")
public class ProfileImage extends BaseEntity {
    @Column
    private String locatedOn;

    public ProfileImage(String locatedOn) {
        this.locatedOn = locatedOn;
    }

    public ProfileImage() {
    }
    public String getLocatedOn() {
        return locatedOn;
    }
    public void setLocatedOn(String locatedOn) {
        this.locatedOn = locatedOn;
    }
}
