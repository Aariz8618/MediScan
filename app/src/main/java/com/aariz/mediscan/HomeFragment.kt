package com.aariz.mediscan;
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Quick access chips → navigate to analysis screens
        view.findViewById<LinearLayout>(R.id.chipLab).setOnClickListener {
            navigateTo(LabFragment())
        }
        view.findViewById<LinearLayout>(R.id.chipEcg).setOnClickListener {
            navigateTo(EcgFragment())
        }
        view.findViewById<LinearLayout>(R.id.chipMri).setOnClickListener {
            navigateTo(MriFragment())
        }
        view.findViewById<LinearLayout>(R.id.chipXray).setOnClickListener {
            navigateTo(XrayFragment())
        }
        view.findViewById<LinearLayout>(R.id.chipRetinal).setOnClickListener {
            navigateTo(RetinalFragment())
        }
        view.findViewById<LinearLayout>(R.id.chipRx).setOnClickListener {
            navigateTo(PrescriptionFragment())
        }

        // AI strip → Lab
        view.findViewById<LinearLayout>(R.id.aiStrip).setOnClickListener {
            navigateTo(LabFragment())
        }

        // Recent report items
        view.findViewById<LinearLayout>(R.id.reportCbc).setOnClickListener {
            navigateTo(LabFragment())
        }
        view.findViewById<LinearLayout>(R.id.reportEcg).setOnClickListener {
            navigateTo(EcgFragment())
        }
        view.findViewById<LinearLayout>(R.id.reportMri).setOnClickListener {
            navigateTo(MriFragment())
        }
        view.findViewById<LinearLayout>(R.id.reportXray).setOnClickListener {
            navigateTo(XrayFragment())
        }
    }

    private fun navigateTo(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
