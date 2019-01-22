package com.jheank16oz.questionform

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_form.*
import kotlinx.android.synthetic.main.fragment_form.view.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FormFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FormFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FormFragment : Fragment() {
    private var parent:ViewGroup? = null
    private var questions:List<Question>? = null
    private var views = ArrayList<View?>()
    private var qHelper:QuestionHelper? = null
    private var cameraUtil: CameraUtil? =null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        cameraUtil = CameraUtil(this)
        // Inflate the layout for this fragment
        parent = inflater.inflate(R.layout.fragment_form, container, false) as ViewGroup
        qHelper = context?.let { QuestionHelper(it,layoutInflater, parent?.contentQuestions!!, cameraUtil) }
        qHelper?.setScrollView(parent?.scrollQuestions)

        parent?.send?.setOnClickListener {
            qHelper?.attemptSend(questions, views)
        }

        return parent
    }

    fun setQuestions(questions:List<Question>){
        this.questions = questions
    }

    fun displayQuestions(){
        if (!isAdded){
            return
        }

        when {
            questions == null -> {
                Toast.makeText(context,"No se encontraron preguntas",Toast.LENGTH_SHORT).show()
                return
            }
            questions!!.isEmpty() -> {
                Toast.makeText(context,"No se encontraron preguntas",Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                contentQuestions.removeAllViews()

                questions?.forEach { question ->

                    qHelper?.create(question)?.let {
                        contentQuestions.addView(qHelper?.label(question))
                        contentQuestions.addView(it)
                        views.add(it)
                    }?:run{
                        // se agregan las vistas nulas para las validaciones
                        views.add(null)
                    }
                }

            }
        }
    }

    fun setChilds(childs: List<Question>) {
        qHelper?.childs = childs
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cameraUtil?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
       cameraUtil?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun setButtonText(msg:String){
        parent?.send?.text = msg
    }
}
