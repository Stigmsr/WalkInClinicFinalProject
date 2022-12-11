package project.stn991577449.aksenov

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import project.stn991577449.aksenov.databinding.FragmentHelpBinding

class HelpFragment: Fragment() {
    private var _binding: FragmentHelpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        binding.backBtn.setOnClickListener {
            val action = HelpFragmentDirections.actionHelpFragmentToAppointmentListFragment()
            this.findNavController().navigate(action)
        }
        return binding.root
    }
}