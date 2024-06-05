package fr.leboncoin.qa.gherkix.restcontroller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

  @GetMapping("/api/user")
  public ResponseEntity<?> getUser(@AuthenticationPrincipal OAuth2User user, HttpSession session) {
    if (user == null) {
      return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    } else {
      Map<String, Object> attributes = user.getAttributes();
      // here internal logic with session
      return ResponseEntity.ok().body(attributes);
    }
  }
}
