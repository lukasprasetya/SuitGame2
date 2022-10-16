package com.lupa.suitgame2.ui.form

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lupa.suitgame2.databinding.FragmentFormBinding
import com.lupa.suitgame2.ui.main.MenuActivity

class FormFragment : Fragment() {

    private lateinit var binding: FragmentFormBinding

    private var listener: OnNameSubmitedListener? = null

    fun setNameSubmitedListener(listener: OnNameSubmitedListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnSetName.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            if (name.isEmpty()) {
                listener?.onNameSubmitedListener(name)
                Toast.makeText(requireContext(), "Please input your name!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                navigateToMenu(name)
            }
        }
    }

    private fun navigateToMenu(name: String) {
        val intent = Intent(activity, MenuActivity::class.java)
        intent.putExtra("name", name)
        startActivity(intent)
    }
    companion object {
        @JvmStatic
        fun newInstance() = FormFragment()
    }
}

interface OnNameSubmitedListener {
    fun onNameSubmitedListener(name: String)
}