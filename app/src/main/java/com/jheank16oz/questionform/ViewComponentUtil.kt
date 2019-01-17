package com.jheank16oz.questionform

import android.support.design.widget.TextInputLayout
import android.view.View

/**
 *
 *  <p>ViewComponentUtil</p>
 */

fun View.getResponse(question: Question):ViewResponse? {

    when(question.type){
        QuestionHelper.INPUT -> {
            val parent = (this as TextInputLayout?)
            val component = parent?.editText
            return ViewResponse(parent?.valid(question)?:false, component?.text)
        }
        QuestionHelper.TIME -> {
            val parent = (this as TextInputLayout?)
            val component = parent?.editText
            return ViewResponse(parent?.valid(question)?:false, component?.text)
        }
        QuestionHelper.DATE -> {
            val parent = (this as TextInputLayout?)
            val component = parent?.editText
            return ViewResponse(parent?.valid(question)?:false, component?.text)
        }
        QuestionHelper.DATETIME -> {
            val parent = (this as TextInputLayout?)
            val component = parent?.editText
            return ViewResponse(parent?.valid(question)?:false, component?.text)
        }
        QuestionHelper.SELECT -> {
            var response:ViewResponse? = null
            (this as SelectViewGroup).let {it->
                if (it.valid(question)) {
                    it.getSelectedQuestions()?.let { list ->
                        response = it.views.getResponse(list) as ViewResponse
                    }
                }


            }
            return response
        }
        QuestionHelper.FORM -> {

        }
    }

    return null
}

private fun SelectViewGroup.valid(question: Question): Boolean {
    this.setError(null)
    var valid = true
    if (question.obligatory){
        valid = !this.isEmptySelection()
        if (!valid){
            this.setError("Campo obligatorio")
        }
    }
    return valid
}

fun TextInputLayout.valid(question:Question):Boolean{
    var valid = true
    val text = this.editText?.text.toString()
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