package com.datawedge

import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.datawedge.entities.Urovo
import com.datawedge.receivebroadcast.ResultReceiveBroadcast
import com.datawedge.receivebroadcast.ScannerReceiveBroadcast
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactMethod

class DatawedgeModule internal constructor(context: ReactApplicationContext) :
        DatawedgeSpec(context), LifecycleEventListener {
  private val _context: ReactApplicationContext = context
  private val managerAppList = ManagerAppList()

  private var myBroadcastReceiver = ScannerReceiveBroadcast(context)
  private var resultReceiveBroadcast = ResultReceiveBroadcast(managerAppList)

  private val _filter: IntentFilter = IntentFilter()
  private var _id: String? = null
  private var _intentAction: String? = null
  private val urovo = Urovo()

  init {
    _filter.addCategory(Intent.CATEGORY_DEFAULT)
    _context.addLifecycleEventListener(this)

    resultReceiveBroadcast.register(_context, getResultFilter())
  }

  override fun getName(): String {
    return NAME
  }

  private fun onRegisterReceiver() {
    if (_id == null) return

    myBroadcastReceiver.id = _id
    myBroadcastReceiver.action = _intentAction
    myBroadcastReceiver.register(_context, _filter)
  }

  @ReactMethod
  fun onInit(id: String) {
    _id = id
    onRegisterReceiver()
  }

  @ReactMethod
  fun createProfile(profileName: String, intentAction: String, keystrokeEnabled: Boolean = false) {
    _intentAction = intentAction
    _filter.addAction(intentAction)
    onRegisterReceiver()

    val scanner = Scanner(profileName, intentAction, keystrokeEnabled, _context)
    managerAppList.setProfileName(profileName)
    managerAppList.setPackageName(_context.packageName)

    scanner.createProfile()
    urovo.setUrovoScannerConfig(intentAction)
  }

  companion object {
    const val NAME   = "Datawedge"
  }

  override fun onHostResume() {
    onRegisterReceiver()
    urovo.setUrovoScannerConfig(
      intentAction = _intentAction ?: ""
    )
  }

  override fun onHostPause() {
    myBroadcastReceiver.unregister(_context)
  }

  override fun onHostDestroy() {
    myBroadcastReceiver.unregister(_context)
    urovo.resetConfig()
  }

  private fun getResultFilter(): IntentFilter {
    val filter = IntentFilter()
    filter.addCategory(Intent.CATEGORY_DEFAULT)
    filter.addAction("com.symbol.datawedge.api.RESULT_ACTION")
    return filter
  }

}
