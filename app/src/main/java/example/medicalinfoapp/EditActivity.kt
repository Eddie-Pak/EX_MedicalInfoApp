package example.medicalinfoapp

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import example.medicalinfoapp.databinding.ActivityEditBinding
import example.medicalinfoapp.databinding.ActivityMainBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this, R.array.bloody_types, android.R.layout.simple_list_item_1
        )

        binding.birthdateLayer.setOnClickListener {
            val listener = OnDateSetListener{ _, year, month, dayOfMonth ->
                binding.brithValueTextView.text = "$year-${month.inc()}-$dayOfMonth"
            }
            DatePickerDialog(
                this, listener, 2000, 1, 1
            ).show()
        }

        binding.precautionCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.precautionEditText.isVisible = isChecked
        }
        binding.precautionEditText.isVisible = binding.precautionCheckBox.isChecked

        binding.saveButton.setOnClickListener {
            saveData()
            finish()
        }
    }

    private fun saveData(){
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit()) {
            putString(NAME, binding.nameEditText.text.toString())
            putString(BIRTHDATE, binding.brithValueTextView.text.toString())
            putString(BLOOD_TYPE, getBloodType())
            putString(PHONE_NUMBER, binding.phoneNumEditText.text.toString())
            putString(PRECAUTION, getPrecaution())
            apply()
        }
        Toast.makeText(this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun getBloodType(): String {
        val bloodAlphabet = binding.bloodTypeSpinner.selectedItem.toString()
        val bloodSign = if(binding.bloodTypePlus.isChecked) "+" else "-"
        return "$bloodSign $bloodAlphabet"
    }

    private fun getPrecaution(): String {
        return if(binding.precautionCheckBox.isChecked) binding.precautionEditText.text.toString() else ""
    }
}