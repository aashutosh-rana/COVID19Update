package com.bcebhagalpur.covid19_update.classes

import okhttp3.OkHttpClient
import okhttp3.Request


object Client{
    private  val okHttpClient= OkHttpClient()

    private  val request=  Request.Builder().url("https://api.covid19india.org/data.json").build()

    val api= okHttpClient.newCall(
        request
    )

//    var client = OkHttpClient()
//    var request = OkHttpRequest(client)
//fun GET(url: String, callback: Callback): Call {
//    val request = Request.Builder()
//        .url(url)
//        .build()
//
//    val call = client.newCall(request)
//    call.enqueue(callback)
//    return call
}

//OkHttpClient client = new OkHttpClient();
//
//RequestBody requestBody = new MultipartBody.Builder()
//.setType(MultipartBody.FORM)
//.addFormDataPart("request_magic_code", Urls.REQUEST_MAGIC_CODE)
//.build();
//
//Request request = new Request.Builder()
//.url(Urls.HOST + Urls.SEND_PHONE_NUMBER)
//.post(requestBody)
//.build();
//
//client.newCall(request).enqueue(this);
//return success;