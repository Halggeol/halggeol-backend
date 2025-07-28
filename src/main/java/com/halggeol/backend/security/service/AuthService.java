package com.halggeol.backend.security.service;

import com.halggeol.backend.security.dto.FindEmailDTO;
import com.mysql.cj.x.protobuf.MysqlxCrud.Find;
import java.util.Map;

public interface AuthService {
    Map<String, String> extendLogin(String email);

    Map<String, String> findEmail(FindEmailDTO info);
}
