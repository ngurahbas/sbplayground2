package app.sbplayground2.domain.user

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository

@Table("user_auth")
class UserAuth(
    @Id
    val id: Long,

    val source: IdSource,

    val type: IdType,

    val value: String,

    val data: Object,
)

interface UserAuthRepository : CrudRepository<UserAuth, Long>