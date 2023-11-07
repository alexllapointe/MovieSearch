package edu.iu.alex.moviesearch

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the toolbar as the app bar for the activity
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_fragment_container, MainFragment())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("MainActivity", "Menu item clicked: ${item.itemId}")
        return when (item.itemId) {
            R.id.action_feedback -> {
                sendFeedbackEmail()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

            /*
            When the feedback menu item is clicked, this method is triggered.

            The method is set up to allow the user to open their desired email app,
            and then once the app is clicked the subject line and recipient(dev) are pre-determined.

            User submit feedback.

            */

    private fun sendFeedbackEmail() {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("alapointe2003@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Feedback")
        }
        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(emailIntent, "Choose an email client..."))
        } else {
            Log.d("MainActivity", "No email app available.")
            Toast.makeText(this, "No email app available.", Toast.LENGTH_LONG).show()
        }
    }
}
