package com.projects.objectdetection.darknet

import com.projects.objectdetection.darknet.directoryHelper.DarknetDirectoryService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class DarknetService(private val darknetDirectoryService: DarknetDirectoryService){

    fun batchGetObjectDetections(files: List<MultipartFile>): String {
        val imageListFile = darknetDirectoryService.storeImagesAndGetImageListFile(files)
        val result = ProcessBuilder("cmd","/c","darknet detector test cfg/coco.data cfg/yolov7.cfg yolov7.weights -ext_output -dont_show -out ${darknetDirectoryService.getResultFilePath()} < ${imageListFile.absolutePath}").directory(darknetDirectoryService.getDarknetDirectory())
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()
        darknetDirectoryService.deleteImageList()

        val resultFile = darknetDirectoryService.getDarknetDirectory().resolve(darknetDirectoryService.getResultFilePath())

        return resultFile.readText()
    }
}