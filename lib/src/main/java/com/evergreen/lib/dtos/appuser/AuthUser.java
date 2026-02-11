package com.evergreen.lib.dtos.appuser;

import java.util.List;

public record AuthUser(Integer id, String email, List<String> roles) {
}
