package app.sbplayground2.domain.user

import org.springframework.context.annotation.Configuration
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

    val data: Object? = null,
)

interface UserAuthRepository : CrudRepository<UserAuth, Long>