package com.ioi.haryeom.auth.controller;

import com.ioi.haryeom.auth.dto.AccessTokenResponse;
import com.ioi.haryeom.auth.dto.LoginResponse;
import com.ioi.haryeom.auth.dto.UserInfoResponse;
import com.ioi.haryeom.auth.service.AuthService;
import com.ioi.haryeom.auth.service.TokenService;
import com.ioi.haryeom.common.util.AuthMemberId;
import com.ioi.haryeom.member.domain.Member;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    @GetMapping
    public ResponseEntity<UserInfoResponse> getUser(@AuthenticationPrincipal Member member) {

        return ResponseEntity.ok().body(authService.getUser(member));
    }

    @GetMapping("/{provider}/login")
    public ResponseEntity<LoginResponse> oauthLogin(@RequestParam String code,
        @PathVariable("provider") String provider)
        throws IOException, ParseException {

        return ResponseEntity.ok().body(authService.oauthLogin(code, provider));
    }

    @PostMapping("/{provider}/logout")
    private ResponseEntity<Void> oauthLogout(@AuthMemberId Long userId,
        @PathVariable("provider") String provider, HttpServletResponse response)
        throws IOException {

        authService.oauthLogout(userId, provider);
        tokenService.resetHeader(response);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    private ResponseEntity<AccessTokenResponse> reissueAccessToken(HttpServletRequest request,
        HttpServletResponse response) {
        String accessToken = tokenService.reissueAccessToken(request, response);

        return ResponseEntity.ok()
            .body(new AccessTokenResponse(accessToken));
    }

    @GetMapping("/test/login/{role}")
    public ResponseEntity<LoginResponse> testLogin(@PathVariable("role") String role) {

        return ResponseEntity.ok().body(authService.testLogin(role));
    }
}
