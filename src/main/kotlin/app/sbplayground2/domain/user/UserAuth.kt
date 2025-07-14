package app.sbplayground2.domain.user

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository

@Table("user_auth")
class UserAuth(
    @Id
    val id: Long? = null,

    val type: IdType,

    val value: String,

    val source: IdSource,

    val data: Map<String, Any>? = null
)

interface UserAuthRepository : CrudRepository<UserAuth, Long> {
    fun findUserAuthsByTypeAndValueAndSource(type: IdType, value: String, source: IdSource): List<UserAuth>
}