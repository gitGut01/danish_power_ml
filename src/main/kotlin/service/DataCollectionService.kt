package service

import kotlinx.coroutines.*
import model.PowerSystemRecord
import model.PowerSystemAvgRecord
import utils.PowerSystemCalculations
import org.slf4j.LoggerFactory

class DataCollectionService(
    private val powerSystemService: PowerSystemService,
    private val csvServicePowerSystemRecord: CsvService<PowerSystemRecord>,
    private val csvServicePowerSystemAvgRecord: CsvService<PowerSystemAvgRecord>
) {
    private val logger = LoggerFactory.getLogger(DataCollectionService::class.java)
    private var lastInCsvMinutes1DK: String? = null

    suspend fun start(pollingInterval: Long = 1000L, delayBetweenFetches: Long = 40_000L) {

        if (lastInCsvMinutes1DK == null){
            fetchData()
        }

        var cnt = 0
        while (true) {
            cnt ++
            delay(pollingInterval)

            val pollSuccess = pollApi()
            logger.info("Poll API ($cnt): $pollSuccess")
            if(pollSuccess){
                fetchData()
                cnt = 0
                logger.info("Sleep for ${delayBetweenFetches/1000} seconds")
                delay(delayBetweenFetches)
            }

        }
    }

    /**
     * Formats timestamp to 2025-01-01T00:00 format
     */
    private fun formatTimestamp(timestamp: String): String {
        return if (timestamp.length > 16) {
            timestamp.take(16)
        } else {
            timestamp
        }
    }

    suspend fun latestOnApiMinutes1Dk() : String?{
        val result = powerSystemService.getPowerSystemData(limit = 1, columns = listOf("Minutes1DK"))
        val response = result.getOrElse {
            logger.error("Error retrieving latest Minutes1DK: ${it.message}")
            return null
        }

        if (response.records.isNotEmpty()) {
            val pollMinutes1DK = formatTimestamp(response.records.first().minutes1DK)
            return pollMinutes1DK
        }
        return null
    }

    suspend fun pollApi() : Boolean{
        val pollMinutes1DK = latestOnApiMinutes1Dk() ?: return false

        if (pollMinutes1DK > lastInCsvMinutes1DK!!){
            return true
        }

        return false
    }

    suspend fun fetchData() : Boolean{
        val result = powerSystemService.getPowerSystemData(limit = 5)

        val response = result.getOrElse {
            logger.error("Error fetching data: ${it.message}")
            return false
        }

        if (response.records.isNotEmpty()) {
            val latestRecord = response.records.first()
            lastInCsvMinutes1DK = formatTimestamp(latestRecord.minutes1DK)
            csvServicePowerSystemRecord.saveRecord(latestRecord)

            val avgRecord = PowerSystemCalculations.calculateAverages(response.records)
            if (avgRecord != null) {
                csvServicePowerSystemAvgRecord.saveRecord(avgRecord)
                logger.info("New data found and saved. Averages calculated for ${response.records.size} records.")
            }
            return true
        }

        return false
    }
}

