package com.jheank16oz.questionform


import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.layout_direction.view.*


class DirectionView   @kotlin.jvm.JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr), SelectLocationDialog.SelectLatLngListener{

    override fun selected(location: LatLng?) {
        this.location = location
        updateView()
    }

    private var location:LatLng? = null
    private  var question:Question? = null
    private var att: AttributeSet? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_direction, this,true)
        attrs?.let {
            att = it
        }

        initialize()
    }

    fun setQuestion(question: Question?){
        this.question = question

    }

    private fun initialize(){
        setOnClickListener {
            val dialog = SelectLocationDialog()
            dialog.initialLatLng(location)
            dialog.setSelectLatLngListener(this)
            val ft = (context as AppCompatActivity).supportFragmentManager?.beginTransaction()
            dialog.show(ft, SelectLocationDialog.TAG)
        }
    }

    private fun updateView() {
        location?.let {
            tittle.text = "ubicación = $location"
        }?:kotlin.run {
            question?.properties?.placeholder?.let {
                tittle.text = it
            }?:kotlin.run {
                tittle.text = "Seleccione una ubicación"
            }
        }
    }

    fun setError(s: String?) {
        //error.text = s
    }


}
