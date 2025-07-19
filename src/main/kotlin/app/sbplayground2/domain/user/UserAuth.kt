package app.sbplayground2.domain.user

import app.sbplayground2.db.ReadRepository
import app.sbplayground2.db.WriteRepository
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table

@Table("user_auth")
class UserAuth(

    @Id
    val id: Long? = null,

    val type: IdType,

    val value: String,

    val source: IdSource,

    val data: Map<String, Any>? = null,

    val createdAt: java.time.OffsetDateTime,

    val updated_at: java.time.OffsetDateTime,
)

interface UserAuthRepository : ReadRepository<UserAuth, Long>, WriteRepository<UserAuth, Long> {

    @Query(
        """
        SELECT *
        FROM user_auth
        WHERE type = :type::id_type AND value = :value AND source = :source::id_source
    """
    )
    fun findByUniqueKey(type: IdType, value: String, source: IdSource): UserAuth?

    @Query(
        """
        INSERT INTO user_auth (type, value, source, data)
        VALUES (:type, :value, :source, :data)
        RETURNING *
    """
    )
    fun insert(type: IdType, value: String, source: IdSource, data: Map<String, Any>): UserAuth

    @Modifying
    @Query(
        """
        UPDATE user_auth
        SET data = :data
        WHERE type = :type::id_type AND value = :value AND source = :source::id_source
    """
    )
    fun updateData(type: IdType, value: String, source: IdSource, data: Map<String, String>)
}