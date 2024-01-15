package com.example.chaquopy

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chaquopy.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    var data = ArrayList<Message>()
    lateinit var adapter: MessageAdapter
    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // this creates a vertical layout Manager
        binding.recyclerview.layoutManager = LinearLayoutManager(this.requireActivity().applicationContext)

        // ArrayList of class ItemsViewModel
        val db = DBHelper(this.requireActivity().applicationContext, null)
        data = db.getData(DBHelper.TABLE_HOME)

        // This will pass the ArrayList to our Adapter
        adapter = MessageAdapter(data)
        binding.recyclerview.adapter = adapter

        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
//                Toast.makeText(this.requireActivity().applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this.requireActivity().applicationContext, "Permission Not Granted", Toast.LENGTH_SHORT).show()
            }
        }

        permissionLauncher.launch(android.Manifest.permission.RECEIVE_SMS)


        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this.requireActivity().applicationContext, android.Manifest.permission.RECEIVE_SMS)) {
            Log.d("Mantap", "Woke")
        }
        else {
            Toast.makeText(this.requireActivity().applicationContext, "Tolong ijinkan aplikasi menerima SMS", Toast.LENGTH_LONG).show()
        }

        permissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this.requireActivity().applicationContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Log.d("Mantap", "Woke")
        }
        else {
            Toast.makeText(this.requireActivity().applicationContext, "Tolong ijinkan aplikasi mengakses penyimpanan", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}