package com.halggeol.backend.security.service;

import java.util.Map;

public interface AuthService {
    public Map<String, String> extendLogin(String email);
}
