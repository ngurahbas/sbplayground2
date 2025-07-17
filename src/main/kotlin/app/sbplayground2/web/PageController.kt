package app.sbplayground2.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class PageController {

    @GetMapping
    fun home(model: Model): String {
        model.addAttribute("title", "Welcome to SB Playground")
        model.addAttribute("message", "Hello from Thymeleaf!")
        return "home" // This will resolve to src/main/resources/templates/home.html
    }
}
