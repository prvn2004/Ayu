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
import java.lang.reflect.Array.get
import java.nio.file.Paths.get
import kotlin.contracts.contract
import kotlin.coroutines.coroutineContext


class MyAdapter(
    val LinkList: ArrayList<DoctorDataFile>
) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() { //class  which will take prameter(list of strings)
// ------------------------------------------------------------------------------------------------------------------------------------------

    private lateinit var binding: DoctorListItemsBinding

    //-------------------------------------------------------------------------------------------------------------------------------------------------

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {  //function take parameter()


        binding =
            DoctorListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(
            binding
        ) //returning MyViewHolder class with a view inside it
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val Link = LinkList[position]
        holder.friction(Link, position, LinkList)
        binding.DoctorBookButton.setOnClickListener {
            val intent = Intent(it.context, Patient_viewing_doctor_profile::class.java)
            intent.putExtra("DoctorName", LinkList.get(position).getName().toString())
            intent.putExtra("DoctorExperience", LinkList.get(position).getExperience().toString())
            intent.putExtra("DoctorExpertise", LinkList.get(position).getExpertise().toString())
            intent.putExtra("DoctorNumber", LinkList.get(position).getNumber().toString())
            intent.putExtra("DoctorReg", LinkList.get(position).getRegNo().toString())
            it.context.startActivity(intent)

        }

//                setOnClickListener { listener(Link) }
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    override fun getItemCount(): Int {  // this function is counting the size of list
        return LinkList.size // returning the size of list
    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    class MyViewHolder(
        ItemViewBinding: DoctorListItemsBinding,
    ) :
        RecyclerView.ViewHolder(ItemViewBinding.root) {

        private val binding = ItemViewBinding
        fun friction(Link: DoctorDataFile, position: Int, list: ArrayList<DoctorDataFile>) {
            val context = itemView.getContext();
            binding.DoctorImage.setImageResource(list[position].DoctorImage)
            binding.DoctorName.text = Link.getName().toString()
            binding.doctorNumber.text = Link.getNumber().toString()
            binding.doctorExpertiseText2.text = Link.getExpertise().toString()
            binding.doctorExperienceYear.text = Link.getExperience().toString()
            binding.doctorRegNo.text = Link.DoctorRegNo.toString()


        }

    }

//--------------------------------------------------------------------------------------------------------------------------------------------


//--------------------------------------------------------------------------------------------------------------------------------------------

    interface OnItemClickListener {
        fun onItemClicked(Link: DoctorDataFile)
    }


}