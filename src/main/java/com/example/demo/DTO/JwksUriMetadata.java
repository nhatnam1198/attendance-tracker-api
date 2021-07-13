package com.example.demo.DTO;

import java.util.List;

public class JwksUriMetadata {
    public List<JsonWebKey> keys;

    public List<JsonWebKey> getKeys() {
        return keys;
    }

    public void setKeys(List<JsonWebKey> keys) {
        this.keys = keys;
    }
}
