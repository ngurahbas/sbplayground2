package app.sbplayground2.web

import app.sbplayground2.domain.user.AuthenticatedUser
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/")
    fun home(model: Model, @AuthenticationPrincipal principal: AuthenticatedUser?): String {
        model.addAttribute("title", "Welcome to SB Playground")
        
        if (principal != null) {
            model.addAttribute("authenticated", true)
            model.addAttribute("username", principal.name)
            model.addAttribute("message", "Hello, ${principal.name}! You are logged in with ${principal.idSource}.")
        } else {
            model.addAttribute("authenticated", false)
            model.addAttribute("message", "Hello from Thymeleaf! Login with Google to see more.")
        }
        
        return "home" // This will resolve to src/main/resources/templates/home.html
    }
}