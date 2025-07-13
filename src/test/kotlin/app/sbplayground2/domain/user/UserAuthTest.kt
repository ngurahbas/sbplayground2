package app.sbplayground2.domain.user

import app.sbplayground2.IntegrationTest
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class UserAuthTest @Autowired constructor(
    private val userAuthRepository: UserAuthRepository
): IntegrationTest() {
    @Test
    fun `crud works`(){
        var list = userAuthRepository.findAll().toList()
        assertTrue(list.isEmpty())
        val e1 = userAuthRepository.save(UserAuth(type = IdType.EMAIL, value = "hahahah@hihi.com", source = IdSource.GOOGLE))
        val e2 = userAuthRepository.save(UserAuth(type = IdType.PHONE, value = "+621234567890", source = IdSource.WA))

        list = userAuthRepository.findAll().toList()
        assertEquals(2, list.size)
    }
}