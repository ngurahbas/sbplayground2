package app.sbplayground2.domain.user

import app.sbplayground2.IntegrationTest
import app.sbplayground2.domain.user.IdSource.GOOGLE
import app.sbplayground2.domain.user.IdType.EMAIL
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertNull
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals

class UserAuthTest : IntegrationTest() {
    @Autowired
    private lateinit var userAuthRepository: UserAuthRepository

    @Test
    fun `insert read update delete`() {
        val insertData = userAuthRepository.insert(EMAIL, "om@om.com", GOOGLE, mapOf())
        val id = insertData.id!!
        val findById = userAuthRepository.findDataById(id)

        if (findById == null) {
            assert(false) { "Should not reach this part. Insertion was failed" }
            return
        }

        assertEquals(EMAIL, findById.type)
        assertEquals("om@om.com", findById.value)
        assertEquals(GOOGLE, findById.source)
        assertNotNull(findById.createdAt)
        assertNotNull(findById.updated_at)
        Thread.sleep(1)
        userAuthRepository.updateData(findById.type, findById.value, findById.source, mapOf("key" to "new value"))

        val afterUpdate = userAuthRepository.findDataById(id)
        assertEquals("new value", afterUpdate?.data?.get("key"))

        userAuthRepository.deleteById(id)
        val noExistsById = userAuthRepository.findDataById(id)

        assertNull(noExistsById)
    }

    @Test
    fun `unique key enforce`() {
        userAuthRepository.insert(EMAIL, "user@domain", GOOGLE, mapOf("key" to "value"))
        try {
            userAuthRepository.insert(EMAIL, "user@domain", GOOGLE, mapOf("key" to "another value"))
        } catch (e: Exception) {
            print("Exception while trying to add user: $e")
        }

        val findByUniqueKey = userAuthRepository.findByUniqueKey(EMAIL, "user@domain", GOOGLE)
        assertNotNull(findByUniqueKey)
        assertEquals("value", findByUniqueKey.data?.get("key"))

        assertEquals(1, userAuthRepository.count())

        userAuthRepository.deleteAll()
    }
}