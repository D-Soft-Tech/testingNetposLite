package com.woleapp.testingnetposcontactlesslite

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.woleapp.testingnetposcontactlesslite.databinding.ActivityMainBinding
import com.woleapp.testingnetposcontactlesslite.utils.AppUtils
import com.woleapp.testingnetposcontactlesslite.utils.AppUtils.LAUNCH_NETPOS_CONTACTLESS_REQUEST_CODE

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var launchBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        launchBtn = binding.button
    }

    override fun onResume() {
        super.onResume()
        launchBtn.setOnClickListener {
            openOrDownloadApp()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LAUNCH_NETPOS_CONTACTLESS_REQUEST_CODE) {
            makeText(this, "$requestCode", Toast.LENGTH_LONG).show()
            if (resultCode == RESULT_OK) {
                Snackbar.make(binding.root, "RESULT_CODE_IS=====>$resultCode", Snackbar.LENGTH_LONG)
                    .show()
                data?.let {
                    Log.d("INTENT_LOGGER1", it.getStringExtra("message") ?: "No success Message")
                    makeText(
                        this,
                        it.getStringExtra("message"),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            } else {
                data?.let {
                    Log.d(
                        "ERROR_LOGGER1",
                        it.getStringExtra("error") ?: "Terminal Configuration Error"
                    )
                    Toast.makeText(this, it.getStringExtra("error"), Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun openOrDownloadApp() {
        val launcherIntent: Intent? =
            packageManager.getLaunchIntentForPackage("com.woleapp.netpos.contactless.zenith.debug")
        //                                                        "com.woleapp.netpos.contactless.zenith.debug"

        Log.d("LAUNCHER===>", "$launcherIntent")

        if (launcherIntent?.resolveActivity(packageManager) != null) { // Open app if installed
            launcherIntent.run {
                this@run.flags = 0
                this@run.action = AppUtils.ACTION_LAUNCH_NETPOS_CONTACTLESS
                this@run.putExtra("extra_terminal_id", "2057NTNH"/*"2033ALZP"*/) // required
                this@run.putExtra(
                    "extra_partner_id",
                    "ceb0b7bb-4611-4ee4-9e96-334b2d2f3c8b"/*"5de231d9-1be0-4c31-8658-6e15892f2b83"*/
                ) // required
                this@run.putExtra("extra_customer_name", "Jumia’s customer") // required
                this@run.putExtra("extra_business_name", "Jumia") // required
                this@run.putExtra("extra_partner_name", "Jumia") // required
                this@run.putExtra("extra_business_address", "Jumia office") // required
                startActivityForResult(
                    this@run,
                    LAUNCH_NETPOS_CONTACTLESS_REQUEST_CODE
                )
            }
        } else { // Send to play store to download or instant app solution
            try {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.woleapp.netpos.contactless")
                ).apply {
                    this@apply.flags = 0
                    this@apply.putExtra("extra_terminal_id", "2057NTNH"/*"2033ALZP"*/) // required
                    this@apply.putExtra(
                        "extra_partner_id",
                        "ceb0b7bb-4611-4ee4-9e96-334b2d2f3c8b"/*"5de231d9-1be0-4c31-8658-6e15892f2b83"*/
                    ) // required
                    this@apply.putExtra("extra_customer_name", "Jumia’s customer") // required
                    this@apply.putExtra("extra_business_name", "Jumia") // required
                    this@apply.putExtra("extra_partner_name", "Jumia") // required
                    this@apply.putExtra("extra_business_address", "Jumia office") // required
                }
                startActivityForResult(
                    intent,
                    LAUNCH_NETPOS_CONTACTLESS_REQUEST_CODE
                )
            } catch (anfe: ActivityNotFoundException) {
                anfe.localizedMessage?.let { Log.d("ACT_NOT_FOUND_EXCEPTION", it) }
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.woleapp.netpos.contactless")
                ).apply {
                    this@apply.flags = 0
                    this@apply.putExtra("extra_terminal_id", "2057NTNH"/*"2033ALZP"*/) // required
                    this@apply.putExtra(
                        "extra_partner_id",
                        "ceb0b7bb-4611-4ee4-9e96-334b2d2f3c8b"/*"5de231d9-1be0-4c31-8658-6e15892f2b83"*/
                    ) // required
                    this@apply.putExtra("extra_customer_name", "Jumia’s customer") // required
                    this@apply.putExtra("extra_business_name", "Jumia") // required
                    this@apply.putExtra("extra_partner_name", "Jumia") // required
                    this@apply.putExtra("extra_business_address", "Jumia office") // required
                }
                startActivityForResult(
                    intent,
                    LAUNCH_NETPOS_CONTACTLESS_REQUEST_CODE
                )
            }
        }
    }
}
