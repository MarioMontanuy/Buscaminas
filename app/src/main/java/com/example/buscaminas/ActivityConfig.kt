package com.example.buscaminas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat

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
