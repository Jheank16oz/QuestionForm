package com.jheank16oz.questionform

import android.graphics.Point
import android.support.design.widget.TextInputLayout
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ScrollView

class ViewComponentUtil(var scrollQuestions: ScrollView?) {

    private var currentErrorView: View? = null
    private var firstError = true
    /**
     *
     *  <p>ViewComponentUtil</p>
     */

    fun View.getResponse(question: Question): ViewResponse? {

        when (question.type) {
            QuestionHelper.INPUT -> {
                val parent = (this as TextInputLayout?)
                val component = parent?.editText
                return ViewResponse(parent?.valid(question) ?: false, component?.text, this)
            }
            QuestionHelper.TIME -> {
                val parent = (this as TextInputLayout?)
                val component = parent?.editText
                return ViewResponse(parent?.valid(question) ?: false, component?.text, this)
            }
            QuestionHelper.DATE -> {
                val parent = (this as TextInputLayout?)
                val component = parent?.editText
                return ViewResponse(parent?.valid(question) ?: false, component?.text, this)
            }
            QuestionHelper.DATETIME -> {
                val parent = (this as TextInputLayout?)
                val component = parent?.editText
                return ViewResponse(parent?.valid(question) ?: false, component?.text, this)
            }
            QuestionHelper.SELECT -> {
                var response: ViewResponse? = null
                (this as SelectViewGroup).let { it ->
                    if (it.valid(question)) {
                        it.getSelectedQuestions()?.let { list ->
                            response = getResponse(it.views, list) as ViewResponse
                        }
                    }


                }
                return response
            }
            QuestionHelper.LOCATION -> {
                (this as DirectionView).let {
                    return ViewResponse(it.valid(question) ?: false, it.directionStr, this)
                }
            }

            QuestionHelper.FORM -> {

            }
        }

        return null
    }

    fun DirectionView.valid(question: Question): Boolean? {
        this.setError(null)
        if (question.obligatory) {
            if (this.location == null) {
                this.setError("Campo obligatorio")
                return false
            }
        }

        return true
    }

    private fun SelectViewGroup.valid(question: Question): Boolean {
        this.setError(null)
        var valid = true
        if (question.obligatory) {
            valid = !this.isEmptySelection()
            if (!valid) {
                this.setError("Campo obligatorio")
            }
        }
        return valid
    }

    fun TextInputLayout.valid(question: Question): Boolean {
        var valid = true
        val text = this.editText?.text.toString()
        if (question.obligatory && text.isEmpty()) {
            this.error = "Campo obligatorio"
            valid = false
        } else if (this.error != null) {
            valid = false
        } else {
            this.error = null
        }

        return valid
    }


    data class ViewResponse(var valid: Boolean, var value: Any?, var componentView:View?)


    fun getResponse(list:ArrayList<View?>, questions: List<Question?>): ViewResponse? {

        var valid = true
        var value = ""
        list.forEachIndexed { index, view ->
            view?.let { currentView ->
                questions[index]?.let {
                    currentView.getResponse(it)?.let { response ->
                        if (!response.valid) {
                            valid = false

                            if (firstError){
                                firstError = false
                                currentErrorView = response.componentView
                            }
                        }

                        value += "${it.id} / ${it.type} / " + response.value
                    } ?: kotlin.run {
                        valid = false
                        value += "${it.id} / ${it.type} / null"

                        if (firstError){
                            firstError = false
                            currentErrorView = currentView
                        }
                    }
                }
            }
        }

        return ViewResponse(valid, value, currentErrorView)

    }


    fun attemptSend(list:ArrayList<View?>, questions: List<Question?>):ViewResponse?{
        firstError = true
        currentErrorView = null
        val viewResponse = getResponse(list, questions)
        if (viewResponse?.valid == false){
            scrollQuestions?.let { currentErrorView?.let { it1 -> scrollToView(it, it1) } }
        }
        return viewResponse
    }


    private fun scrollToView(scrollViewParent: ScrollView, view: View) {
        // Get deepChild Offset
        val childOffset = Point()
        getDeepChildOffset(scrollViewParent, view.parent, view, childOffset)
        // Scroll to child.
        Log.e("scroll",childOffset.y.toString())
        scrollViewParent.smoothScrollTo(0, childOffset.y - ((childOffset.y * 0.1).toInt()))
    }


    /**
     * Used to get deep child offset.
     *
     *
     * 1. We need to scroll to child in scrollview, but the child may not the direct child to scrollview.
     * 2. So to get correct child position to scroll, we need to iterate through all of its parent views till the main parent.
     *
     * @param mainParent        Main Top parent.
     * @param parent            Parent.
     * @param child             Child.
     * @param accumulatedOffset Accumalated Offset.
     */
    private fun getDeepChildOffset(mainParent: ViewGroup, parent: ViewParent, child: View, accumulatedOffset: Point) {
        val parentGroup = parent as ViewGroup
        accumulatedOffset.x += child.left
        accumulatedOffset.y += child.top
        if (parentGroup == mainParent) {
            return
        }
        getDeepChildOffset(mainParent, parentGroup.parent, parentGroup, accumulatedOffset)
    }
}