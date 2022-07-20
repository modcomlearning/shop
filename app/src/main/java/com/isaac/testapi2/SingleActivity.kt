package com.isaac.testapi2

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONObject

class SingleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        //access shared prefferences
        val prefs: SharedPreferences = getSharedPreferences("store",
            Context.MODE_PRIVATE)

         //access the saved product_name from prefferences and put in the TextView
        val title = prefs.getString("product_name", "")
        val text_title = findViewById(R.id.p_name) as TextView
        text_title.text = title

        //access the saved product_desc from prefferences and put in the TextView
        val desc = prefs.getString("product_desc", "")
        val text_desc = findViewById(R.id.p_desc) as TextView
        text_desc.text = desc

        //access the saved product_cost from prefferences and put in the TextView
        val cost = prefs.getString("product_cost", "")
        val text_cost= findViewById(R.id.p_cost) as TextView
        text_cost.text = cost

        //access the saved image from prefferences and put in the ImageView Using Glide
        val image_url = prefs.getString("image_url", "")
        val image = findViewById(R.id.img_url) as ImageView
        Glide.with(applicationContext).load(image_url)
            .apply(RequestOptions().centerCrop())
            .into(image)

        val progressbar = findViewById<ProgressBar>(R.id.progressbar)
        progressbar.visibility = View.GONE
        val phone = findViewById<EditText>(R.id.phone)
        val pay = findViewById<Button>(R.id.pay)

        pay.setOnClickListener {
            progressbar.visibility  = View.VISIBLE
             val client = AsyncHttpClient(true, 80, 443)
             val json = JSONObject()
              json.put("amount", cost)
              json.put("phone", phone)

              val con_body = StringEntity(json.toString())
              client.post(this, "", con_body, "application/json",
              object : JsonHttpResponseHandler(){
                  override fun onSuccess(
                      statusCode: Int,
                      headers: Array<out Header>?,
                      response: JSONObject?
                  ) {
                      //super.onSuccess(statusCode, headers, response)
                      Toast.makeText(applicationContext, "Paid", Toast.LENGTH_LONG).show()
                      progressbar.visibility = View.GONE
                  }//


                  override fun onFailure(
                      statusCode: Int,
                      headers: Array<out Header>?,
                      throwable: Throwable?,
                      errorResponse: JSONObject?
                  ) {
                      //super.onFailure(statusCode, headers, throwable, errorResponse)
                      Toast.makeText(applicationContext, "Not Paid", Toast.LENGTH_LONG).show()
                      progressbar.visibility = View.GONE
                  }
              })

        }//end


    }
}




