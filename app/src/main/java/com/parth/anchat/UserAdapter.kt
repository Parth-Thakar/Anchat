package com.parth.anchat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class UserAdapter(val context: Context, val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.userlayout, parent,  false)

        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val curentUser = userList[position]

        holder.textname.text = curentUser.name

        holder.itemView.setOnClickListener{
            val intent = Intent(context,ChatActivity::class.java)

            intent.putExtra("name",curentUser.name)
            intent.putExtra("uid",curentUser.uid)

            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class  UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val textname = itemView.findViewById<TextView>(R.id.name)

    }
}