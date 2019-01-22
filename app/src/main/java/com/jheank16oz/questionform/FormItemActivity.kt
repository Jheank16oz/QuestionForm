package com.jheank16oz.questionform

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class FormItemActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_item)

        title = intent.getStringExtra(TITTLE)?:""
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_clear_white_24dp)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val parents: List<Question> = intent.getParcelableArrayListExtra(QUESTIONS)
        with(fragment as FormFragment){
            setQuestions(parents)
            displayQuestions()
            setButtonText("Agregar")
        }



    }


    companion object {
        const val QUESTIONS = "parcelable_questions"
        const val TITTLE= "tittle"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
