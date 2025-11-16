package service

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import model.PowerSystemResponse

class PowerSystemService {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }
    
    private val baseUrl = "https://api.energidataservice.dk/dataset"
    

    suspend fun getPowerSystemData(
        limit: Int = 5, 
        columns: List<String> = emptyList(),
        start: String = "",
        end: String = ""
        //doSortDesc: Boolean = false
    ): Result<PowerSystemResponse> {

        return try {
            val response: PowerSystemResponse = client.get("$baseUrl/PowerSystemRightNow") {
                parameter("limit", limit)
                if (columns.isNotEmpty()) {
                    parameter("columns", columns.joinToString(","))
                }
                if (start.isNotEmpty()) {
                    parameter("start", start)
                }
                if (end.isNotEmpty()){
                    parameter("end", end)
                }
                //if (doSortDesc){
                //    parameter("sort", "Minutes1DK")
                //}

            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun close() {
        client.close()
    }
}
