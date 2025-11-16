package service

import kotlinx.serialization.SerialName
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.RandomAccessFile
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

class CsvService<T : Any>(
    dataClass: KClass<T>,
    csvFilePath: String
) {
    
    private val csvFile = File(csvFilePath)
    
    private val sortedProperties = dataClass.memberProperties.sortedBy { it.name }
    
    private val fieldToPropertyMap = sortedProperties.associateBy { property ->
        property.annotations.find { it is SerialName }?.let { 
            (it as SerialName).value 
        } ?: property.name
    }
    
    private val fieldNames = fieldToPropertyMap.keys.toList()
    
    private val logger = LoggerFactory.getLogger(CsvService::class.java)
    
    init {
        logger.info("CsvService initialized with ${fieldNames.size} fields:")
        fieldNames.forEachIndexed { index, name ->
            logger.debug("  ${index + 1}. $name")
        }
        createCsvFileIfNotExists()
    }
    
    private fun createCsvFileIfNotExists() {
        if (!csvFile.exists()) {
            try {
                csvFile.createNewFile()
                writeHeader()
            } catch (e: IOException) {
                logger.error("Error creating CSV file: ${e.message}", e)
            }
        }
    }
    
    private fun writeHeader() {
        val header = fieldNames.joinToString(";")
        
        try {
            FileWriter(csvFile, false).use { writer ->
                writer.write(header + "\n")
            }
        } catch (e: IOException) {
            logger.error("Error writing header to CSV: ${e.message}", e)
        }
    }
    
    fun getLatestMinutes1DK(): String? {
        if (!csvFile.exists()) {
            logger.debug("CSV file does not exist")
            return null
        }
        
        var lastMinutes1DK =  try {
            RandomAccessFile(csvFile, "r").use { raf ->
                val fileLength = raf.length()
                if (fileLength == 0L) {
                    logger.debug("CSV file is empty")
                    return null
                }

                var pos = fileLength - 1
                val lastLine = StringBuilder()
                
                while (pos >= 0) {
                    raf.seek(pos)
                    val c = raf.read().toChar()
                    if (c == '\n' && lastLine.isNotEmpty()) {
                        break
                    }
                    if (c != '\n' && c != '\r') {
                        lastLine.insert(0, c)
                    }
                    pos--
                }
                
                if (lastLine.isEmpty()) {
                    logger.debug("No data line found")
                    return null
                }
                
                raf.seek(0)
                val headerLine = raf.readLine()
                val headers = headerLine.split(";")
                val minutes1DKIndex = headers.indexOf("Minutes1DK")
                
                if (minutes1DKIndex == -1) {
                    logger.error("Minutes1DK column not found in CSV header")
                    return null
                }
                
                val columns = lastLine.toString().split(";")
                if (columns.size > minutes1DKIndex) {
                    val latestMinutes1DK = columns[minutes1DKIndex]
                    logger.debug("Latest Minutes1DK from CSV: $latestMinutes1DK")
                    latestMinutes1DK
                } else {
                    logger.error("Invalid CSV format: not enough columns in last line")
                    null
                }
            }
        } catch (e: IOException) {
            logger.error("Error reading latest Minutes1DK: ${e.message}", e)
            null
        }

        if(lastMinutes1DK == "Minutes1DK"){
            lastMinutes1DK = null
        }
        return lastMinutes1DK
    }
    
    fun saveRecord(record: T): Boolean {        
        val csvLine = fieldNames.joinToString(";") { fieldName ->
            val property = fieldToPropertyMap[fieldName]
            if (property != null) {
                @Suppress("UNCHECKED_CAST")
                val value = property.get(record)
                value?.toString() ?: ""
            } else {
                ""
            }
        }
        
        return try {
            FileWriter(csvFile, true).use { writer ->
                writer.write(csvLine + "\n")
            }
            // Get the minutes1DK value for logging
            val minutes1DKProperty = fieldToPropertyMap["Minutes1DK"]
            val recordMinutes1DK = if (minutes1DKProperty != null) {
                @Suppress("UNCHECKED_CAST")
                minutes1DKProperty.get(record)?.toString() ?: "unknown"
            } else {
                "unknown"
            }
            logger.info("Saved new record for $recordMinutes1DK")
            true
        } catch (e: IOException) {
            logger.error("Error saving record to CSV: ${e.message}", e)
            false
        }
    }
}
