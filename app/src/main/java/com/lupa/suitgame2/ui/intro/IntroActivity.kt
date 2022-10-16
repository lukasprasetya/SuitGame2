package com.lupa.suitgame2.ui.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isInvisible
import androidx.viewpager2.widget.ViewPager2
import com.lupa.suitgame2.databinding.ActivityIntroBinding
import com.lupa.suitgame2.model.SliderData
import com.lupa.suitgame2.ui.form.FormFragment
import com.lupa.suitgame2.ui.form.OnNameSubmitedListener
import com.lupa.suitgame2.ui.slider.SliderFragment
import com.lupa.suitgame2.utils.ViewPagerAdapter
import com.lupa.suitgame2.utils.getNextIndex
import com.lupa.suitgame2.utils.getPreviousIndex

class IntroActivity : AppCompatActivity(), OnNameSubmitedListener {

    private val binding: ActivityIntroBinding by lazy {
        ActivityIntroBinding.inflate(layoutInflater)
    }

    private val pageAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(supportFragmentManager, lifecycle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        initFragmentViewPager()
        setOnClickListeners()
    }

    private fun initAdapter() {
        pageAdapter.apply {
            addFragment(
                SliderFragment.newInstance(
                    SliderData(
                        description = "Play suit against fellow cats.",
                        imgSlider = "https://lilgoods.com/ic_cat_vs_cat_700dp.png"
                    )
                )
            )
            addFragment(
                SliderFragment.newInstance(
                    SliderData(
                        description = "Play suit against robot cat.",
                        imgSlider = "https://lilgoods.com/ic_cat_vs_com_700dp.png"
                    )
                )
            )
            addFragment(FormFragment.newInstance().apply {
                setNameSubmitedListener(this@IntroActivity)
            })
        }
    }

    private fun setupViewPager() {
        binding.vpIntro.apply {
            adapter = pageAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when {
                        position == 0 -> {
                            binding.tvNext.isInvisible = false
                            binding.tvNext.isEnabled = true
                            binding.tvPrevious.isInvisible = true
                            binding.tvPrevious.isEnabled = false
                        }
                        position < pageAdapter.getMaxIndex() -> {
                            binding.tvNext.isInvisible = false
                            binding.tvNext.isEnabled = true
                            binding.tvPrevious.isInvisible = false
                            binding.tvPrevious.isEnabled = true
                        }
                        position == pageAdapter.getMaxIndex() -> {
                            binding.tvNext.isInvisible = true
                            binding.tvNext.isEnabled = false
                            binding.tvPrevious.isInvisible = false
                            binding.tvPrevious.isEnabled = true
                        }
                    }
                }
            })
        }
        binding.dotsIndicator.attachTo(binding.vpIntro)
    }

    private fun setOnClickListeners() {
        binding.tvNext.setOnClickListener {
            navigateToNextFragment()
        }
        binding.tvPrevious.setOnClickListener {
            navigateToPreviousFragment()
        }
    }

    private fun navigateToPreviousFragment() {
        val nextIndex = binding.vpIntro.getPreviousIndex()
        if (nextIndex != -1) {
            binding.vpIntro.setCurrentItem(nextIndex, true)
        }
    }

    private fun navigateToNextFragment() {
        val nextIndex = binding.vpIntro.getNextIndex()
        if (nextIndex != -1) {
            binding.vpIntro.setCurrentItem(nextIndex, true)
        }
    }

    private fun initFragmentViewPager() {
        initAdapter()
        setupViewPager()
    }

    override fun onNameSubmitedListener(name: String) {
        Log.d(IntroActivity::class.java.simpleName, "onNameSubmitedListener: $name ")
    }
}