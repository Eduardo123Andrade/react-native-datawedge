package com.datawedge.receivebroadcast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.datawedge.IntentEmitter
import com.datawedge.ManagerAppList
import com.datawedge.ZebraProfile

class ResultReceiveBroadcast(private val managerAppList: ManagerAppList) : ReceiveBroadcast() {

  private val intentEmitter = IntentEmitter()

  override fun onReceive(context: Context?, intent: Intent?) {
    if (intent == null || context == null) return

    managerAppList.setContext(context)
    intentEmitter.setIntentContext(context)

    if (intent.hasExtra("RESULT_LIST")) {
      val resultList: ArrayList<Bundle> = intent.getParcelableArrayListExtra("RESULT_LIST")!!

      if (isAppAlreadyAssociated(resultList)) {
        return intentEmitter.requestActiveProfile()
      }
    }

    if (intent.hasExtra("com.symbol.datawedge.api.RESULT_GET_ACTIVE_PROFILE")) {
      val activeProfile =
              intent.getStringExtra("com.symbol.datawedge.api.RESULT_GET_ACTIVE_PROFILE")
      return managerAppList.checkActiveProfile(activeProfile!!)
    }

    if (intent.hasExtra("com.symbol.datawedge.api.RESULT_GET_CONFIG")) {
      return setProfileConfig(intent)
    }
  }

  private fun isAppAlreadyAssociated(results: List<Bundle>): Boolean {
    return results.any {
      it.containsKey("RESULT_CODE") && it["RESULT_CODE"] == "APP_ALREADY_ASSOCIATED"
    }
  }

  private fun setProfileConfig(intent: Intent) {
    val config = intent.getBundleExtra("com.symbol.datawedge.api.RESULT_GET_CONFIG")
    val profileName = config?.getString("PROFILE_NAME")

    if (profileName != null) {
      val profile = ZebraProfile(profileName, config)
      managerAppList.addProfile(profile)
    }
  }
}
