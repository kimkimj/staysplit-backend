package staysplit.hotel_reservation.common.oauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import staysplit.hotel_reservation.common.oauth.service.OAuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final OAuthService oauthService;

//    @PostMapping("/login/{provider}")
//    public ResponseEntity<TokenResponse> oauthLogin(@PathVariable String provider,
//                                                    @RequestParam String code) {
//        TokenResponse tokens = oAuthService.loginWithOAuth(provider, code);
//        return ResponseEntity.ok(tokens);
//    }


}
