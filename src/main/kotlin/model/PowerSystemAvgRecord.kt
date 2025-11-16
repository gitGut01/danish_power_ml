package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PowerSystemAvgRecord(
    @SerialName("Minutes1UTC")
    val minutes1UTC: String?,

    @SerialName("Minutes1DK")
    val minutes1DK: String,

    @SerialName("avg_CO2Emission")
    val avgCo2Emission: Double,

    @SerialName("avg_ProductionGe100MW")
    val avgProductionGe100MW: Double,

    @SerialName("avg_ProductionLt100MW")
    val avgProductionLt100MW: Double,

    @SerialName("avg_SolarPower")
    val avgSolarPower: Double,

    @SerialName("avg_OffshoreWindPower")
    val avgOffshoreWindPower: Double,

    @SerialName("avg_OnshoreWindPower")
    val avgOnshoreWindPower: Double,

    @SerialName("avg_Exchange_Sum")
    val avgExchangeSum: Double,

    @SerialName("avg_Exchange_DK1_DE")
    val avgExchangeDK1DE: Double,

    @SerialName("avg_Exchange_DK1_NL")
    val avgExchangeDK1NL: Double,

    @SerialName("avg_Exchange_DK1_GB")
    val avgExchangeDK1GB: Double,

    @SerialName("avg_Exchange_DK1_NO")
    val avgExchangeDK1NO: Double,

    @SerialName("avg_Exchange_DK1_SE")
    val avgExchangeDK1SE: Double,

    @SerialName("avg_Exchange_DK1_DK2")
    val avgExchangeDK1DK2: Double,

    @SerialName("avg_Exchange_DK2_DE")
    val avgExchangeDK2DE: Double,

    @SerialName("avg_Exchange_DK2_SE")
    val avgExchangeDK2SE: Double,

    @SerialName("avg_Exchange_Bornholm_SE")
    val avgExchangeBornholmSE: Double,

    @SerialName("avg_aFRR_ActivatedDK1")
    val avgAFRRActivatedDK1: Double?,

    @SerialName("avg_aFRR_ActivatedDK2")
    val avgAFRRActivatedDK2: Double?,

    @SerialName("avg_mFRR_ActivatedDK1")
    val avgMFRRActivatedDK1: Double?,

    @SerialName("avg_mFRR_ActivatedDK2")
    val avgMFRRActivatedDK2: Double?,

    @SerialName("avg_ImbalanceDK1")
    val avgImbalanceDK1: Double?,

    @SerialName("avg_ImbalanceDK2")
    val avgImbalanceDK2: Double?
)
