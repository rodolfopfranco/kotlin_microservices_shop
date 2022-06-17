package com.pan.apiusuarios.filter

import com.pan.apiusuarios.entity.Usuario
import com.pan.apiusuarios.service.AuthenticationService
import com.pan.apiusuarios.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class FiltroAutenticacao(
    @Autowired
    private val authenticationService: AuthenticationService,
    @Autowired
    private val usuarioService: UsuarioService
): OncePerRequestFilter()
{
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var header: String? = request.getHeader("Authorization");
        var token: String? = null
        if(header != null && header.startsWith("Bearer")){
            token = header.substring(7, header.length)
        }
        if (authenticationService.verificaToken(token)){
            var idUsuario: Long = authenticationService.retornarIdUsuario(token)
            var usuario: Usuario = usuarioService.buscarUsuarioPorId(idUsuario)
            SecurityContextHolder.getContext()
                .setAuthentication(UsernamePasswordAuthenticationToken(usuario, null, usuario.authorities))
        }
        filterChain.doFilter(request, response)
    }
}