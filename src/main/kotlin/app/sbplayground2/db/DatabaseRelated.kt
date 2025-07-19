package app.sbplayground2.db

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.postgresql.util.PGobject
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.jdbc.core.mapping.JdbcValue
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import java.sql.JDBCType


@WritingConverter
class JsonNodeToJsonb(
    private val mapper: ObjectMapper = jacksonObjectMapper()
) : Converter<JsonNode, JdbcValue> {

    override fun convert(source: JsonNode): JdbcValue {
        val pg = PGobject().apply {
            type = "jsonb"
            value = mapper.writeValueAsString(source)
        }
        return JdbcValue.of(pg, JDBCType.OTHER)
    }
}

@ReadingConverter
class JsonbToJsonNode(
    private val mapper: ObjectMapper = jacksonObjectMapper()
) : Converter<PGobject, JsonNode> {

    override fun convert(source: PGobject): JsonNode =
        mapper.readTree(source.value)
}

@WritingConverter
class MapToJsonb(
    private val mapper: ObjectMapper = jacksonObjectMapper()
) : Converter<Map<String, Any>, JdbcValue> {
    override fun convert(source: Map<String, Any>): JdbcValue {
        val pg = PGobject().apply {
            type = "jsonb"
            value = mapper.writeValueAsString(source)
        }
        return JdbcValue.of(pg, JDBCType.OTHER)
    }
}

@ReadingConverter
class JsonbToMap(
    private val mapper: ObjectMapper = jacksonObjectMapper()
) : Converter<PGobject, Map<String, Any>> {
    override fun convert(source: PGobject): Map<String, Any> =
        mapper.readValue(
            source.value,
            mapper.typeFactory.constructMapType(Map::class.java, String::class.java, Any::class.java)
        )
}

@Configuration
class JdbcConfig : AbstractJdbcConfiguration() {
    override fun userConverters(): List<*> {
        return listOf(JsonNodeToJsonb(), JsonbToJsonNode(), MapToJsonb(), JsonbToMap())
    }
}