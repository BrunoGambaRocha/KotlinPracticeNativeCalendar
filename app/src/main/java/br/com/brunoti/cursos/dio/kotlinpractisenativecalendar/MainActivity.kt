package br.com.brunoti.cursos.dio.kotlinpractisenativecalendar

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events.*
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import br.com.brunoti.cursos.dio.kotlinpractisenativecalendar.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val btnSetEvent: Button = binding.btnSetEvent
        btnSetEvent.setOnClickListener{

            //TODO: not yet implemented exceptions
            val title: String = binding.edtTitle.text.toString()
            val description: String = binding.edtDescription.text.toString()
            val local: String = binding.edtPlace.text.toString()
            val guests: String = binding.edtGuestEmail.text.toString()
            val startDate: String = binding.edtStartDate.text.toString()
            val startTime: String = binding.edtStartTime.text.toString()
            val endDate: String = binding.edtEndDate.text.toString()
            val endTime: String = binding.edtEndTime.text.toString()

            val alertTimeInMilliseconds: Int = Integer.valueOf(binding.edtReminder.text.toString()) * 60 * 1000
            Log.d("debug-", alertTimeInMilliseconds.toString())

            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val startTimeInMilliseconds: Long = (dateFormat.parse("$startDate $startTime:00") as Date).time
            val endTimeInMilliseconds: Long = (dateFormat.parse("$endDate $endTime:59") as Date).time

            val intent = Intent(Intent.ACTION_INSERT)
                .setData(CONTENT_URI)
                .putExtra(TITLE, title)
                .putExtra(DESCRIPTION, description)
                .putExtra(EVENT_LOCATION, local)
                .putExtra(ALL_DAY, 0)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTimeInMilliseconds)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTimeInMilliseconds)

                .putExtra(GUESTS_CAN_MODIFY, 0)
                .putExtra(GUESTS_CAN_INVITE_OTHERS, 0)
                .putExtra(GUESTS_CAN_SEE_GUESTS, 0)
                .putExtra(Intent.EXTRA_EMAIL, guests)

                //TODO: not yet added reminders
                .putExtra(HAS_ALARM, 1)
                .putExtra(ALLOWED_REMINDERS, "CalendarContract.Reminders.METHOD_ALERT")
                .putExtra(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
                .putExtra(CalendarContract.Reminders.MINUTES, alertTimeInMilliseconds)

            startActivity(intent)
        }

    }

}