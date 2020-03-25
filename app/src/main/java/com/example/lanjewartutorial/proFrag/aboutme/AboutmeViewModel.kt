package com.example.lanjewartutorial.proFrag.aboutme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutmeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Github:: rj-priyanshu"
    }
    val text: LiveData<String> = _text
}