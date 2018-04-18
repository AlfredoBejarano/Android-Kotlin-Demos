package com.alfredobejarano.kotlinlocalserver

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import kotlinx.android.synthetic.main.activity_main.*

/**
* Constant that defines the port in which the server will listen to.
**/
const val PORT = 3000
/**
* Default endpoint for the server.
**/
const val ROOT_ENDPOINT = "/"
/**
* Defines the value of a fully loaded web page in a WebView.
**/
const val WEB_VIEW_PROGRESS_FULL = 100v
/**
* URL in which the server will respond.
**/
const val LOCAL_SERVER_URL = "http://localhost:$PORT/"
/**
* JSON that the server will respond.
**/
const val JSON_RESPONSE = "{message:\"Hello world!\"}"

class MainActivity : AppCompatActivity() {
    /**
    * Variable that holds a [NettyApplicationEngine] reference.
    **/
    private var server: NettyApplicationEngine? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupServer()
        setupWebView()
    }

    /**
     * Initializes the [server] property and
     * sets a single endpoint in the root that
     * responds with the [JSON_RESPONSE] constant.
     */
    private fun setupServer() {
        server = embeddedServer(Netty, PORT) {
            routing { // Define the endpoints for responses.
                get(ROOT_ENDPOINT) {
                    call.respondText(JSON_RESPONSE, ContentType.Application.Json) // Respond the [JSON_RESPONSE] value defining in the headder that the response is a JSON.
                }
            }
        }
        server?.start(wait = false) // Start the server, don't freeze the app while waiting for it to finish.
    }

    /**
     * Setups the WebView and loads the localhost URL.
     */
    private fun setupWebView() {
        web_view.webChromeClient = object : WebChromeClient() { // Sets a WebChromeClient isntance to the WebView.
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                val visibility = if (newProgress == WEB_VIEW_PROGRESS_FULL) View.GONE else View.VISIBLE // Set this value if the WebView has finished loading.
                web_view_progress.visibility = visibility // Set the visibility value for the ProgressBar.
                web_view_progress_indicator.visibility = visibility // Set the visibility value for the TextView that displays the WebView progress value.
                web_view_progress_indicator.text = "$newProgress%" // Set the progress numeric value as text in the indicator TextView.
        
            }
        }
        web_view.setBackgroundColor(Color.TRANSPARENT) // Set the WebView web page background color as transparent.
        web_view.loadUrl(LOCAL_SERVER_URL) // Load the server's URL.
    }
}
