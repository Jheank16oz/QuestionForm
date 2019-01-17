package com.jheank16oz.questionform

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.widget.Toast
import com.jheank16oz.questionform.Functions.getCompressImage
import java.io.File

/**
 *
 *  <p>CameraUtil</p>
 */
class CameraUtil(val fragment: Fragment?) {


    /**
     * Objetivo para almacenar la ruta de la imagen capturada,
     * posterior a ser escaneada con MediaScanner.
     */
    private var selectedFilePath: String? = null

    private var currentCamerListener:OnCameraListener? = null
    /**
     * Objetivo para el directorio donde se almacenara las fotos
     * capturadas mediante Intent de Camera.
     */
    private val MEDIA_DIRECTORY = "Camera/"

    /**
     * Objetivo para almacenar la ruta de la imagen capturada.
     */
    private var mPath: String? = null

    /**
     * Request Code para el intent de camara.
     */
    private val PHOTO_CODE = 5

    /**
     * Se encarga de iniciar la camara para las opciones
     * de tipo imagen, aqui se crea  el archivo destino para la imagen
     * capturada, ademas de validar los permisos de almacenamiento.
     */
    fun openCamera(cameraListener: OnCameraListener?) {
        this.currentCamerListener = cameraListener
        if (fragment == null) {
            return
        }

        if (Functions.checkPermission(fragment)) {
            return
        }

        val file = File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY)
        var isDirectoryCreated = file.exists()

        if (!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs()

        if (isDirectoryCreated) {
            val timestamp = System.currentTimeMillis() / 1000
            val imageName = timestamp.toString() + ".jpg"

            mPath = Environment.getExternalStorageDirectory().toString() + File.separator + MEDIA_DIRECTORY + File.separator + imageName

            val newFile = File(mPath)

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            val uriFromFile: Uri
            if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                uriFromFile = FileProvider.getUriForFile(fragment.context!!,
                        BuildConfig.APPLICATION_ID + ".provider",
                        newFile)
            } else {
                uriFromFile = Uri.fromFile(newFile)
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFromFile)

            try {
                fragment.startActivityForResult(intent, PHOTO_CODE)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(fragment.context, R.string.mensaje_camara_no_encontrada, Toast.LENGTH_SHORT).show()
            }


        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PHOTO_CODE) run {
                MediaScannerConnection.scanFile(fragment?.context,
                        arrayOf(mPath!!), null
                ) { path, uriX ->
                    object : Thread() {
                        override fun run() {
                            fragment?.activity?.runOnUiThread {
                                selectedFilePath = FilePath.getPath(fragment.context, uriX)
                                if (selectedFilePath != null && selectedFilePath != "") {
                                    var fileSelected = File(selectedFilePath)
                                    fileSelected = getCompressImage(selectedFilePath, fileSelected)
                                    currentCamerListener?.onFileResult(fileSelected)
                                }
                            }
                        }
                    }.start()
                }
            }
        }
    }


    interface OnCameraListener{
        fun onFileResult(file:File)
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == Functions.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera(currentCamerListener)
            }
        }
    }
}