package project.stn991577449.aksenov

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import project.stn991577449.aksenov.data.Appointment
import project.stn991577449.aksenov.databinding.AppointmentListFragmentBinding


class AppointmentListFragment : Fragment() {
    private var _binding: AppointmentListFragmentBinding? = null
    private val binding get() = _binding!!
    private val base = mutableListOf<Appointment>()
    private val firebaseAuth = FirebaseAuth.getInstance()
    val fireStoreDatabase = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AppointmentListFragmentBinding.inflate(inflater, container, false)
        fireStoreDatabase.collection("appointments")
            .get()
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    var cnt = 0
                    for(document in it.result!!) {
                        base.add(Appointment(cnt, document.data.getValue("date").toString()
                            , document.data.getValue("hour").toString()
                            , document.data.getValue("doctor").toString()))
                        binding.recyclerView.adapter?.notifyDataSetChanged()
                        cnt += 1
                    }
                }
            }
        if (firebaseAuth.currentUser?.email.toString().equals("vlador3et@gmail.com")) {
            binding.floatingActionButton.show()
        } else {
            binding.floatingActionButton.hide()
        }
        binding.recyclerView.adapter = AppointmentListAdapter(base)
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener {
            val action = AppointmentListFragmentDirections.actionAppointmentListFragmentToAddAppointmentFragment()
            this.findNavController().navigate(action)
        }
        binding.helpBtn.setOnClickListener {
            val action = AppointmentListFragmentDirections.actionAppointmentListFragmentToHelpFragment()
            this.findNavController().navigate(action)
        }
    }
    private fun getUpdate(): List<Appointment> {
        val list = ArrayList<Appointment>()
        fireStoreDatabase.collection("appointments")
            .get()
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    var cnt = 0
                    for(document in it.result!!) {
                        list += Appointment(cnt, document.data.getValue("date").toString()
                            , document.data.getValue("hour").toString()
                            , document.data.getValue("doctor").toString())
                        cnt += 1
                    }
                }
            }
        return list
    }
}