package com.lead.CatalagoFilmes.config.security;

import com.lead.CatalagoFilmes.repository.UsuarioRepository;
import com.lead.CatalagoFilmes.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/filme/filmes").permitAll()
                .antMatchers(HttpMethod.GET, "/filme/filmeBusca/*").permitAll()
                .antMatchers(HttpMethod.GET, "/filme/filmeById/*").permitAll()
                .antMatchers(HttpMethod.GET, "/categoria/categorias").permitAll()
                .antMatchers(HttpMethod.GET, "/categoria/categoriaById/*").permitAll()
                .antMatchers(HttpMethod.GET, "/usuario/usuarios").permitAll()
                .antMatchers(HttpMethod.GET, "/usuario/usuarioById/*").permitAll()
                .antMatchers(HttpMethod.GET, "/idioma/idiomas").permitAll()
                .antMatchers(HttpMethod.GET, "/idioma/idiomaById/*").permitAll()
                .antMatchers(HttpMethod.GET, "/filme/getFilmesByCategoria/*").permitAll()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .csrf().ignoringAntMatchers("/h2-console/**")
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AuthenticationTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception { }

}
