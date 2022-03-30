package com.project.ayu

import android.content.ClipData.Item
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewTreeLifecycleOwner.get
import androidx.recyclerview.widget.RecyclerView
import com.project.ayu.databinding.DoctorListItemsBinding
import com.project.ayu.databinding.PastAppointmentListItemBinding
import java.lang.reflect.Array.get
import java.nio.file.Paths.get
import kotlin.contracts.contract
import kotlin.coroutines.coroutineContext


class PastAppointmentAdapter(val LinkList: ArrayList<PastAppointmentsDataFile>) :
    RecyclerView.Adapter<PastAppointmentAdapter.MyViewHolder>() { //class  which will take prameter(list of strings)
// ------------------------------------------------------------------------------------------------------------------------------------------


    private lateinit var binding: PastAppointmentListItemBinding

    //-------------------------------------------------------------------------------------------------------------------------------------------------

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {  //function take parameter()


        val binding =
            PastAppointmentListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return MyViewHolder(binding) //returning MyViewHolder class with a view inside it
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val Link2 = LinkList[position]
        holder.friction(Link2, position, LinkList)
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    override fun getItemCount(): Int {  // this function is counting the size of list
        return LinkList.size // returning the size of list
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    class MyViewHolder(ItemViewBinding: PastAppointmentListItemBinding) :
        RecyclerView.ViewHolder(ItemViewBinding.root) {


        private val binding = ItemViewBinding
        fun friction(
            Link: PastAppointmentsDataFile,
            position: Int,
            list: ArrayList<PastAppointmentsDataFile>
        ) {
            val context = itemView.getContext();
            binding.DoctorImage.setImageResource(list[position].DoctorImage)
            binding.doctorDate.text = Link.getDate()
            binding.doctorNumber.text = Link.getNumber().toString()
            binding.TimeButton1.text = Link.getTime().toString()
        }
    }


//--------------------------------------------------------------------------------------------------------------------------------------------


//--------------------------------------------------------------------------------------------------------------------------------------------

    interface OnItemClickListener {
        fun onItemClicked(Link: DoctorDataFile)
    }

}