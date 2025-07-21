package app.sbplayground2.config

import app.sbplayground2.domain.user.AuthenticatedUser
import app.sbplayground2.domain.user.IdSource
import app.sbplayground2.domain.user.IdType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun oauth2UserService(): DefaultOAuth2UserService {
        val delegate = DefaultOAuth2UserService()
        return object : DefaultOAuth2UserService() {
            override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
                val oauth2User = delegate.loadUser(userRequest)
                val registrationId = userRequest.clientRegistration.registrationId
                
                return AuthenticatedUser(
                    name = oauth2User.getAttribute("name") ?: "Unknown",
                    idType = IdType.EMAIL,
                    idValue = oauth2User.getAttribute("email") ?: "",
                    idSource = when (registrationId.lowercase()) {
                        "google" -> IdSource.GOOGLE
                        "github" -> IdSource.GITHUB
                        else -> throw IllegalStateException("Unsupported OAuth2 provider: $registrationId")
                    }
                )
            }
        }
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/error").permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login { oauth2Login ->
                oauth2Login
                    .userInfoEndpoint { userInfo ->
                        userInfo.userService(oauth2UserService())
                    }
                    .defaultSuccessUrl("/", true)
            }
            .logout { logout ->
                logout
                    .logoutSuccessUrl("/")
                    .permitAll()
            }

        return http.build()
    }
}