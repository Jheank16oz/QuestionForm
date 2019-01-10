package com.jheank16oz.questionform

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FormFragment.OnFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val json = JSONHandler
                .parseResource(this, R.raw.questions)
        Log.e("questions ", json)

        val mGson = Gson()
        try {
            val list: List<Question> = mGson.fromJson(json, object : TypeToken<List<Question>>() {}.type)
            (fragment as FormFragment).setQuestions(list)
            (fragment as FormFragment).displayQuestions()
        }catch (e: JsonSyntaxException){
            Toast.makeText(this, "JSON malformed", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }


    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
