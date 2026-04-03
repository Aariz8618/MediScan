package com.aariz.mediscan

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

class ProcessingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_processing, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<FrameLayout>(R.id.btnBack)?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        view.findViewById<Button>(R.id.btnViewResults)?.visibility = View.GONE

        // Get arguments passed from UploadFragment
        val fileUriStr    = arguments?.getString("file_uri")
        val analysisType  = arguments?.getString("analysis_type").orEmpty()
        val patientName   = arguments?.getString("patient_name")  ?: "Patient"
        val patientAge    = arguments?.getInt("patient_age", 25)  ?: 25
        val patientGender = arguments?.getString("patient_gender") ?: "Not specified"

        if (fileUriStr == null) {
            showError(view, "No file provided")
            return
        }
        if (analysisType.isBlank()) {
            showError(view, "No analysis type selected")
            return
        }

        val fileUri = Uri.parse(fileUriStr)
        callApi(view, fileUri, analysisType, patientName, patientAge, patientGender)
    }

    private fun callApi(
        view: View,
        fileUri: Uri,
        analysisType: String,
        patientName: String,
        patientAge: Int,
        patientGender: String
    ) {
        lifecycleScope.launch {
            try {
                updateStatus(view, "Reading file...")

                // Read file bytes
                val inputStream: InputStream = requireContext().contentResolver.openInputStream(fileUri)
                    ?: throw Exception("Cannot open file")
                val fileBytes = inputStream.readBytes()
                inputStream.close()

                // Get filename and mime type
                val fileName = getFileName(fileUri) ?: "upload"
                val mimeType = requireContext().contentResolver.getType(fileUri) ?: "application/octet-stream"

                updateStatus(view, "Sending to MedScan AI...")

                // Build multipart file part
                val requestFile = fileBytes.toRequestBody(mimeType.toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("file", fileName, requestFile)

                // Build other parts
                val nameBody   = patientName.toRequestBody("text/plain".toMediaTypeOrNull())
                val ageBody    = patientAge.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val genderBody = patientGender.toRequestBody("text/plain".toMediaTypeOrNull())

                updateStatus(view, "Analyzing with AI...")

                // Call the right endpoint based on type
                val response = when (analysisType) {
                    "prescription" -> RetrofitClient.api.analyzePrescription(
                        filePart, nameBody, ageBody, genderBody
                    )
                    "lab" -> RetrofitClient.api.analyzeReport(
                        filePart, nameBody, ageBody, genderBody
                    )
                    else -> RetrofitClient.api.analyzeImage(
                        filePart,
                        analysisType.toRequestBody("text/plain".toMediaTypeOrNull()),
                        nameBody, ageBody, genderBody
                    )
                }

                val body = response.body()
                if (response.isSuccessful && body != null && body.success) {
                    // Store result in shared singleton
                    AnalysisResult.patientReport = body.patientReport
                    AnalysisResult.doctorReport  = body.doctorReport
                    AnalysisResult.reportType    = body.reportType
                    AnalysisResult.modelUsed     = body.modelUsed ?: ""
                    AnalysisResult.confidence    = body.confidence ?: 0f
                    AnalysisResult.patientName   = body.patientName
                    AnalysisResult.saveLatestAndHistory(requireContext().applicationContext)

                    updateStatus(view, "Analysis complete!")
                    navigateToResults(analysisType)
                } else {
                    val errorMsg = body?.error ?: response.message()
                    showError(view, "API Error: $errorMsg")
                }

            } catch (e: Exception) {
                showError(view, "Error: ${e.message}")
            }
        }
    }

    private fun updateStatus(view: View, message: String) {
        view.findViewById<TextView>(R.id.tvProcessingStatus)?.text = message
    }

    private fun showError(view: View, message: String) {
        updateStatus(view, message)
        view.findViewById<Button>(R.id.btnViewResults)?.apply {
            visibility = View.VISIBLE
            text = "Go Back"
            setOnClickListener { parentFragmentManager.popBackStack() }
        }
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun getFileName(uri: Uri): String? {
        var name: String? = null
        requireContext().contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && idx >= 0) name = cursor.getString(idx)
        }
        return name ?: uri.lastPathSegment
    }

    private fun navigateToResults(analysisType: String) {
        if (!isAdded) return
        val fragment: Fragment = when (analysisType) {
            "lab"         -> LabFragment()
            "brain_mri"   -> MriFragment()
            "ecg"         -> EcgFragment()
            "chest_xray"  -> XrayFragment()
            "lung_ct"     -> CtFragment()
            "retinal"     -> RetinalFragment()
            "prescription"-> PrescriptionFragment()
            "skin"        -> MriFragment() // reuse generic result screen
            else          -> LabFragment()
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
