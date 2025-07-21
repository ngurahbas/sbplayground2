package app.sbplayground2.domain.user

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

data class AuthenticatedUser(
    val name: String,
    val idType: IdType,
    val idValue: String,
    val idSource: IdSource,
) : OAuth2User {
    override fun getAttributes() = mapOf("name" to name)
    override fun getAuthorities() = listOf(SimpleGrantedAuthority("ROLE_USER"))
    override fun getName() = name
}