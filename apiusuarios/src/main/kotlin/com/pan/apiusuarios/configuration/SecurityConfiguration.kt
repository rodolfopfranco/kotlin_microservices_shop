package com.pan.apiusuarios.configuration

import com.pan.apiusuarios.filter.FiltroAutenticacao
import com.pan.apiusuarios.service.AuthenticationService
import com.pan.apiusuarios.service.UsuarioService

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration (
    private val usuarioService: UsuarioService,
    @Lazy private val authenticationService: AuthenticationService,
    private val disableDefaults: Boolean?= true
) : WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManager(): AuthenticationManager{
        return super.authenticationManager()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(usuarioService).passwordEncoder(BCryptPasswordEncoder())
    }

    override fun configure(web : WebSecurity){
        super.configure(web)
        web.ignoring().antMatchers(
            "/configuration/ui",
            "swagger-resources/**",
            "/configuration/security",
            "swagger-ui.html",
            "/webjars/**"
        )
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/v1/users/login").permitAll()
            .anyRequest().authenticated()
            .and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().addFilterBefore(
                FiltroAutenticacao(authenticationService, usuarioService),
                UsernamePasswordAuthenticationFilter::class.java)
    }

}