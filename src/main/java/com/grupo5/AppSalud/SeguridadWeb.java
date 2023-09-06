package com.grupo5.AppSalud;

import com.grupo5.AppSalud.servicios.CustomUserDetailsService;
import com.grupo5.AppSalud.servicios.ServicioProfesional;
import com.grupo5.AppSalud.servicios.ServicioUsuario;
import javax.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {

    /////Se agrego seguridad para el login
    @Autowired
    public ServicioUsuario servicioUsuario;

    @Autowired
    public CustomUserDetailsService customUserDS;

    @Autowired
    public ServicioProfesional servicioProfesional;

    @Autowired
    public void configueGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDS)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/*").hasRole("ADMIN")
                .antMatchers("/usuario/*").hasAnyRole("PACIENTE", "ADMIN")
                .antMatchers("/profesional/*").hasAnyRole("MEDICO", "ADMIN")
                .antMatchers("/css/*", "/js/*", "/img", "/**")
                .permitAll()
                .and().formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/logincheck")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/") // Redirige a esta URL por defecto
                .successHandler((request, response, authentication) -> {
                    if (authentication != null) {
                        if (authentication.getAuthorities().stream()
                                .anyMatch(a -> a.getAuthority().equals("ROLE_MEDICO"))) {
                            response.sendRedirect("/profesional/dashboard"); // Cambia a la URL deseada
                        } else if (authentication.getAuthorities().stream()
                                .anyMatch(a -> a.getAuthority().equals("ROLE_PACIENTE"))) {
                            response.sendRedirect("/usuario/dashboard");
                        } else if (authentication.getAuthorities().stream()
                                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                            response.sendRedirect("/admin/dashboard");
                        } else {
                            response.sendRedirect("/"); // Redirige a la URL por defecto en otros casos
                        }
                    }
                })
                .permitAll() ///dashboard
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
                .and().csrf()
                .disable();
    }
}
