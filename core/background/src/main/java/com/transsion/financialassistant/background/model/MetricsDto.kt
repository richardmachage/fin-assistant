package com.transsion.financialassistant.background.model

import com.transsion.financialassistant.data.preferences.Metrics

data class MetricsDto(
    val deviceMetadata: DeviceMetadata? = null,
    val metrics: Metrics? = null
)
