package com.aariz.mediscan

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class UploadFragment : Fragment() {

    private var selectedUri: Uri? = null
    private var selectedType: String? = "lab"

    private val pickFile = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedUri = it
            Toast.makeText(requireContext(), "File selected ✓", Toast.LENGTH_SHORT).show()
        }
    }

    private val typeCards by lazy {
        listOf(
            R.id.typeLab, R.id.typeEcg, R.id.typeMri, R.id.typeXray,
            R.id.typeCt, R.id.typeRetinal, R.id.typeRx, R.id.typeOther
        )
    }

    // Maps card ID → API image_type value
    private val typeMap = mapOf(
        R.id.typeLab     to "lab",
        R.id.typeEcg     to "ecg",
        R.id.typeMri     to "brain_mri",
        R.id.typeXray    to "chest_xray",
        R.id.typeCt      to "lung_ct",
        R.id.typeRetinal to "retinal",
        R.id.typeRx      to "prescription",
        R.id.typeOther   to "lab"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_upload, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<FrameLayout>(R.id.btnBack).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        view.findViewById<LinearLayout>(R.id.dropZone).setOnClickListener {
            pickFile.launch("*/*")
        }

        view.findViewById<Button>(R.id.btnBrowse).setOnClickListener {
            pickFile.launch("*/*")
        }

        // Type card selection
        typeCards.forEach { id ->
            view.findViewById<LinearLayout>(id)?.setOnClickListener { card ->
                deselectAll(view)
                card.setBackgroundResource(R.drawable.bg_type_card_active)
                selectedType = typeMap[id] ?: "lab"
            }
        }

        // Analyze button → go to ProcessingFragment with data
        view.findViewById<Button>(R.id.btnAnalyze).setOnClickListener {
            val uri = selectedUri
            if (uri == null) {
                Toast.makeText(requireContext(), "Please select a file first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val type = selectedType
            if (type.isNullOrBlank()) {
                Toast.makeText(requireContext(), "Please select report type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            navigateToProcessing(uri, type)
        }
    }

    private fun deselectAll(view: View) {
        typeCards.forEach { id ->
            view.findViewById<LinearLayout>(id)?.setBackgroundResource(R.drawable.bg_type_card)
        }
    }

    private fun navigateToProcessing(uri: Uri, type: String) {
        val appCtx = requireContext().applicationContext
        val prefs = appCtx.getSharedPreferences("mediscan_prefs", android.content.Context.MODE_PRIVATE)
        val profileName = prefs.getString("profile_name", "Patient").orEmpty().ifBlank { "Patient" }
        val profileAge = prefs.getInt("profile_age", 25)
        val profileGender = prefs.getString("profile_gender", "Not specified").orEmpty().ifBlank { "Not specified" }

        val fragment = ProcessingFragment().apply {
            arguments = Bundle().apply {
                putString("file_uri", uri.toString())
                putString("analysis_type", type)
                putString("patient_name", profileName)
                putInt("patient_age", profileAge)
                putString("patient_gender", profileGender)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
