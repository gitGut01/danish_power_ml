package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PowerSystemRecord(
    @SerialName("Minutes1UTC")
    val minutes1UTC: String? = null,
    
    @SerialName("Minutes1DK")
    val minutes1DK: String,
    
    @SerialName("CO2Emission")
    val co2Emission: Double? = null,
    
    @SerialName("ProductionGe100MW")
    val productionGe100MW: Double? = null,
    
    @SerialName("ProductionLt100MW")
    val productionLt100MW: Double? = null,
    
    @SerialName("SolarPower")
    val solarPower: Double? = null,
    
    @SerialName("OffshoreWindPower")
    val offshoreWindPower: Double? = null,
    
    @SerialName("OnshoreWindPower")
    val onshoreWindPower: Double? = null,
    
    @SerialName("Exchange_Sum")
    val exchangeSum: Double? = null,
    
    @SerialName("Exchange_DK1_DE")
    val exchangeDK1DE: Double? = null,
    
    @SerialName("Exchange_DK1_NL")
    val exchangeDK1NL: Double? = null,
    
    @SerialName("Exchange_DK1_GB")
    val exchangeDK1GB: Double? = null,
    
    @SerialName("Exchange_DK1_NO")
    val exchangeDK1NO: Double? = null,
    
    @SerialName("Exchange_DK1_SE")
    val exchangeDK1SE: Double? = null,
    
    @SerialName("Exchange_DK1_DK2")
    val exchangeDK1DK2: Double? = null,
    
    @SerialName("Exchange_DK2_DE")
    val exchangeDK2DE: Double? = null,
    
    @SerialName("Exchange_DK2_SE")
    val exchangeDK2SE: Double? = null,
    
    @SerialName("Exchange_Bornholm_SE")
    val exchangeBornholmSE: Double? = null,
    
    @SerialName("aFRR_ActivatedDK1")
    val aFRRActivatedDK1: Double? = null,
    
    @SerialName("aFRR_ActivatedDK2")
    val aFRRActivatedDK2: Double? = null,
    
    @SerialName("mFRR_ActivatedDK1")
    val mFRRActivatedDK1: Double? = null,
    
    @SerialName("mFRR_ActivatedDK2")
    val mFRRActivatedDK2: Double? = null,
    
    @SerialName("ImbalanceDK1")
    val imbalanceDK1: Double? = null,
    
    @SerialName("ImbalanceDK2")
    val imbalanceDK2: Double? = null
)
