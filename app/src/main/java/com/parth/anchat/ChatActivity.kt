package com.parth.anchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {

    private lateinit var messaRecyler: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var Sendbtn : ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList : ArrayList<message>
    private lateinit var mDbref : DatabaseReference


    var reciverRoom : String? = null
    var senderRoom : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        val name = intent.getStringExtra("name")
        val rid = intent.getStringExtra("uid")


        val Senderid = FirebaseAuth.getInstance().currentUser?.uid
        mDbref = FirebaseDatabase.getInstance().getReference()

        senderRoom = rid + Senderid

        reciverRoom = Senderid + rid


        supportActionBar?.title = name




        messaRecyler = findViewById(R.id.chatrec)

        messageBox  =  findViewById(R.id.msbox)

        Sendbtn = findViewById(R.id.senimg)

        messageList = ArrayList()

        messageAdapter = MessageAdapter(this,messageList)

        messaRecyler.layoutManager = LinearLayoutManager(this)

        messaRecyler.adapter = messageAdapter

        // logic to add data to recylerview item


        mDbref.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for(postSnapshot in snapshot.children)
                    {
                        val message = postSnapshot.getValue(message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }


            })


        // adding the message in db:
        Sendbtn.setOnClickListener{
                val message = messageBox.text.toString()
                val messageObject = com.parth.anchat.message(message,Senderid)


            mDbref.child("chats").child(senderRoom!!).child("messages").push()

                .setValue(messageObject).addOnSuccessListener {
                    mDbref.child("chats").child(reciverRoom!!).child("messages").push()

                        .setValue(messageObject)
                }
            messageBox.setText("")


        }
    }
}