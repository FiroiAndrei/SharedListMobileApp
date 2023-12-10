package com.example.sharetasks.data.network.firebase

import android.adservices.topics.Topic
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.sharetasks.data.network.Group
import com.example.sharetasks.data.network.Groups
import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import org.json.JSONObject

class FirebaseApi() {
    private val db = Firebase.firestore

    private var _results = MutableStateFlow(Groups())

    val results = _results.asStateFlow()

    private val TAG = "Firebase"

    init {
        Log.d(TAG, "Created Firebase: ")
    }


    fun createGroup(name: String) {
        val group = hashMapOf(
            "name" to name,
            "items" to listOf<String>()
        )

        db.collection("groups")
            .add(group)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Document Added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document: ", e)
            }
    }

    fun deleteItem(group: Group, item: String) {
        var newList = mutableListOf<String>()
        var skip = false
        for (itemInGroup in group.items?: listOf()) {
            if (skip == true) {
                newList.add(itemInGroup)
            } else {
                if (itemInGroup.compareTo(item) == 0) {
                    skip = true
                } else {
                    newList.add(itemInGroup)
                }
            }
        }

        db.collection("groups").document(group.id!!)
            .update("items", newList)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }


    fun addItems(group: Group, items: List<String>) {
        var newList = mutableListOf<String>()
        for (item in group.items?: listOf()) {
            newList.add(item)
        }
        for (item in items)
            newList.add(item)
        db.collection("groups").document(group.id!!)
            .update("items", newList)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }

    suspend fun getGroups() {

        Log.d(TAG, "getGroups: GOT CALLED ${results.value}")

        var groupList = mutableListOf<Group>()

        db.collection("groups")
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    var group = Group()
                    group.id = document.id
                    document.data.forEach() { it ->
                        when (it.key) {
                            "name" -> group.name = it.value.toString()
                            "items" -> group.items = it.value as List<String>?
                        }
                    }

                    groupList.add(group)

                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    _results.update { it ->
                      it.copy(
                          groups = groupList
                      )
                    }
                }
            }
            .await()


    }
}














//
//
//
//    private val database = Firebase.database
//
//    private val myRef = database.getReference()
//
//
//    fun getGroups(): List<Group>? {
//        return results.groups
//    }
//
//    fun createGroup(name: String) {
//
//
//        val key = myRef.child("groups").push().key
//        val newGroup = Group(id = key, items = listOf("Milk", "Sugar"), name = name)
//        myRef.child("groups").child(key!!).setValue(newGroup)
//    }
//
//    fun addItems(groupKey: String, items: List<String>) {
//        items.forEach() {
//            myRef.child("groups").child(groupKey).child("items").push().setValue(it)
//        }
//    }
//
//
//    fun getDataFromFirebase() {
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
////                val value = dataSnapshot.children.forEach { dataSnapshot ->
////                    Log.d(TAG, "Inside: ${dataSnapshot.value}")
////                }
//
//                val value = dataSnapshot.value
//
//                Log.d(TAG, "onDataChange: $value")
//
////                val group = value?.values?.last() ?: Groups(listOf())
//
//                if (value != null) {
//                    results = Groups(listOf())
//                } else {
//                    results = Groups(listOf())
//                }
//
//            }
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//        })
//    }
