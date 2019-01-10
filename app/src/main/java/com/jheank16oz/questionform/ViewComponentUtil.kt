package com.jheank16oz.questionform

import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.EditText

/**
 *
 *  <p>ViewComponentUtil</p>
 */

fun View.getResponse(question: Question):ViewResponse? {

    when(question.type){
        QuestionHelper.INPUT -> {
            val component = (this as TextInputLayout).editText
            return ViewResponse(component?.valid(question)?:false, component?.text)
        }
        QuestionHelper.TIME -> {
            val component = (this as TextInputLayout).editText as TimeTextView?
            return ViewResponse(component?.valid(question)?:false, component?.text)
        }
        QuestionHelper.DATE -> {
            val component = (this as TextInputLayout).editText
            return ViewResponse(component?.valid(question)?:false, component?.text)
        }
        QuestionHelper.DATETIME -> {
            val component = (this as TextInputLayout).editText
            return ViewResponse(component?.valid(question)?:false, component?.text)
        }
        QuestionHelper.SELECT -> {
            var response:ViewResponse? = null
            (this as SelectViewGroup).let {it->
                it.getSelectedQuestions()?.let {list->
                    response = it.views.getResponse(list) as ViewResponse
                }
            }
            return response
        }
        QuestionHelper.FORM -> {

        }
    }

    return null
}

fun EditText.valid(question:Question):Boolean{
    var valid = true
    val text = this.text.toString()
    if (question.obligatory && text.isEmpty()){
        this.error = "Campo obligatorio"
        valid = false
    }else if (this.error != null){
        valid = false
    }else{
        this.error = null
    }

    return valid
}


data class ViewResponse(var valid: Boolean, var value:Any?)


fun ArrayList<View?>.getResponse(questions: List<Question?>) : ViewResponse?{
    var valid = true
    var value = ""
    this.forEachIndexed{index, view ->
        view?.let{currentView ->
            questions[index]?.let {
                currentView.getResponse(it)?.let {response ->
                    if (!response.valid){
                        valid = false
                    }
                    value += "${it.id} / ${it.type} / " + response.value
                }?:kotlin.run {
                    valid = false
                    value += "${it.id} / ${it.type} / null"
                }
            }
        }
    }

    return ViewResponse(valid, value)

}