package deps.practice.myapplication

import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import deps.practice.myapplication.deserializers.WeatherDeserializer
import deps.practice.myapplication.models.WeatherObject
import deps.practice.myapplication.networking.NetworkClient
import kotlinx.android.synthetic.main.activity_gson_deserializer_async.*
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader

class GsonDeserializerOkhttpAsyncActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gson_deserializer_async)

        GetJsonWithOkHttpClient(this.text).execute()

    }

    open class GetJsonWithOkHttpClient(textView: TextView) : AsyncTask<Unit, Unit, String>() {

        val mInnerTextView = textView

        override fun doInBackground(vararg params: Unit?): String? {
            val networkClient = NetworkClient()
            val stream = BufferedInputStream(
                networkClient.get("https://raw.githubusercontent.com/irontec/android-kotlin-samples/master/common-data/bilbao.json"))
            return readStream(stream)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)


            val gsonBuilder = GsonBuilder().serializeNulls()
            gsonBuilder.registerTypeAdapter(WeatherObject::class.java, WeatherDeserializer())
            val gson = gsonBuilder.create()
            val weather = gson.fromJson(result, WeatherObject::class.java)

            mInnerTextView.text = weather.toString()

        }

        private fun readStream(inputStream: BufferedInputStream): String {
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            bufferedReader.forEachLine { stringBuilder.append(it) }
            return stringBuilder.toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_settings) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}