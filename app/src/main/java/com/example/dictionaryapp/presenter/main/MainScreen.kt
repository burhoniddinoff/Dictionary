package com.example.dictionaryapp.presenter.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.ScreenMainBinding
import com.example.dictionaryapp.presenter.adapter.MainPagerAdapter
import com.example.dictionaryapp.presenter.adapter.ViewPagerAdapter
import com.example.dictionaryapp.presenter.adapter.WordAdapter
import com.example.dictionaryapp.presenter.home.HomeScreen
import com.example.dictionaryapp.presenter.saved.SavedScreen
import com.example.dictionaryapp.utils.popBackStack
import com.example.dictionaryapp.utils.replaceScreen

class MainScreen : Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private var adapter: ViewPagerAdapter? = null
    private lateinit var adapterWORD: WordAdapter
    private val firstPage by lazy { HomeScreen() }
    private val secondPage by lazy { HomeScreen() }
    private var adapterBN: MainPagerAdapter? = null
    private val REQ_CODE_SPEECH_INPUT = 100
    private var tts: TextToSpeech? = null

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapterBN = MainPagerAdapter(this, this.firstPage, this.secondPage)
        adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        adapterWORD = WordAdapter(requireContext())


        // TAB LAYOUT >
////    private val item = arrayListOf("Dictionary", "Speak")

//        binding.viewPager.adapter = adapter
//        binding.viewPager.isUserInputEnabled = false
//
//        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
//            tab.text = item[pos]
//        }.attach()


        // BOTTOM NAVIGATION

        binding.viewPager.adapter = adapterBN
        binding.viewPager.isUserInputEnabled = false

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigationView.menu[position].isChecked = true
            }
        })

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottomFirst -> binding.viewPager.currentItem = 0
            }
            return@setOnItemSelectedListener true
        }


        // DRAWER LAYOUT

        drawerLayout = binding.drawerLayout


        binding.btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
            hideKeyboardFrom(requireContext(), view)
        }

        val navigationView = binding.navView
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_menu -> {
                    drawerLayout.close()
                    true
                }


                R.id.nav_favorite -> {
                    replaceScreen(SavedScreen())
                    drawerLayout.close()
                    true
                }

                R.id.nav_share -> {
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=${requireContext().packageName}")
                            )
                        )
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=${requireContext().packageName}")
                            )
                        )
                    }
                    true
                }

                R.id.nav_rate -> {
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=${requireContext().packageName}")
                            )
                        )
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=${requireContext().packageName}")
                            )
                        )
                    }
                    true
                }

                else -> false
            }
        }


        // speak


        binding.linear2.setOnClickListener {
            promptSpeechInput()
        }


        // transfer

//        binding.linear3.setOnClickListener {
//            if (adapterWORD.isEnglish) {
////                adapterWORD.isEnglish = false
//                adapterWORD.setLanguages(false)
//            } else {
////                adapterWORD.isEnglish = true
////                binding.searchView.queryHint = "Qidiring"
//                adapterWORD.setLanguages(true)
//            }
//            Log.d("TTT", "linear 3 bosildii ${adapterWORD.isEnglish}")
//            adapterWORD.notifyDataSetChanged()
//        }
    }

    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.menu_home))

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                "Sorry! Your device doesn\\'t support speech input",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val message = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                updateResults(message!![0].lowercase())
            }
        }
    }

    private fun hideKeyboardFrom(context: Context, view: View?) {
        val imm =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun updateResults(s: String) {
        var str = s
        if (s.contains(' ')) {
            str = s.split(" ")[0]
        }
        firstPage.searchText(str)
    }

    override fun onPause() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onPause()
    }
}