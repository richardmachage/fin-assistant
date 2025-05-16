package com.transsion.financialassistant.background.model

import com.transsion.financialassistant.data.room.entities.unknown.UnknownEntity

data class UnknownDto(
    val unknownEntity: UnknownEntity? = null,
    val deviceMetadata: DeviceMetadata? = null,
)
