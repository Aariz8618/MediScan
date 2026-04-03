package com.aariz.mediscan;
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnEdit)?.setOnClickListener {
            Toast.makeText(requireContext(), "Edit profile coming soon", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<LinearLayout>(R.id.rowMedHistory)?.setOnClickListener {
            Toast.makeText(requireContext(), "Medical History", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<LinearLayout>(R.id.rowMedications)?.setOnClickListener {
            Toast.makeText(requireContext(), "Current Medications", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<LinearLayout>(R.id.rowShare)?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ShareFragment())
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<LinearLayout>(R.id.rowNotifications)?.setOnClickListener {
            // Toggle handled by the Switch widget itself
        }

        view.findViewById<LinearLayout>(R.id.rowPrivacy)?.setOnClickListener {
            Toast.makeText(requireContext(), "Privacy & Security", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<LinearLayout>(R.id.rowHelp)?.setOnClickListener {
            Toast.makeText(requireContext(), "Help & Support", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<LinearLayout>(R.id.rowSignOut)?.setOnClickListener {
            Toast.makeText(requireContext(), "Signed out", Toast.LENGTH_SHORT).show()
            requireActivity().finish()
        }
    }
}
