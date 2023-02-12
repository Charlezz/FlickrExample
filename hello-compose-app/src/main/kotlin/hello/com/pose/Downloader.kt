package hello.com.pose

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.net.URL

class Downloader(private val context: Context) {

    fun download(url: String, fileName: String) {
        saveFileUsingMediaStore(url, fileName)
    }

    @SuppressLint("Range")
    private fun saveFileUsingMediaStore(url: String, fileName: String) {
        val uri = fileName.run {
            existFileUri() ?: newFileUri() ?: throw IllegalStateException("Can't create file uri")
        }

        URL(url).openStream().use { input ->
            context.contentResolver.openOutputStream(uri, "rwt").use { output ->
                input.copyTo(output!!, DEFAULT_BUFFER_SIZE)
            }
        }
    }

    fun isFileValid(fileName: String): Boolean {
        return fileName.existFileUri() != null || fileName.newFileUri() != null
    }

    private fun String.newFileUri(): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, this@newFileUri)
            put(MediaStore.MediaColumns.MIME_TYPE, "png")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }
        val resolver = context.contentResolver
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        } else {
            return null
        }
    }

    @SuppressLint("Range")
    private fun String.existFileUri(): Uri? {
        val contentUri = MediaStore.Files.getContentUri("external")
        val selection = MediaStore.MediaColumns.RELATIVE_PATH + "=?"
        val selectionArgs = arrayOf("${Environment.DIRECTORY_DOWNLOADS}/")
        val cursor = context.contentResolver.query(contentUri, null, selection, selectionArgs, null)
        if (cursor != null && cursor.count != 0) {
            while (cursor.moveToNext()) {
                val name =
                    cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME))
                if (this == name) {
                    val id = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
                    return ContentUris.withAppendedId(contentUri, id);
                }
            }
        }
        return null
    }
}

