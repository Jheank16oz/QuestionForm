package com.jheank16oz.questionform

import android.content.Context
import android.net.Uri
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
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var parent:ViewGroup? = null
    private var questions:List<Question>? = null
    private var views = ArrayList<View?>()
    private var qHelper:QuestionHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        parent = inflater.inflate(R.layout.fragment_form, container, false) as ViewGroup
        qHelper = context?.let { QuestionHelper(it,layoutInflater, parent?.contentQuestions!!) }

        parent?.send?.setOnClickListener {
            qHelper?.attemptSend(questions, views)
        }

        return parent
    }


    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FormFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FormFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
