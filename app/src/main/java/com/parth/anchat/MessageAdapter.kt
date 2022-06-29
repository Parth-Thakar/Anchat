package com.parth.anchat

import android.content.Context
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MessageAdapter(val context: Context,  val messageList: ArrayList<message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val itemrec = 1
    val itemsen = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1)
        {

            //inflate recieve
            val view: View = LayoutInflater.from(context).inflate(R.layout.recieved, parent,  false)

            return ReciveViewHolder(view)
        }
        else
        {
            // inflate sent
            val view: View = LayoutInflater.from(context).inflate(R.layout.send, parent,  false)

            return SentViewHolder(view)

        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentmess = messageList[position]
        if(holder.javaClass == SentViewHolder::class.java)
        {
            ///  do the stuff for sent view holder





            val viewHolder = holder as SentViewHolder
            holder.sent_mess.text =  currentmess.message


        }
        else
        {
            //do stuff for recive view holder
            val viewHolder= holder as ReciveViewHolder

            holder.rec_mess.text = currentmess.message


        }

    }

    override fun getItemViewType(position: Int): Int {
        val currentmess = messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentmess.senderid))
        {
            return itemsen
        }
        else
        {
            return itemrec
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }



    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val sent_mess = itemView.findViewById<TextView>(R.id.text_sent_message)
    }


    class ReciveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val rec_mess = itemView.findViewById<TextView>(R.id.recive_message_txt)

    }
}