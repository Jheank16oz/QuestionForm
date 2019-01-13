package com.jheank16oz.questionform


import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.layout_select.view.*


class SelectViewGroup   @kotlin.jvm.JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr){

    private var items:List<Item>? = null
    private var selecteChilds:ArrayList<Question>? = null
    public var views = ArrayList<View?>()
    private var qHelper:QuestionHelper? = null
    val inflater = LayoutInflater.from(context)

    private var att: AttributeSet? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_select, this,true)
        attrs?.let {
            att = it
        }
        qHelper = QuestionHelper(context,inflater , contentDependants)

    }

    fun setItems(questions: List<Item>){
        this.items = questions
    }

    fun initialize(){
        att?.let { it ->

            val itemList = ArrayList<Any>()
            itemList.add(Item(-1,"Seleccione una opción"))
            items?.let {
                itemList.addAll(it)
            }

            val spinnerArrayAdapter = ArrayAdapter<Any>(context, android.R.layout.simple_spinner_item, itemList)
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item)
            this.spinner.adapter = spinnerArrayAdapter

            this.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) { }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    contentDependants.removeAllViews()
                    selecteChilds?.clear()
                    views.clear()
                    if (position != 0){
                        (itemList[position] as Item).dependants?.forEach { questionId ->


                            qHelper?.getQuestionByChildId(questionId)?.let { question ->
                                selecteChilds?.add(question)
                                qHelper?.create(question)?.let { it ->
                                    contentDependants?.addView(qHelper?.label(question))
                                    contentDependants.addView(it)
                                    views.add(it)
                                } ?: kotlin.run {
                                    views.add(null)
                                }
                            }
                        }
                    }

                }

            }

            //error?.text = "Seleccione una opción"
        }

    }


    fun getSelectedQuestions():List<Question?>?{
        return selecteChilds
    }

    fun setChilds(childs: List<Question>?) {
        qHelper?.childs = childs
    }


}
