package com.jheank16oz.questionform

import android.content.Context
import android.content.Intent
import android.support.design.widget.TextInputLayout
import android.text.*
import android.text.method.DigitsKeyListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.form.view.*
import kotlinx.android.synthetic.main.image.view.*
import org.json.JSONArray
import java.io.File


/**
 *
 *  <p>QuestionHelper</p>
 */

class QuestionHelper(var context: Context, private var inflater: LayoutInflater?, var group:ViewGroup, var cameraUtil: CameraUtil?){


    var childs:List<Question>? = null
    var files = HashMap<Int,File>()
    private var scrollQuestions: ScrollView? = null


    /**
     * Crea el componenete con el id de layout correspondiente
     * posteriormente solicita la asignacion de propiedades
     * @param question contiene
     * @return una vista con las propiedades
     */
    fun create(question: Question?): View? {
        var layout:Int? = null
        when(question?.type){
            INPUT -> {
                layout = R.layout.input
            }
            TIME -> {
                layout = R.layout.time
            }
            DATE -> {
                layout = R.layout.date
            }
            DATETIME -> {
                layout = R.layout.datetime
            }
            SELECT -> {
                layout = R.layout.select
            }
            FORM -> {
                layout = R.layout.form
            }
            CAPTURE -> {
                layout = R.layout.image
            }
            LOCATION -> {
                layout = R.layout.direction
            }

        }
        layout?.let {
            val component = inflater?.inflate(it, group, false)
            component?.setProperties(question)
            return component
        }

        return null
    }

    fun label(question: Question?):View?{
        val component = inflater?.inflate(R.layout.label, group, false) as TextView?
        component?.text = question?.tittle
        if (question?.obligatory == true){
            val obligatory = "<font color='#EE0000'>*</font>"
            component?.text = Html.fromHtml("${question.tittle}  $obligatory")
        }
        return component
    }

    /**
     * Divide la asignacion de propiedades por typo de componente
     */
    private fun View.setProperties(question: Question?){
        when(question?.type){
            INPUT -> {
                inputProperties(this as TextInputLayout, question)
            }
            DATE -> {
                dateProperties((this as TextInputLayout).editText as DateTextView, question)
            }
            DATETIME -> {
                dateTimeProperties(this as TextInputLayout, question)
            }

            TIME ->{
                timeProperties(this as TextInputLayout, question)
            }

            SELECT -> {
                selectProperties(this as SelectViewGroup, question)
            }
            FORM -> {
                formProperties(this as LinearLayout, question)
            }
            CAPTURE -> {
                captureProperties(this as LinearLayout, question)
            }
            LOCATION -> {
                locationProperties(this as ViewGroup, question)
            }
        }
    }

    private fun locationProperties(viewGroup: ViewGroup?, question: Question?) {
        question?.let { it ->
            viewGroup?.let { group ->
                group.id = it.id
            }
        }

    }

    private fun captureProperties(linearLayout: LinearLayout?, question: Question?) {
        question?.let { it ->
            linearLayout?.let { group ->
                group.id = it.id
                question.properties?.let { it ->
                    it.placeholder?.let {placeholder ->
                        group.capture.text = placeholder
                    }

                    group.image.setOnClickListener {
                        cameraUtil?.openCamera(object:CameraUtil.OnCameraListener{
                            override fun onFileResult(file: File) {
                                Glide.with(context).load(file).into(group.image)
                                files[group.id] = file
                            }

                        })
                    }
                    group.capture.setOnClickListener {
                        cameraUtil?.openCamera(object:CameraUtil.OnCameraListener{
                            override fun onFileResult(file: File) {
                                Glide.with(context).load(file).into(group.image)
                                group.imageContainer.displayedChild = 1
                                files[group.id] = file

                            }

                        })
                    }
                }
            }
        }

    }


