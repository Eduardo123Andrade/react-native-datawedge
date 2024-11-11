package com.datawedge.entities

import android.device.ScanManager
import android.device.scanner.configuration.PropertyID
import android.util.Log

class Urovo {
  private val scannerManager = ScanManager()


  fun setUrovoScannerConfig(intentAction: String) {
    val mode = scannerManager.outputMode
    val intentIds = intArrayOf(
      PropertyID.WEDGE_INTENT_ACTION_NAME,
      PropertyID.WEDGE_INTENT_DATA_STRING_TAG,
    )

    val valuesBufferString =
      arrayOf(
        intentAction,
        "com.symbol.datawedge.data_string",
      )

    scannerManager.setParameterString(intentIds, valuesBufferString)

    val outputTypeIds = intArrayOf(
      PropertyID.WEDGE_KEYBOARD_ENABLE
    )

    val valuesBufferInt = intArrayOf(
      0
    )

    scannerManager.setParameterInts(outputTypeIds, valuesBufferInt)
    Log.d("Mode", mode.toString())
  }

  fun resetConfig() {
    scannerManager.resetScannerParameters()
  }
}
