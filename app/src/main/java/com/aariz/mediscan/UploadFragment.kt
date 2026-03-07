package com.aariz.mediscan

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class UploadFragment : Fragment() {

    private var selectedType: String = "lab"
    private var selectedFileUri: Uri? = null

    private val filePicker = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedFileUri = result.data?.data
            goToProcessing()
        }
    }

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) =
        i.inflate(R.layout.fragment_upload, c, false)

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)

        // Back → Analysis
        v.findViewById<View>(R.id.btnBack).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        // Browse / drop zone / analyze
        v.findViewById<View>(R.id.btnBrowse).setOnClickListener { openFilePicker() }
        v.findViewById<View>(R.id.dropZone).setOnClickListener  { openFilePicker() }
        v.findViewById<View>(R.id.btnAnalyze).setOnClickListener {
            if (selectedFileUri != null) goToProcessing() else openFilePicker()
        }

        // Type card selection
        val typeCards = mapOf(
            "lab"     to v.findViewById<LinearLayout>(R.id.typeLab),
            "ecg"     to v.findViewById(R.id.typeEcg),
            "mri"     to v.findViewById(R.id.typeMri),
            "xray"    to v.findViewById(R.id.typeXray),
            "ct"      to v.findViewById(R.id.typeCt),
            "retinal" to v.findViewById(R.id.typeRetinal)
        )

        typeCards.forEach { (type, card) ->
            card.setOnClickListener {
                selectedType = type
                typeCards.forEach { (t, c) ->
                    c.background = ContextCompat.getDrawable(
                        requireContext(),
                        if (t == selectedType) R.drawable.bg_type_on else R.drawable.bg_type_off
                    )
                }
            }
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/pdf", "image/jpeg", "image/png"))
        }
        filePicker.launch(intent)
    }

    private fun goToProcessing() {
        (activity as? MainActivity)?.navigateTo(ProcessingFragment(), "processing")
    }
}