package com.project.ayu;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.ayu.databinding.MypatientListItemBinding
import com.project.ayu.databinding.PastAppointmentListItemBinding

class MyPatientAdapter(val LinkList: ArrayList<MyPatientDataFile>) :
    RecyclerView.Adapter<MyPatientAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {  //function take parameter()


        val binding =
            MypatientListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding) //returning MyViewHolder class with a view inside it
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val Link3 = LinkList[position]
        holder.friction(Link3, position, LinkList)
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    override fun getItemCount(): Int {  // this function is counting the size of list
        return LinkList.size // returning the size of list
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    class MyViewHolder(ItemViewBinding: MypatientListItemBinding) :
        RecyclerView.ViewHolder(ItemViewBinding.root) {


        private val binding = ItemViewBinding
        fun friction(Link: MyPatientDataFile, position: Int, list: ArrayList<MyPatientDataFile>) {
            val context = itemView.getContext();
            binding.DoctorNumber.text = Link.getDoctor().toString()
            binding.PatientNumber.text = Link.getPatient().toString()
            binding.AppointmentDate.text = Link.getDate().toString()
            binding.TimeButton1.text = Link.getTime().toString()
        }
    }


//--------------------------------------------------------------------------------------------------------------------------------------------


//--------------------------------------------------------------------------------------------------------------------------------------------

    interface OnItemClickListener {
        fun onItemClicked(Link: DoctorDataFile)
    }
}