    private fun formProperties(linearLayout: LinearLayout?, question: Question?) {
        question?.let { it ->
            linearLayout?.let {group ->
                group.id = it.id
                it.properties?.let {properties ->
                    val content = group.contentForm
                    val add = group.add
                    add.text = properties.placeholder?:it.tittle

                    add.setOnClickListener {_->
                        /*val chip = inflater?.inflate(R.layout.chip, group, false) as Chip?
                        chip?.isCheckable = false
                        content.addView(chip)*/
                        val intent = Intent(context,FormItemActivity::class.java)
                        intent.putExtra(FormItemActivity.QUESTIONS, properties.form)
                        intent.putExtra(FormItemActivity.TITTLE, properties.placeholder?:it.tittle)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }

    private fun selectProperties(viewGroup: SelectViewGroup?, question: Question?) {
        question?.let {
            viewGroup?.let {group ->
                group.id = it.id
                it.properties?.let{properties ->
                    properties.items?.let { items ->
                        group.setItems(items)
                        group.setChilds(childs)
                        group.setCameraUtil(cameraUtil)
                        group.initialize()
                    }
                }
            }
        }
    }

    private fun timeProperties(timeInputLayout: TextInputLayout?, question: Question?) {
        question?.let {
            timeInputLayout?.let {time ->
                it.properties?.let{properties ->
                    (timeInputLayout.editText as TimeTextView).id = it.id
                    (timeInputLayout.editText as TimeTextView).setTextInputLayout(time)
                    (timeInputLayout.editText as TimeTextView).setRangeTime(properties.minTime, properties.maxTime)
                }
            }
        }
    }

    private fun dateTimeProperties(dateTimeTextView: TextInputLayout?, question: Question?) {
        question?.let { it ->
            dateTimeTextView?.let{datetime->
                datetime.id = it.id
                it.properties?.let{properties ->
                    (dateTimeTextView.editText as DateTimeTextView).id = it.id
                    (dateTimeTextView.editText as DateTimeTextView).setTextInputLayout(datetime)
                    (dateTimeTextView.editText as DateTimeTextView).setRangeDateTime(properties.minDateTime, properties.maxDateTime)
                }
            }
        }
    }

    private fun dateProperties(dateTextView: DateTextView?, question: Question?) {
        question?.let { it ->
            dateTextView?.let{date->
                date.id = it.id
                it.properties?.let{properties ->
                    dateTextView.id = it.id
                    dateTextView.setRangeDate(properties.minDate, properties.maxDate)
                }
            }
        }

    }

    /**
     * Asigna las propiedades del TextInputLayout
     */
    private fun inputProperties(textInputLayout: TextInputLayout?, question: Question?) {
        if (question != null) {
            textInputLayout?.id = question.id


            question.properties?.let { it ->
                // placeholder o hint en Android
                it.placeholder?.let{ placeholder->
                    textInputLayout?.hint = placeholder
                }

                when(it.inputType){

                    INPUT_TYPE_TEXT ->{

                        textInputLayout?.editText?.inputType = InputType.TYPE_CLASS_TEXT
                        // se asigna la cantidad maxima de caracteres
                        textInputLayout?.editText?.filters = arrayOf<InputFilter>(
                                InputFilter.LengthFilter(if (it.length > 0) it.length else DEFAULT_INPUT_COUNT_TEXT))
                    }

                    INPUT_TYPE_NUMBER ->{
                        textInputLayout?.editText?.inputType = InputType.TYPE_CLASS_NUMBER
                        textInputLayout?.editText?.keyListener = DigitsKeyListener.getInstance("-0123456789.")
                        // se asigna la cantidad maxima de caracteres
                        textInputLayout?.editText?.filters = arrayOf<InputFilter>(
                                InputFilter.LengthFilter(if (it.length > 0) it.length else DEFAULT_INPUT_COUNT_NUMBER))

                        textInputLayout?.editText?.addTextChangedListener(object:TextWatcher{
                            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                            }

                            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                                try {
                                    // se
                                    val count = p0.toString().toDouble()
                                    if (count > 0) {
                                        if (count < it.min) {
                                            textInputLayout.error = "la cantidad mínma es de ${it.min}"
                                        } else if (it.max > it.min && count > it.max) {
                                            textInputLayout.error = "la cantidad maxima es de ${it.max}"
                                        } else {
                                            textInputLayout.error = null
                                        }
                                    }
                                }catch (e:NumberFormatException){
                                    e.printStackTrace()
                                    textInputLayout.error = "Número no valido"
                                }
                            }

                            override fun afterTextChanged(p0: Editable?) {

                            }

                        })
                    }

                    INPUT_TYPE_MULTILINE ->{
                        textInputLayout?.editText?.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
                        // se asigna la cantidad maxima de caracteres
                        textInputLayout?.editText?.filters = arrayOf<InputFilter>(
                                InputFilter.LengthFilter(if (it.length > 0) it.length else DEFAULT_INPUT_COUNT_MULTILINE))

                        textInputLayout?.editText?.setSingleLine(false)
                        textInputLayout?.editText?.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION)

                    }
                    INPUT_TYPE_EMAIL -> {
                        textInputLayout?.editText?.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                        // se asigna la cantidad maxima de caracteres
                        textInputLayout?.editText?.filters = arrayOf<InputFilter>(
                                InputFilter.LengthFilter(if (it.length > 0) it.length else DEFAULT_INPUT_COUNT_EMAIL))

                    }

                    else ->{ }
                }


            }
        }else{
            throw IllegalArgumentException("la pregunta no es valida = $question")
        }


    }

    fun attemptSend(questions: List<Question>?, views: ArrayList<View?>) {
        val viewComponentUtil = ViewComponentUtil(scrollQuestions)
        questions?.let { it ->
            // generating JSON_ARRAY
            val response = JSONArray()
            viewComponentUtil.attemptSend(views,questions)?.let {
                Log.e("Response", it.toString())
            }
        }

    }

    fun getQuestionByChildId(childId:Int): Question? {

        val a = childs?.filter {  question -> question.id == childId }
        return a?.get(0)
    }


    fun setScrollView(scrollQuestions: ScrollView?) {

        this.scrollQuestions = scrollQuestions
    }


    companion object {


        const val INPUT:String = "Text"
        const val TIME:String = "Time"
        const val DATE:String = "Date"
        const val DATETIME:String = "Datetime"
        const val CAPTURE:String = "Image"
        const val SELECT:String = "List"
        const val FORM:String = "Form"
        const val LOCATION:String = "Direction"

        const val INPUT_TYPE_TEXT = "text"
        const val INPUT_TYPE_NUMBER = "number"
        const val INPUT_TYPE_MULTILINE = "multiline"
        const val INPUT_TYPE_EMAIL = "email"



        /**
         * maximo numero de caracteres por defecto para  INPUT con inputType INPUT_TYPE_NUMBER
         */
        const val DEFAULT_INPUT_COUNT_NUMBER = 12

        /**
         * maximo numero de caracteres por defecto para  INPUT con inputType INPUT_TYPE_TEXT
         */
        const val DEFAULT_INPUT_COUNT_TEXT = 50

        /**
         * maximo numero de caracteres por defecto para  INPUT con inputType INPUT_TYPE_MULTILINE
         */
        const val DEFAULT_INPUT_COUNT_MULTILINE = 255

        /**
         * maximo numero de caracteres por defecto para  INPUT con inputType INPUT_TYPE_EMAIL
         */
        const val DEFAULT_INPUT_COUNT_EMAIL = 100
    }




}





