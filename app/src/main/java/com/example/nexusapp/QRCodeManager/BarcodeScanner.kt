package com.example.nexusapp.QRCodeManager

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class BarcodeScanner(
    private val onQrCodeScanned: (String) -> Unit
):ImageAnalysis.Analyzer {
    private lateinit var barcodeScanner: BarcodeScanner
    @OptIn(ExperimentalGetImage::class) override fun analyze(imageProxy: ImageProxy) {
        barcodeScanner = BarcodeScanning.getClient()
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)


            barcodeScanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        // Handle barcode data
                        val value = barcode.displayValue

                        // Do something with the barcode value
                        onQrCodeScanned(value!!)
                    }
                }
                .addOnFailureListener {
                    // Handle failure
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }

}