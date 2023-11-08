package com.lead.CatalagoFilmes.config.security;

import com.lead.CatalagoFilmes.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String generateToken(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Date data = new Date();
        Date expiracao = new Date(data.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("API Catálago Filmes - LEAD DELL")
                .setSubject(usuario.getId().toString())
                .setIssuedAt(data)
                .setExpiration(expiracao)
                .signWith(SignatureAlgorithm.HS256 ,secret)
                .compact();
    }
    public boolean isValid(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getIdUsuario(String token) {
            Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
            return Long.parseLong(claims.getSubject());
    }
}
