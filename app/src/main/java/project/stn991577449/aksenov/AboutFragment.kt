package project.stn991577449.aksenov

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import project.stn991577449.aksenov.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        binding.backBtn.setOnClickListener {
            val action = AboutFragmentDirections.actionAboutFragmentToLoginFragment()
            this.findNavController().navigate(action)
        }
        return binding.root
    }
}