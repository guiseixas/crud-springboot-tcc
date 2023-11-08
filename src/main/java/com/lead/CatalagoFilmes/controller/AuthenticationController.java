    package com.lead.CatalagoFilmes.controller;

    import com.lead.CatalagoFilmes.model.DTO.TokenDTO;
    import com.lead.CatalagoFilmes.model.DTO.UsuarioDTO;
    import com.lead.CatalagoFilmes.config.security.TokenService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.AuthenticationException;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/auth")
    public class AuthenticationController {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private TokenService tokenService;

        @PostMapping
        public ResponseEntity<?> autenticar(@RequestBody @Validated UsuarioDTO usuarioDTO){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = usuarioDTO.convert();
            try {
                Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
                String token = tokenService.generateToken(authentication);
                return ResponseEntity.ok(new TokenDTO(token, "Bearer")) ;
            } catch (AuthenticationException e) {
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
            }
        }
    }