package com.isaac.testapi2

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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
    }
}