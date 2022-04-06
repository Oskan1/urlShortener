package com.urlShortener.urlShortener.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Random;

@javax.persistence.Entity
@Table(name = "urls")
public class UrlShort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name =  "main_url")
    private String mainUrl;

    @Column(name = "short_url")
    private String shortUrl;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl() {
        int asciiA = 97;
        int asciiZ = 122;
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = asciiA + (int)
                    (random.nextFloat() * (asciiZ - asciiA + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        this.shortUrl = generatedString;
    }

    @Column(name = "timestamp")
    private Date timestamp;

    public UrlShort() {
        super();
    }

    public UrlShort(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
