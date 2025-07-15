package app.sbplayground2.common

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
interface ReadRepository<T , ID> : Repository<T, ID> {
    fun findDataById(id: ID): T?

    fun count(): Long
}

@NoRepositoryBean
interface WriteRepository<T , ID> : Repository<T, ID> {
    fun deleteById(id: ID)
}