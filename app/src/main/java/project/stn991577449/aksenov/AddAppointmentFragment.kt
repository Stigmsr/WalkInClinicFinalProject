//Modified by Aksenov Vladsilav 991577449

package project.stn991577449.aksenov

import android.content.ContentValues.TAG
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.FirebaseFirestore
import project.stn991577449.aksenov.data.Appointment
import project.stn991577449.aksenov.databinding.FragmentAddAppointmentBinding

class AddAppointmentFragment : Fragment() {
   //private val navigationArgs: AppointmentDetailFragmentArgs by navArgs()
    private var _binding: FragmentAddAppointmentBinding? = null
    private val binding get() = _binding!!
    val fireStoreDatabase = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isEntryValid() : Boolean
    {
        val date = binding.appointmentDate.text.toString()
        val hour = binding.appointmentHour.text.toString()
        val doctor = binding.appointmentDoctor.text.toString()
        return !(date.isBlank() || hour.isBlank() || doctor.isBlank())
    }
    private fun addNewAppointment() {
        if (isEntryValid()) {
            val date = binding.appointmentDate.text.toString()
            val hour = binding.appointmentHour.text.toString()
            val doctor = binding.appointmentDoctor.text.toString()
            val appointment : MutableMap<String, Any> = HashMap()
            appointment["date"] = date
            appointment["hour"] = hour
            appointment["doctor"] = doctor
            fireStoreDatabase.collection("appointments")
                .add(appointment)
                .addOnSuccessListener {
                    Log.d("Firestore", "Added document with ID ${it.id}")
                }
                .addOnFailureListener {
                    Log.w("Firestore", "Error adding document $it")
                }

        }
        val action = AddAppointmentFragmentDirections.actionAddAppointmentFragmentToAppointmentListFragment()
        findNavController().navigate(action)
    }

    private fun deleteAppointment() {
        if (isEntryValid()) {
            val date = binding.appointmentDate.text.toString()
            val hour = binding.appointmentHour.text.toString()
            val doctor = binding.appointmentDoctor.text.toString()
            val appointment : MutableMap<String, Any> = HashMap()
            appointment["date"] = date
            appointment["hour"] = hour
            appointment["doctor"] = doctor
            fireStoreDatabase.collection("appointments")
                .get()
                .addOnSuccessListener { result ->
                    for (tmp in result) {
                        if (tmp.data.getValue("date").equals(date)
                            && tmp.data.getValue("hour").equals(hour)
                               && tmp.data.getValue("doctor").equals(doctor)){
                            fireStoreDatabase.collection("appointments")
                                .document(tmp.id)
                                .delete()
                                .addOnSuccessListener {
                                    Log.d(TAG, "Appointment Deleted")
                                }
                                .addOnFailureListener {
                                    Log.w(TAG, "Error Occured")
                                }
                        } else {
                            Log.w(TAG, "Appointment not found")
                        }
                    }
                }
        }

        val action = AddAppointmentFragmentDirections.actionAddAppointmentFragmentToAppointmentListFragment()
        findNavController().navigate(action)
    }
    /*private fun bind(appointment: Appointment) {

        binding.apply {
            appointmentDate.setText(appointment.appointmentDate, TextView.BufferType.SPANNABLE)
            appointmentHour.setText(appointment.appointmentHour, TextView.BufferType.SPANNABLE)
            appointmentDoctor.setText(appointment.doctor, TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateAppointment() }
        }
    }

    private fun updateAppointment() {
        if (isEntryValid()) {
            viewModel.updateAppointment(
                this.navigationArgs.appointmentId,
                this.binding.appointmentDate.text.toString(),
                this.binding.appointmentHour.text.toString(),
                this.binding.appointmentDoctor.text.toString()
            )
            val action = AddAppointmentFragmentDirections.actionAddAppointmentFragmentToAppointmentListFragment()
            findNavController().navigate(action)
        }
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveAction.setOnClickListener {
            addNewAppointment()
        }
        binding.deleteAction.setOnClickListener {
            deleteAppointment()
        }
        /*val id = navigationArgs.appointmentId

        if (id > 0) {
            viewModel.retrieveAppointment(id).observe(this.viewLifecycleOwner) { selectedAppointment ->
                appointment = selectedAppointment
                bind(appointment)
            }
        } else {
            binding.saveAction.setOnClickListener {
                addNewAppointment()
            }
        }*/
    }


    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}
