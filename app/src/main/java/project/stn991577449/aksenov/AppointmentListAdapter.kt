package project.stn991577449.aksenov

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.stn991577449.aksenov.data.Appointment
import project.stn991577449.aksenov.databinding.AppointmentListAppointmentBinding

class AppointmentListAdapter(private val sampleList: List <Appointment>) :
    RecyclerView.Adapter<AppointmentListAdapter.AppointmentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        return AppointmentViewHolder(
            AppointmentListAppointmentBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: AppointmentListAdapter.AppointmentViewHolder, position: Int) {
        with(holder) {
            with(sampleList[position]) {
                binding.appointmentDate.text = appointmentDate
                binding.appointmentHour.text = appointmentHour
                binding.appointmentDoctor.text = doctor
            }
        }
    }
    override fun getItemCount() = sampleList.size

    inner class AppointmentViewHolder(val binding: AppointmentListAppointmentBinding)
        :RecyclerView.ViewHolder(binding.root)
}