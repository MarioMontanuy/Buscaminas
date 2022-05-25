package com.example.buscaminas.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import com.example.buscaminas.R
import com.example.buscaminas.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ActivityConfig: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)
        supportFragmentManager.beginTransaction().replace(android.R.id.content, ActivityConfigFragment()).commit()
    }
}


class ActivityConfigFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
