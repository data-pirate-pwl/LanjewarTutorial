package com.example.lanjewartutorial.proFrag.update

import android.net.Uri

class Upload(name:String,url:String)
{
    var name :String
    var url: String



    init {
        if(name.trim().equals(""))
        {
            this.name="No name"
        }
        else
            this.name=name
        this.url=url
    }


    fun getname() : String = name
    fun geturl() : String = url
    fun setname(name:String)
    {
        this.name=name
    }
    fun seturl(url:String)
    {
        this.url=url
    }


}