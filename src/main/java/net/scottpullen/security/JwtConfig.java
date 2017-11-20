package net.scottpullen.security;

public class JwtConfig {
    private String signingKey;
    private Integer ttl;

    public String getSigningKey() {
        return this.signingKey;
    }

    public Integer getTtl() {
        return this.ttl;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }
}
