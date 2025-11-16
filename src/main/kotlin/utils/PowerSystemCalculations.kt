package utils

import model.PowerSystemRecord
import model.PowerSystemAvgRecord

object PowerSystemCalculations {
    
    fun calculateAverages(records: List<PowerSystemRecord>): PowerSystemAvgRecord? {
        if (records.isEmpty()) return null
        
        val latestRecord = records.maxByOrNull { it.minutes1DK }
            ?: return null
        
        return PowerSystemAvgRecord(
            minutes1UTC = latestRecord.minutes1UTC,
            minutes1DK = latestRecord.minutes1DK,
            avgCo2Emission = records.mapNotNull { it.co2Emission }.average(),
            avgProductionGe100MW = records.mapNotNull { it.productionGe100MW }.average(),
            avgProductionLt100MW = records.mapNotNull { it.productionLt100MW }.average(),
            avgSolarPower = records.mapNotNull { it.solarPower }.average(),
            avgOffshoreWindPower = records.mapNotNull { it.offshoreWindPower }.average(),
            avgOnshoreWindPower = records.mapNotNull { it.onshoreWindPower }.average(),
            avgExchangeSum = records.mapNotNull { it.exchangeSum }.average(),
            avgExchangeDK1DE = records.mapNotNull { it.exchangeDK1DE }.average(),
            avgExchangeDK1NL = records.mapNotNull { it.exchangeDK1NL }.average(),
            avgExchangeDK1GB = records.mapNotNull { it.exchangeDK1GB }.average(),
            avgExchangeDK1NO = records.mapNotNull { it.exchangeDK1NO }.average(),
            avgExchangeDK1SE = records.mapNotNull { it.exchangeDK1SE }.average(),
            avgExchangeDK1DK2 = records.mapNotNull { it.exchangeDK1DK2 }.average(),
            avgExchangeDK2DE = records.mapNotNull { it.exchangeDK2DE }.average(),
            avgExchangeDK2SE = records.mapNotNull { it.exchangeDK2SE }.average(),
            avgExchangeBornholmSE = records.mapNotNull { it.exchangeBornholmSE }.average(),
            avgAFRRActivatedDK1 = calculateNullableAverage(records.mapNotNull { it.aFRRActivatedDK1 }),
            avgAFRRActivatedDK2 = calculateNullableAverage(records.mapNotNull { it.aFRRActivatedDK2 }),
            avgMFRRActivatedDK1 = calculateNullableAverage(records.mapNotNull { it.mFRRActivatedDK1 }),
            avgMFRRActivatedDK2 = calculateNullableAverage(records.mapNotNull { it.mFRRActivatedDK2 }),
            avgImbalanceDK1 = calculateNullableAverage(records.mapNotNull { it.imbalanceDK1 }),
            avgImbalanceDK2 = calculateNullableAverage(records.mapNotNull { it.imbalanceDK2 })
        )
    }
    
    private fun calculateNullableAverage(values: List<Double>): Double? {
        return if (values.isEmpty()) null else values.average()
    }
}
