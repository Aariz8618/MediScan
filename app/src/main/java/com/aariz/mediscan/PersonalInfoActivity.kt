package com.aariz.mediscan;

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * PersonalInfoActivity
 *
 * Lets user set: Full Name · Phone · Age · Sex · Blood Group · City
 *
 * The name they enter here is exactly what appears in the profile header.
 * No "Dr." prefix is added automatically.
 *
 * Back button (header ←) OR device back → finish() without saving.
 * "Save Changes" → validates name → saves all fields → finish().
 * ProfileFragment.onResume() picks up changes automatically.
 */
class PersonalInfoActivity : AppCompatActivity() {

    private lateinit var prefs: UserPreferences

    private lateinit var etName:  EditText
    private lateinit var etPhone: EditText
    private lateinit var etAge:   EditText
    private lateinit var spinSex: Spinner
    private lateinit var spinBlood: Spinner
    private lateinit var etCity:  EditText

    private val sexOptions   = listOf("Select", "Male", "Female", "Other", "Prefer not to say")
    private val bloodOptions = listOf("Select", "A+", "A−", "B+", "B−", "AB+", "AB−", "O+", "O−")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)

        prefs = UserPreferences(this)

        bindViews()
        setupSpinners()
        loadSavedData()
        setupListeners()
    }

    private fun bindViews() {
        etName    = findViewById(R.id.et_name)
        etPhone   = findViewById(R.id.et_phone)
        etAge     = findViewById(R.id.et_age)
        spinSex   = findViewById(R.id.spinner_sex)
        spinBlood = findViewById(R.id.spinner_blood)
        etCity    = findViewById(R.id.et_city)
    }

    private fun setupSpinners() {
        spinSex.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, sexOptions
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        spinBlood.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, bloodOptions
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
    }

    private fun loadSavedData() {
        etName.setText(prefs.fullName)
        etPhone.setText(prefs.phone)
        etAge.setText(prefs.age)
        etCity.setText(prefs.city)

        val sexIdx = sexOptions.indexOf(prefs.sex).takeIf { it >= 0 } ?: 0
        spinSex.setSelection(sexIdx)

        val bloodIdx = bloodOptions.indexOf(prefs.bloodGroup).takeIf { it >= 0 } ?: 0
        spinBlood.setSelection(bloodIdx)
    }

    private fun setupListeners() {
        // Header back button
        findViewById<FrameLayout>(R.id.btn_back).setOnClickListener { finish() }

        // Save button
        findViewById<TextView>(R.id.btn_save).setOnClickListener { save() }
    }

    private fun save() {
        val name = etName.text.toString().trim()

        if (name.isBlank()) {
            etName.error = "Name is required"
            etName.requestFocus()
            return
        }

        prefs.fullName   = name
        prefs.phone      = etPhone.text.toString().trim()
        prefs.age        = etAge.text.toString().trim()
        prefs.city       = etCity.text.toString().trim()

        val selectedSex   = spinSex.selectedItem?.toString() ?: ""
        prefs.sex         = if (selectedSex == "Select") "" else selectedSex

        val selectedBlood = spinBlood.selectedItem?.toString() ?: ""
        prefs.bloodGroup  = if (selectedBlood == "Select") "" else selectedBlood

        Toast.makeText(this, "Saved ✓", Toast.LENGTH_SHORT).show()
        finish()   // Returns to ProfileFragment → onResume() refreshes UI
    }
}