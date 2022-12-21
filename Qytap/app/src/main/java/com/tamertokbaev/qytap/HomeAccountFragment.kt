package com.tamertokbaev.qytap

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.tamertokbaev.qytap.globals.Constants
import com.tamertokbaev.qytap.models.ProfileImage
import com.tamertokbaev.qytap.services.BookManipulationService
import com.tamertokbaev.qytap.services.ServiceBuilder
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.IOException

class HomeAccountFragment : Fragment() {
    var imageView: ImageView? = null;
    private var imageData: MultipartBody.Part? = null
    private var bearerToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home_account, container, false)
        val welcomeTextView = rootView.findViewById<TextView>(R.id.home_welcome_text)

        val sharedPreferences =
            activity?.getSharedPreferences(Constants.APP_SHARED_PREF_NAME, Context.MODE_PRIVATE)

        val email: String? =
            sharedPreferences?.getString(Constants.APP_SHARED_USER_EMAIL_KEY, "Empty email")
        val name: String? =
            sharedPreferences?.getString(Constants.APP_SHARED_USER_NAME_KEY, "Empty name")

        welcomeTextView.text = "Welcome, ${name}"


        // Inflate the layout for this fragment
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = view.context.getSharedPreferences(Constants.APP_SHARED_PREF_NAME, Context.MODE_PRIVATE)
        this.bearerToken = preferences.getString(Constants.APP_SHARED_USER_TOKEN_KEY, "")
        imageView = view.findViewById<ImageView>(R.id.home_avatar)
        imageView?.setOnClickListener {
            launchGallery()
        }
    }

    private fun launchGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 999)
    }

    private fun uploadImage(data: MultipartBody.Part) {
        imageData?: return
        val destinationService = ServiceBuilder.buildService(BookManipulationService::class.java)

        val requestCall =
            destinationService.uploadProfilePhoto(this.bearerToken, data)
        requestCall.enqueue(object : retrofit2.Callback<Any> {
            override fun onResponse(
                call: retrofit2.Call<Any>,
                response: retrofit2.Response<Any>
            ) {
                Log.d("Response", "onResponse: ${response.body()}")
            }

            override fun onFailure(call: retrofit2.Call<Any>, t: Throwable) {
                Log.d("Exception", "Occurred exception: ${t}")
                Toast.makeText(
                    view?.context,
                    "Something went wrong $t",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    @Throws(IOException::class)
    private fun createImageData(uri: Uri) {
        val inputStream = activity?.contentResolver?.openInputStream(uri)
        val request = RequestBody.create(MediaType.parse("image/*"), inputStream!!.readBytes())
        imageData = MultipartBody.Part.createFormData(
            "image",
            "test.jpg",
            request
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 999) {
            val uri = data?.data
            if (uri != null) {
                imageView?.setImageURI(uri)
                createImageData(uri)
            }
        }
        uploadImage(this.imageData!!)
        super.onActivityResult(requestCode, resultCode, data)
    }
}