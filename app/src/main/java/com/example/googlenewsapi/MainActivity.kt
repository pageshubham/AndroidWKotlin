package com.example.googlenewsapi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.googlenewsapi.beans.Articles
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.indiview.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getNews.setOnClickListener{
            var r:Retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://newsapi.org/").build()
            var api = r.create(NewsAPI::class.java)
            var call:Call<Articles> = api.getNews()
            call.enqueue(object : Callback<Articles> {
                override fun onResponse(call: Call<Articles>, response: Response<Articles>) {
                    var artcls:Articles? = response.body()

                    lview.adapter = object : BaseAdapter() {
                        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                            var inflater = LayoutInflater.from(this@MainActivity)
                            var v = inflater.inflate(R.layout.indiview,null)
                            v.title.text = artcls?.articles!![position].title
                            v.desc.text = artcls?.articles!![position].description
                            Glide.with(this@MainActivity).load(artcls?.articles!![position].urlToImage).into(v.iview)
                            return v
                        }

                        override fun getItem(position: Int): Any = 0

                        override fun getItemId(position: Int): Long = 0

                        override fun getCount(): Int = artcls?.articles!!.size

                    }
                    lview.setOnItemClickListener { adapterView, view, i, l ->

                        var it = Intent(this@MainActivity, BrowserActivity::class.java)
                        it.putExtra("url",artcls?.articles!!.get(i).url)
                        startActivity(it)

                    }

                }

                override fun onFailure(call: Call<Articles>, t: Throwable) {
                    Toast.makeText(this@MainActivity,"Failed To Get News",Toast.LENGTH_LONG).show()
                }

            })
        }
    }
}
