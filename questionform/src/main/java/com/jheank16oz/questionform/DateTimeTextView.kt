package com.jheank16oz.questionform


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.View
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE
import java.util.Locale


class DateTimeTextView : TextInputEditText, View.OnClickListener {

    private var listener: OnDateSelectedListener? = null

    private var myCalendar = Calendar.getInstance()
    private var minC:Calendar? = null
    private var maxC:Calendar? = null
    private var textInputLayout:TextInputLayout? = null

    private var date: DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateLabel()
        if (listener != null) {
            listener!!.onDateReady(this@DateTimeTextView)
        }

        displayTimeDialog()

    }

    private fun displayTimeDialog() {

        val mTimePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener {
            _, hourOfDay, minute ->

            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            myCalendar.set(Calendar.MINUTE, minute)
            myCalendar.set(Calendar.SECOND, 0)
            myCalendar.set(Calendar.MILLISECOND, 0)
            validateRange()
            updateLabel()
        }, myCalendar.get(HOUR_OF_DAY), myCalendar.get(MINUTE), false)
        mTimePicker.setTitle("Seleccione la hora")
        mTimePicker.show()
    }

    private fun validateRange() {
        textInputLayout?.error = null
        minC?.let {
            if (myCalendar.after(maxC)){
                maxC?.timeInMillis?.let { it1 ->
                    val time = DateUtils.formatDateTime(context, it1, DateUtils.FORMAT_SHOW_TIME)
                    textInputLayout?.error = "La hora maxima disponible para este día es $time"
                }
            }

            if (myCalendar.before(minC)) {
                minC?.timeInMillis?.let { it1 ->
                    val time = DateUtils.formatDateTime(context, it1, DateUtils.FORMAT_SHOW_TIME)
                    textInputLayout?.error = "La hora mínima disponible para este día es $time"
                }
            }
        }


    }

    //In which you need put here
    val timeText: String
        get() {
            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)

            return sdf.format(myCalendar.time.time)
        }

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        this.listener = null
        if (attrs != null) {
            setOnClickListener(this)
            updateLabel()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun setRangeDateTime(start: String?, end: String?){
        val pattern = "yyyy-MM-dd HH:mm:ss"
        val formatter = SimpleDateFormat(pattern)
        minC = Calendar.getInstance()
        maxC = Calendar.getInstance()


        try {
            minC?.time = formatter.parse(start)
        }catch (e:Exception){
            minC = null
        }

        try {
            maxC?.time = formatter.parse(end)
        }catch (e:Exception){
            maxC = null
        }

        maxC?.let {
            if (myCalendar.after(it)){
                myCalendar.time = it.time
                updateLabel()
            }
        }

        minC?.let {
            if (myCalendar.before(it)){
                myCalendar.time = it.time
                updateLabel()
            }
        }


    }

    override fun onClick(view: View) {

        val mDatePickerDialog = DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH))

        // si se asignan el rango de las dos fechas y no coinciden
        // no se asignan los rangos
        if (minC != null && maxC != null){
            if (minC!!.after(maxC)){
                mDatePickerDialog.show()
                return
            }
        }

        minC?.let {
            mDatePickerDialog.datePicker.minDate = it.timeInMillis
        }

        maxC?.let {
            mDatePickerDialog.datePicker.maxDate = it.timeInMillis
        }
        mDatePickerDialog.show()


    }


    private fun updateLabel() {
        setText(formatDateTime(myCalendar.time.time, context))
    }

    private fun formatDateTime(time: Long, context: Context): String {
        val sb = StringBuilder()

        sb.append(DateUtils.formatDateTime(context, time, DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_DATE or
                DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_ABBREV_ALL))

        return sb.toString()
    }


    interface OnDateSelectedListener {
        fun onDateReady(v: View)
    }

    fun setOnDateSelectedListener(listener: OnDateSelectedListener) {
        this.listener = listener
    }


    fun getDate(): Calendar {
        return myCalendar
    }

    fun setDate(year: Int, monthOfYear: Int, dayOfMonth: Int){
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        updateLabel()
    }

    fun setTextInputLayout(textInputLayout: TextInputLayout?) {
        this.textInputLayout = textInputLayout
    }


}
