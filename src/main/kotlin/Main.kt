import kotlinx.coroutines.runBlocking
import model.PowerSystemAvgRecord
import service.PowerSystemService
import service.CsvService
import service.DataCollectionService
import model.PowerSystemRecord
import org.slf4j.LoggerFactory

fun main() = runBlocking {
    val logger = LoggerFactory.getLogger("Main")
    val powerSystemService = PowerSystemService()
    val csvServicePowerSystemRecord = CsvService(PowerSystemRecord::class, "csv/power_system_data.csv")
    val csvServicePowerSystemAvgRecord = CsvService(PowerSystemAvgRecord::class, "csv/power_system_avg_data.csv")
    val dataCollectionService = DataCollectionService(
        powerSystemService,
        csvServicePowerSystemRecord,
        csvServicePowerSystemAvgRecord
    )
    
    try {
        logger.info("=== Danish Power System Data Collector ===")

        dataCollectionService.start()
        
    } catch (e: Exception) {
        logger.error("Application error: ${e.message}", e)
    } finally {
        powerSystemService.close()
        logger.info("Application shutdown complete")
    }
}

