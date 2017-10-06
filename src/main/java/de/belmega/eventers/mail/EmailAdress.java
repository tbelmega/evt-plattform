package de.belmega.eventers.mail;

public class EmailAdress {

    private int port = 587;
    private String host = "smtp.gmail.com";
    private String from = "noreply@the-eventers.de";
    private boolean auth = true;
    private String username = "the.eventers.de@gmail.com";
    private String password = "RcCuZnRev2VTWdnlabNy";
    private String protocol = "TLS";
    private boolean debug = false;


    public EmailAdress() {
    }

    public EmailAdress(int port, String host, String from, boolean auth, String username, String password, String protocol, boolean debug) {
        this.port = port;
        this.host = host;
        this.from = from;
        this.auth = auth;
        this.username = username;
        this.password = password;
        this.protocol = protocol;
        this.debug = debug;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
