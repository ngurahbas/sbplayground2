package app.sbplayground2.domain.user

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class UserAuthTest @Autowired constructor(
    private val userAuthRepository: UserAuthRepository
)
{
    @Test
    fun `crud works`(){
        val list = userAuthRepository.findAll().toList()
        assertTrue(list.isEmpty())
    }
}