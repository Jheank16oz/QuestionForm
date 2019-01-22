package com.jheank16oz.questionform


import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.View
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*


class TimeTextView : TextInputEditText, View.OnClickListener {

    private var listener: OnDateSelectedListener? = null

    private var myCalendar = Calendar.getInstance()
    private var minC:Calendar? = null
    private var maxC:Calendar? = null
    private var textInputLayout:TextInputLayout? = null

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
                    textInputLayout?.error = "La hora maxima disponible es $time"
                }
            }

            if (myCalendar.before(minC)) {
                minC?.timeInMillis?.let { it1 ->
                    val time = DateUtils.formatDateTime(context, it1, DateUtils.FORMAT_SHOW_TIME)
                    textInputLayout?.error = "La hora mínima disponible es $time"
                }
            }
        }


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
            myCalendar.set(YEAR,1970)
            myCalendar.set(MONTH,0)
            myCalendar.set(DAY_OF_MONTH,1)
            updateLabel()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun setRangeTime(start: String?, end: String?){
        val pattern = "HH:mm:ss"
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
        displayTimeDialog()
    }


    private fun updateLabel() {
        setText(formatDateTime(myCalendar.time.time, context))
    }

    private fun formatDateTime(time: Long, context: Context): String {
        val sb = StringBuilder()

        sb.append(DateUtils.formatDateTime(context, time, DateUtils.FORMAT_SHOW_TIME))

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
