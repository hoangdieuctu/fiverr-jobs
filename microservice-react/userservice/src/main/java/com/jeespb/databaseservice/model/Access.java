package com.jeespb.databaseservice.model;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Document(indexName = "access")
public class Access {
    @Id
    private String id;

    private String type;
    private String host;
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
