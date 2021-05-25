package com.example.weather_app.ui.login_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.example.weather_app.util.Util
import com.example.weather_app.util.showToast
import com.example.weatherapp.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment: Fragment() {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for requireActivity() fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(requireActivity(), R.id.nav_fragment)

        initializeTheUI()
        clickListener()
    }

    private fun initializeTheUI() {

        cl_main_layout.background = Util.getGradientColor(requireActivity(), R.color.blue, R.color.lightBlue)
    }

    private fun clickListener() {
        btn_login.setOnClickListener {
            if(et_email.text.toString().toLowerCase() == "kunal"&&et_password.text.toString() == "12345") {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.loginFragment, true)
                    .build()
                navController.navigate(
                    R.id.action_loginFragment_to_weatherFragment,
                    null,
                    navOptions
                )
            }
            else
            {
                requireActivity().showToast("Email = kunal  password = 12345")
            }
        }
    }
}