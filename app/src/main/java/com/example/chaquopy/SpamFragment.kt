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
import com.example.chaquopy.databinding.FragmentSpamBinding

class SpamFragment : Fragment() {
    var data = ArrayList<Message>()
    lateinit var adapter: MessageAdapter
    private var _binding: FragmentSpamBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSpamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // this creates a vertical layout Manager
        binding.recyclerview.layoutManager =
            LinearLayoutManager(this.requireActivity().applicationContext)

        // ArrayList of class ItemsViewModel
        val db = DBHelper(this.requireActivity().applicationContext, null)
        data = db.getData(DBHelper.TABLE_SPAM)

        // This will pass the ArrayList to our Adapter
        adapter = MessageAdapter(data)
        binding.recyclerview.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}