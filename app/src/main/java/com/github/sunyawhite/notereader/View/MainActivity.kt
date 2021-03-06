package com.github.sunyawhite.notereader.View

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.github.sunyawhite.notereader.R
import com.github.sunyawhite.notereader.View.Fragments.DisplayNoteFragment
import com.github.sunyawhite.notereader.View.Fragments.NoteRecycleView.NoteFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(),
    NoteFragment.OnListFragmentInteractionListener{

    private val PERMISSION_REQUEST_CODE = 4
    // Required permissions
    private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.INTERNET)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!checkPermissions())
            requestPermissions()

        // Задаем функцию перехода для иконки добавления новой заметки
        val button : FloatingActionButton = findViewById(R.id.floatingActionButton)
        button.setOnClickListener{ v: View? ->
            // Переходим к activity для добавления новой заметки
            startActivity(Intent(this, CameraActivity::class.java))
        }

        if(savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainDynamicFragment, NoteFragment.newInstance(), NoteFragment.TAG)
                .addToBackStack(null)
                .commit()
    }

    // Маршрутизация. Переходим к просмотру отдельной заметки
    override fun onListClick(id : Long) {
        if(supportFragmentManager.findFragmentByTag(DisplayNoteFragment.TAG) != null)
            supportFragmentManager.popBackStack()

        // Переход к другому фрагменту
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter, R.anim.exit)
            .replace(R.id.mainHelperFragment, DisplayNoteFragment.newInstance(id), DisplayNoteFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    // Маршрутизация. Переходим к редактированию отдельной заметки
    override fun onEditButtonClick(id: Long) {
        startActivity(Intent(this, EditNoteActivity::class.java).apply
        {
            putExtra(EditNoteActivity.TAG, id)
        })
    }

    // Запускаем функцию Share
    override fun onShareButtonClick(intent: Intent) {
        startActivity(intent)
    }

    private fun checkPermissions() = REQUIRED_PERMISSIONS.all { permission ->
        ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions(){
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_REQUEST_CODE -> {
                if(grantResults.isNotEmpty() && checkPermissions()) {
                    Toast.makeText(this, "Permission are granted", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this,"Permission are not granted", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            else -> {
            }
        }
    }


    override fun onBackPressed() {
        when(supportFragmentManager.backStackEntryCount){
            1 -> finish()
            else -> super.onBackPressed()
        }
    }
}
