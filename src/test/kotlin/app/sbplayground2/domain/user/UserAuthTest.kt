package app.sbplayground2.domain.user

import app.sbplayground2.IntegrationTest
import app.sbplayground2.domain.user.IdSource.GOOGLE
import app.sbplayground2.domain.user.IdType.EMAIL
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals

class UserAuthTest : IntegrationTest() {
    @Autowired
    private lateinit var userAuthRepository: UserAuthRepository

    @Test
    fun `insert read`() {
        val insertData = userAuthRepository.insert(EMAIL, "om@om.com", GOOGLE, mapOf())
        val findById = userAuthRepository.findById(insertData.id!!).get()

        assertEquals(EMAIL, findById.type)
        assertEquals("om@om.com", findById.value)
        assertEquals(GOOGLE, findById.source)

        assertNotNull(findById.createdAt)
        assertNotNull(findById.modifiedAt)

        userAuthRepository.delete(findById)
    }

    @Test
    fun `unique key enforce`() {
        userAuthRepository.insert(EMAIL, "user@domain", GOOGLE, mapOf("key" to "value"))
        try {
            userAuthRepository.insert(EMAIL, "user@domain", GOOGLE, mapOf("key" to "another value"))
        } catch (e: Exception) {
            print("Exception while trying to add user: $e")
        }

        assertEquals(1, userAuthRepository.findAll().count())
    }
}