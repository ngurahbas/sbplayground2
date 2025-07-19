package app.sbplayground2.web

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Controller
class PageController {

    @GetMapping("/")
    fun home(model: Model, @AuthenticationPrincipal principal: OAuth2User?): String {
        model.addAttribute("title", "Welcome to SB Playground")
        
        if (principal != null) {
            val name = principal.getAttribute<String>("name") ?: principal.name
            model.addAttribute("authenticated", true)
            model.addAttribute("username", name)
            model.addAttribute("message", "Hello, $name! You are logged in with Google.")
        } else {
            model.addAttribute("authenticated", false)
            model.addAttribute("message", "Hello from Thymeleaf! Login with Google to see more.")
        }
        
        return "home" // This will resolve to src/main/resources/templates/home.html
    }
}

@RestController
class ApiController {
    
    @GetMapping("/api/user-info")
    fun getUserInfo(@AuthenticationPrincipal principal: OAuth2User): ResponseEntity<Map<String, Any>> {
        val name = principal.getAttribute<String>("name") ?: principal.name
        val email = principal.getAttribute<String>("email") ?: "Email not available"
        
        val userInfo = mapOf(
            "name" to name,
            "email" to email,
            "attributes" to principal.attributes
        )
        
        return ResponseEntity.ok(userInfo)
    }
}
