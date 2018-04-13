package demo.model;

import com.google.api.client.util.Key;

public class Response {

    @Key("status")
    public String status;

    @Key("total")
    public int total;

    @Key("skills")
    public String skills;
}
