package com.decagon.android.sq007


import android.app.AlertDialog
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.contactrecyclerview.ContactAdapter
import com.decagon.android.sq007.model.ContactData


class ContactFragment : Fragment() {

    lateinit var ContactRecyclerView : RecyclerView

    companion object {
        private val TAG = "Permission"
        const val readContacts_requestCode= 17
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contact, container, false)

        ContactRecyclerView = view.findViewById(R.id.contact_recyclerView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ContactRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        setUpPermissions(android.Manifest.permission.READ_CONTACTS, "contacts", readContacts_requestCode)

        val contactList: MutableList<ContactData> = ArrayList()
        val contacts = contentReadContact()

        while (contacts?.moveToNext()!!) {
            val name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val mobileNumber = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

            val contactObject = ContactData()
            contactObject.nameData = name
            contactObject.phoneNumberData = mobileNumber

            contactList.add(contactObject)
        }

        ContactRecyclerView.adapter = ContactAdapter(contactList, requireContext())

    }

    private fun setUpPermissions(permission: String, name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(requireContext(), "$name Permission Granted", Toast.LENGTH_LONG).show()
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name, requestCode)

                else -> requireActivity().requestPermissions(arrayOf(permission), requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "$name permission not granted", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(requireContext(), "$name permission granted", Toast.LENGTH_LONG).show()
            }
        }

        when (requestCode) {
            readContacts_requestCode -> innerCheck("contacts")
        }
    }

    fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(requireContext())

        builder.apply {
            setTitle("Permission required")
            setMessage("Permission to access your $name is required to use this app")
            setPositiveButton("Allow") { dialog, which ->
                requireActivity().requestPermissions(arrayOf(permission), requestCode)
            }
        }
        builder.create().show()
    }

    private fun contentReadContact (): Cursor? {
        return activity?.contentResolver?.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)
    }


}