package com.lupa.suitgame2.ui.slider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.lupa.suitgame2.databinding.FragmentSliderBinding
import com.lupa.suitgame2.model.SliderData

class SliderFragment : Fragment() {

    private lateinit var binding: FragmentSliderBinding

    private var sliderData: SliderData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSliderBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindSliderData()
    }

    private fun bindSliderData() {
        with(binding) {
            ivIntro.load(sliderData?.imgSlider) {
                crossfade(true)
            }
            tvIntro.text = sliderData?.description.orEmpty()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sliderData = it.getParcelable(ARG_SLIDER_DATA)
        }
    }

    companion object {

        private const val ARG_SLIDER_DATA = "ARG_SLIDER_DATA"

        @JvmStatic
        fun newInstance(sliderData: SliderData) =
            SliderFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SLIDER_DATA, sliderData)
                }
            }
    }
}