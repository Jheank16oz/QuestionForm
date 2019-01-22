package com.jheank16oz.questionform


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.support.design.widget.TextInputEditText
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.View
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale


class DateTextView : TextInputEditText, View.OnClickListener {

    private var listener: OnDateSelectedListener? = null

    private var myCalendar = Calendar.getInstance()
    private var minC:Calendar? = null
    private var maxC:Calendar? = null

    private var date: DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateLabel()
        if (listener != null) {
            listener!!.onDateReady(this@DateTextView)
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
    fun setRangeDate(start: String?, end: String?){
        val pattern = "yyyy-MM-dd"
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
        DateUtils.formatDateRange(context, Formatter(sb), time, time,
                DateUtils.FORMAT_SHOW_DATE,
                TimeZone.getDefault().id)
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



}
