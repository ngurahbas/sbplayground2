package app.sbplayground2.domain.user

import app.sbplayground2.IntegrationTest
import app.sbplayground2.domain.user.IdSource.GOOGLE
import app.sbplayground2.domain.user.IdType.EMAIL
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals

class UserAuthTest : IntegrationTest() {
    @Autowired
    private lateinit var userAuthRepository: UserAuthRepository

    @Test
    fun `create read`() {
        val ua1 = UserAuth(null, EMAIL, "user@domain", GOOGLE)
        val findById = userAuthRepository.findById(userAuthRepository.save(ua1).id!!).get()

        assertEquals(EMAIL, findById.type)
        assertEquals("user@domain", findById.value)
        assertEquals(GOOGLE, findById.source)

        userAuthRepository.delete(findById)

    }

    @Test
    fun `unique key enforce`() {
        val ua1 = UserAuth(null, EMAIL, "user@domain", GOOGLE, mapOf("key" to "value"))
        val ua2 = UserAuth(null, EMAIL, "user@domain", GOOGLE, mapOf("key" to "another value"))

        userAuthRepository.save(ua1)
        try {
            userAuthRepository.save(ua2)
        } catch (e: Exception) {}

        assertEquals(1, userAuthRepository.findAll().count())
    }
}