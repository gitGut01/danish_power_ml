package model

import kotlinx.serialization.Serializable

@Serializable
data class PowerSystemResponse(
    val total: Long,
    val limit: Int,
    val dataset: String,
    val records: List<PowerSystemRecord>
)
