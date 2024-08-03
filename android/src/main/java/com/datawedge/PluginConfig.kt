package com.datawedge

data class PluginConfig(
        val resetConfig: Boolean? = false, // Default value of false
        val pluginName: String,
        val outputConfig: OutputConfig
)
