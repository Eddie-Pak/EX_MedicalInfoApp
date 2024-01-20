package example.medicalinfoapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import example.medicalinfoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goInputActivityButton.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }

        binding.deleteButton.setOnClickListener {
            deleteData()
        }

        binding.phoneNumlayer.setOnClickListener {
            with(Intent(Intent.ACTION_VIEW)) {
                val phoneNumber = binding.phoneNumValueTextView.text.toString()
                    .replace("-", "")
                data= Uri.parse("tel:$phoneNumber")
                startActivity(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getDataAndUiUpdate()
    }

    private fun getDataAndUiUpdate() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)) {
            binding.nameValueTextView.text = getString(NAME, "미정")
            binding.brithValueTextView.text = getString(BIRTHDATE, "미정")
            binding.bloodTypeValueTextView.text = getString(BLOOD_TYPE, "미정")
            binding.phoneNumValueTextView.text = getString(PHONE_NUMBER, "미정")

            val precaution = getString(PRECAUTION, "")

            binding.precautionTextView.isVisible = precaution.isNullOrEmpty().not()
            binding.precautionValueTextView.isVisible = precaution.isNullOrEmpty().not()

            if(!precaution.isNullOrEmpty()) {
                binding.precautionValueTextView.text = precaution
            }
        }
    }

    private fun deleteData() {
        with(getSharedPreferences(USER_INFORMATION, MODE_PRIVATE).edit()) {
            clear()
            apply()
            getDataAndUiUpdate()
        }
        Toast.makeText(this, "초기화 하였습니다.", Toast.LENGTH_SHORT).show()
    }
}