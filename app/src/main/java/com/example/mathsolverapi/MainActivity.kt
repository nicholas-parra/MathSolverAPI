package com.example.mathsolverapi

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mathsolverapi.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding : ActivityMainBinding
    var operator = "Simplify"
    companion object {
        val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.groupMainInstructionsUI.visibility = View.GONE

        val categoryDropdown : Spinner = findViewById(R.id.spinner_main_operations)
        ArrayAdapter.createFromResource(this, R.array.operators_array,
            android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categoryDropdown.adapter = adapter
        }
        categoryDropdown.setSelection(0, false)
        categoryDropdown.onItemSelectedListener = this

        binding.textViewMainHelp.text = resources.getString(R.string.instructions)

        binding.buttonMainHelp.setOnClickListener {
            binding.groupMainMainUI.visibility = View.GONE
            binding.groupMainInstructionsUI.visibility = View.VISIBLE
        }

        binding.buttonMainReturn.setOnClickListener {
            binding.groupMainMainUI.visibility = View.VISIBLE
            binding.groupMainInstructionsUI.visibility = View.GONE
        }

        binding.buttonMainEnter.setOnClickListener {
            val expression = prepareExpression(binding.editTextMainExpression.text.toString())
            val newtonCall = chooseCall(expression) ?: return@setOnClickListener
            newtonCall.enqueue(object : Callback<JsonInfo> {
                override fun onResponse(
                    call: Call<JsonInfo>,
                    response: Response<JsonInfo>
                ) {
                    if (response.body()?.error ?: "" == "Unable to perform calculation") {
                        Toast.makeText(this@MainActivity, "There was an error with the inputted expression. Please fix it and try again.", Toast.LENGTH_LONG).show()
                        return
                    }
                    var result = response.body()?.result.toString()
                    if (operator == "Integrate" && !result.contains("+ C")) {
                        result += " + C"
                    }

                    binding.textViewMainResult.text = result
                }

                override fun onFailure(call: Call<JsonInfo>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
        }
    }

    fun chooseCall(expression : String) : Call<JsonInfo>? {
        val newtonApi = RetrofitHelper.getInstance().create(NewtonService::class.java)
        return when (operator) {
            "Simplify" -> newtonApi.simplify(expression)
            "Factor" -> newtonApi.factor(expression)
            "Derive" -> newtonApi.derive(expression)
            "Integrate" -> newtonApi.integrate(expression)
            "Find zeroes" -> newtonApi.zeroes(expression)
            "Find tangent line" -> newtonApi.tangentLine(expression)
            "Area under curve" -> newtonApi.area(expression)
            "Cosine" -> newtonApi.cos(expression)
            "Sine" -> newtonApi.sin(expression)
            "Tangent" -> newtonApi.tan(expression)
            "Inverse Cosine" -> newtonApi.arccos(expression)
            "Inverse Sine" -> newtonApi.arcsin(expression)
            "Inverse Tangent" -> newtonApi.arctan(expression)
            "Absolute Value" -> newtonApi.abs(expression)
            "Logarithm" -> newtonApi.log(expression)
            else -> null
        }
    }
    
    fun prepareExpression(expression : String) : String {
        return URLEncoder.encode(expression.replace(" ", "").replace("/", "(over)"), "UTF-8")
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        operator = parent.getItemAtPosition(pos) as String
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}