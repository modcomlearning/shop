# Recycler View - In this Project we create a Recycler using data from an online API.
## RecyclerView is the ViewGroup that contains the views corresponding to your data. · Each individual element in the list is defined by a view holder object

## Step 1.
### Create a New Project using Empty Activity, Open your build.gradle(Module) add below lines , these are dependancies we will need in our project
#### Glide will be used to Load images, Loopj will help us get data from our API and GSON to make data in the right format to be used by RecyclerAdapter
```
dependencies {
     
    ..... 
    implementation 'com.github.bumptech.glide:glide:4.4.0'
    implementation 'com.loopj.android:android-async-http:1.4.9'
    implementation "com.google.code.gson:gson:2.8.7"
    .....
}
```

## Step 2
### Create a New Kotlin Class Named Product.kt, put below code, this is a model that will define the data fields we will be working on.

```
  class Product (
      var product_name : String = "",
      var product_cost : String = "",
      var image_url : String = "",// all images from mipmap and drawable
      var product_desc: String = "" ,
  )
```

## Step 3
### Create a new XML layout under res - layout, name this XML single_item.xml, these files defines how our items will be displayed to the user.


```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    >
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:layout_editor_absoluteX="0dp"
        tools:layout_editor_absoluteY="0dp">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:orientation="horizontal">
            <TextView
                android:id="@+id/title"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Person Name"
                android:padding="10dp"
                android:textColor="#FF5722"
                android:textSize="18dp"
                android:textStyle="bold"
                />

            <TextView
                android:id="@+id/cost"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:padding="10dp"
                android:text="KES 50"
                android:textColor="#533131"
                android:textAlignment="textEnd"
                android:textStyle="bold"
                android:layout_gravity="end"

                android:layout_marginRight="20dp"

                />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:padding="10dp">


            <ImageView
                android:id="@+id/image"
                android:layout_width="101dp"
                android:layout_height="74dp"
                android:padding="10dp"

                android:src="@mipmap/ic_launcher">


            </ImageView>

            <TextView
                android:id="@+id/desc"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:padding="10dp"
                android:text="Welcome to Modcom. Here We are dedicated to serve and offer you he best"/>
        </LinearLayout>

    </LinearLayout>

</androidx.constraintlayout.widget.ConstraintLayout>

```

## Step 4
### Below we create a Recycler adapter that will connect the XML file and the Model we created on Step 2 and 3, the Recycler adpater also will receive data coming from our API


```
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class RecyclerAdapter(var context: Context)://When you want to toast smthg without intent or activities
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    //View holder holds the views in single item.xml

    var productList : List<Product> = listOf() // empty product list


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
    //Note below code returns above class and pass the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_item, parent, false)
        return ViewHolder(view)
    }
    //so far item view is same as single item
    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val title = holder.itemView.findViewById(R.id.title) as TextView
        val desc = holder.itemView.findViewById(R.id.desc) as TextView
        val cost = holder.itemView.findViewById(R.id.cost) as TextView
        val image = holder.itemView.findViewById(R.id.image) as ImageView

        //bind
        val item = productList[position]
        title.text = item.product_name
        desc.text = item.product_desc
        cost.text = item.product_cost

        Glide.with(context).load(item.image_url)
            .apply(RequestOptions().centerCrop())
            .into(image)
        //image.setImageResource(item.image)

          //        holder.itemView.setOnClickListener {
          //
          //
          //
          //        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    //we will call this function on Loopj response
    fun setProductListItems(productList: List<Product>){
        this.productList = productList
        notifyDataSetChanged()
    }
}//end class

```

## Step 5
### Now Go to activity_main.xml and create a recycler View widget and a Progress Bar.

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">
        <ProgressBar
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:id="@+id/progressbar"/>

        <androidx.recyclerview.widget.RecyclerView
            android:layout_width="match_parent"
            android:id="@+id/recycler"
            android:layout_height="match_parent"
            tools:listitem="@layout/single_item"/>
    </LinearLayout>


