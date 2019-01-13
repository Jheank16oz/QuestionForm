package com.jheank16oz.questionform

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), FormFragment.OnFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val json = JSONHandler
                .parseResource(this, R.raw.request)

        val jsonOBJ = JSONObject(json)
        val jsonQuestios = jsonOBJ.getJSONArray("preguntas").getJSONObject(0)
        Log.e("questions ", json)

        val mGson = Gson()
        try {
            val parents: List<Question> = mGson.fromJson(jsonQuestios.getJSONArray("parents").toString(), object : TypeToken<List<Question>>() {}.type)
            val childs: List<Question> = mGson.fromJson(jsonQuestios.getJSONArray("childs").toString(), object : TypeToken<List<Question>>() {}.type)
            (fragment as FormFragment).setChilds(childs)
            (fragment as FormFragment).setQuestions(parents)
            (fragment as FormFragment).displayQuestions()
        }catch (e: JsonSyntaxException){
            Toast.makeText(this, "JSON malformed", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }


    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
