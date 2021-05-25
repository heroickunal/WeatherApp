package com.example.weather_app.ui.weather_screen

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.example.weather_app.model.WeatherResponse
import com.example.weather_app.ui.weather_screen.adapter.WeatherSliderAdapter
import com.example.weather_app.util.ResponseHandler
import com.example.weather_app.util.showToast
import com.example.weatherapp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_weather.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class WeatherFragment : Fragment() {

    private val viewModel by viewModel<WeatherViewModel>()
    val weatherInfoList = mutableListOf<WeatherResponse>()
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for requireActivity() fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("onViewCreated")

        initializeTheView()
        viewModelWorks()
        clickListener()
    }

    private fun initializeTheView() {
        //set Default city
        viewModel.getWeatherByCity("California")
        navController = Navigation.findNavController(requireActivity(), R.id.nav_fragment)
    }

    private fun clickListener() {
        btn_add_city.setOnClickListener {
            showFindByCityNameDialog()
        }

        btn_logout.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.weatherFragment, true)
                .build()
            navController.navigate(R.id.action_weatherFragment_to_loginFragment, null, navOptions)
        }
    }

    private fun viewModelWorks() {
        viewModel.getWeatherByCityResponse.observe(requireActivity(), { response ->
            when (response) {
                is ResponseHandler.Success -> {
                    response.data?.let { weatherResponse ->
                        Timber.d("weatherResponse ${weatherResponse}")

                        weatherInfoList.add(weatherResponse)
                        weatherInfoList.reverse()

                        val adapter = WeatherSliderAdapter(requireActivity(), weatherInfoList)
                        sliderView.setSliderAdapter(adapter)

                        progress_circular.visibility = View.GONE
                    }
                }
                is ResponseHandler.Loading -> {
                    Timber.d("Loading ")
                    progress_circular.visibility = View.VISIBLE
                }
                is ResponseHandler.Failure -> {
                    Timber.d("response ${response}")
                    progress_circular.visibility = View.GONE
                    if (response.errorMessage == "404") {
                        requireActivity().showToast("City not found!")
                    } else {
                        requireActivity().showToast("Failure ${response.errorMessage}")
                    }
                }
            }
        })
    }

    private fun showFindByCityNameDialog() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_weather)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        val cityName = dialog.findViewById(R.id.et_city_name) as TextInputEditText
        val btnFind = dialog.findViewById(R.id.btn_find) as MaterialButton

        btnFind.setOnClickListener {
            viewModel.getWeatherByCity(cityName.text.toString())
            dialog.dismiss()
        }

        requireActivity().resources?.let {
            val displayMetrics = it.displayMetrics
            val dialogWidth = (displayMetrics.widthPixels * 0.85).toInt()
            val dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(dialogWidth, dialogHeight)
            dialog.show()
        }
    }

}