</androidx.constraintlayout.widget.ConstraintLayout>
```

## Step 6
### We now head to our MainActivity File and Get data from our API, we will be getting data from below API - (Application Programming Interface), this link will provide us data from a database.
(https://modcom.pythonanywhere.com/api/all)
#### Open Your Main Activity and write this Code. Below code gets data from our API and pushes to the recycler adapter which maps the data to our XML.

```
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapi.Product
import com.example.testapi.RecyclerAdapter
import com.google.gson.GsonBuilder
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var productList:ArrayList<Product>
    lateinit var recyclerAdapter: RecyclerAdapter //call the adapter
    lateinit var progressbar: ProgressBar
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler)
        progressbar= findViewById(R.id.progressbar)
        progressbar.visibility = View.VISIBLE

        val client = AsyncHttpClient(true,80,443)
        //        //pass the product list to adapter
        recyclerAdapter = RecyclerAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)

        client.get(this, "https://modcom.pythonanywhere.com/api/all",
            null,
            "application/json",
            object: JsonHttpResponseHandler(){
                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONArray?) {
                    //we convert json array to a list of a given model
                    val gson = GsonBuilder().create()
                    val list = gson.fromJson(response.toString(),
                        Array<Product>::class.java).toList()
                    //now pass the converted list to adapter
                    recyclerAdapter.setProductListItems(list)
                    progressbar.visibility = View.GONE
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseString: String?,
                    throwable: Throwable?
                ) {
                    Toast.makeText(applicationContext, "no products on sale"+statusCode, Toast.LENGTH_LONG).show()
                    progressbar.visibility = View.GONE
                }
            }//end handler
        )//end post

        //now put the adapter to recycler view
        recyclerView.adapter = recyclerAdapter
    }
}

```

### Incase of errors with compatibilty issues with AndroidX add below file to gradle.properties add below line
```
android.enableJetifier=true
```

### You can also add Internet Permissions in AndroidManifest.xml.
```
  <uses-permission android:name="android.permission.INTERNET"/>
```


You are done, Run Your code on the device.

## Looks Like below ScreenShot
![Screenshot_20220718-083244](https://user-images.githubusercontent.com/66998462/179450564-569e7f2a-72d2-4059-9669-e5ba126b3d18.png)


## Step 7
### Creating a Single Page Display when one product is clicked.
#### Right click on app - New - Activity - Empty Activity. Give it a name SingleActivity - Finish
#### After Activity is created, head back to your recycler adapter and add below code inside  onBindViewHolder function.


```
 holder.itemView.setOnClickListener {
            //create a shared prefferences variable to store our clicked product
            val prefs: SharedPreferences = context.getSharedPreferences(
                "store",
                Context.MODE_PRIVATE
            )
            //save the product
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString("product_name", item.product_name)
            editor.putString("product_desc", item.product_desc)
            editor.putString("product_cost", item.product_cost)
            editor.putString("image_url", item.image_url)
            editor.apply()

            //Navigate to SingleACtivity, Created Earlier
            val i = Intent(context, SingleActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)

        }
        
        
```        
      
### NB: above code must be put below Glide code in Your Adapter created in Step 4
      Glide.with(context).load(item.image_url)
            .apply(RequestOptions().centerCrop())
            .into(image)
      
## Step 8
### Open the activity_single.xml and put below code, this code shows how a single item wil be displayed.

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".SingleActivity">


    <TextView
        android:id="@+id/p_name"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="New Balance Shoes"
        android:textSize="30dp"
        android:textAlignment="center"
        android:layout_margin="10dp"
        android:padding="20dp"/>

    <ImageView
        android:id="@+id/img_url"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:src="@mipmap/ic_launcher"
        android:scaleType="centerCrop"
        android:adjustViewBounds="true"/>

    <TextView
        android:id="@+id/p_desc"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Latest Desihn in the Market, Welcome All for Our Offers and delivery options Available"
        android:textAlignment="center"
        android:textSize="20sp"
        android:padding="20dp"/>

    <TextView
        android:id="@+id/p_cost"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="KES 3200"
        android:textSize="30dp"
        android:textAlignment="center"
        android:padding="10dp"
        android:layout_margin="10dp"
        android:textStyle="bold"/>

</LinearLayout>

```


## Step 9
### Open Your Single Activity.kt File and add below codes

```
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
```

### Done, Run your App, you will see below screenshot
![Screenshot_20220719-095519](https://user-images.githubusercontent.com/66998462/179686006-dc2c3103-2617-4d90-8ea1-511c01291f83.png)

### Click on One Product, you will see below screnshot

![Screenshot_20220719-095524](https://user-images.githubusercontent.com/66998462/179686058-5e6d27aa-9222-4349-8f2c-b2b23e1117af.png)


References.
(https://developer.android.com/guide/topics/ui/layout/recyclerview)  <br/>
(https://developer.android.com/training/data-storage/shared-preferences)

Buy me a coffee!  
