package com.aariz.mediscan;
import android.view.View
import android.widget.FrameLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment

/**
 * Base class for all analysis screens (Lab, ECG, MRI, X-Ray, CT, Retinal, Prescription).
 * Handles the Patient/Doctor view toggle, back button, and Share→Doctor button.
 */
abstract class BaseDualViewFragment : Fragment() {

    protected fun setupDualView(view: View) {
        // Back button
        view.findViewById<FrameLayout>(R.id.btnBack)?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val flipper = view.findViewById<ViewFlipper>(R.id.viewFlipper)
        val patientBtn = view.findViewById<TextView>(R.id.btnPatientView)
        val doctorBtn = view.findViewById<TextView>(R.id.btnDoctorView)

        patientBtn?.setOnClickListener {
            flipper?.displayedChild = 0
            setToggleActive(view, isPatient = true)
        }

        doctorBtn?.setOnClickListener {
            flipper?.displayedChild = 1
            setToggleActive(view, isPatient = false)
        }

        // "Share with Doctor" button inside patient panel → switch to doctor view
        view.findViewById<android.widget.Button>(R.id.btnShareWithDoctor)?.setOnClickListener {
            flipper?.displayedChild = 1
            setToggleActive(view, isPatient = false)
            // Scroll doctor panel to top
            view.findViewById<ScrollView>(R.id.doctorPanel)?.scrollTo(0, 0)
        }
    }

    private fun setToggleActive(view: View, isPatient: Boolean) {
        val patientBtn = view.findViewById<TextView>(R.id.btnPatientView)
        val doctorBtn = view.findViewById<TextView>(R.id.btnDoctorView)

        if (isPatient) {
            patientBtn?.setBackgroundResource(R.drawable.bg_vtoggle_active)
            patientBtn?.setTextColor(requireContext().getColor(R.color.white))
            doctorBtn?.setBackgroundResource(android.R.color.transparent)
            doctorBtn?.setTextColor(requireContext().getColor(R.color.muted))
        } else {
            doctorBtn?.setBackgroundResource(R.drawable.bg_vtoggle_active)
            doctorBtn?.setTextColor(requireContext().getColor(R.color.white))
            patientBtn?.setBackgroundResource(android.R.color.transparent)
            patientBtn?.setTextColor(requireContext().getColor(R.color.muted))
        }
    }

    /** Populate a doc_param include with label + value text and optional color. */
    protected fun setDocParam(
        paramView: View?,
        label: String,
        value: String,
        valueColor: Int? = null
    ) {
        paramView ?: return
        paramView.findViewById<TextView>(R.id.paramLabel)?.text = label
        val valView = paramView.findViewById<TextView>(R.id.paramValue)
        valView?.text = value
        valueColor?.let { valView?.setTextColor(it) }
    }

    /** Populate a lab row include. */
    protected fun setLabRow(
        rowView: View?,
        name: String,
        value: String,
        ref: String,
        flag: String,
        valueColor: Int? = null,
        flagColor: Int? = null
    ) {
        rowView ?: return
        rowView.findViewById<TextView>(R.id.labName)?.text = name
        val valView = rowView.findViewById<TextView>(R.id.labValue)
        valView?.text = value
        valueColor?.let { valView?.setTextColor(it) }
        rowView.findViewById<TextView>(R.id.labRef)?.text = ref
        val flagView = rowView.findViewById<TextView>(R.id.labFlag)
        flagView?.text = flag
        flagColor?.let { flagView?.setTextColor(it) }
    }
}
