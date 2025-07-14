package app.sbplayground2.domain.user

import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
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
    @Query(
        """
           SELECT *
           FROM user_auth
           WHERE type = :type::id_type AND value = :value AND source = :source::id_source
    """
    )
    fun findByUniqueKey(type: IdType, value: String, source: IdSource): UserAuth
}