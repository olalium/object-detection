package com.projects.objectdetection.darknet.directoryHelper

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.createDirectory
import kotlin.io.path.createFile

// TODO make env variables

@Service
class DarknetDirectoryService {
    private val log = LoggerFactory.getLogger(this.javaClass)
    fun getDarknetDirectory(): File {
        return  File("../darknet")
    }

    fun getTempDirectoryPath(): String {
        return "../darknet/data/temp"
    }

    fun getResultFilePath(): String {
        return "data/result.json"
    }

    fun getTempFileDirectory(): File {
        return  File(getTempDirectoryPath())
    }

    fun storeImagesAndGetImageListFile(images: List<MultipartFile>): File {
        Path(getTempDirectoryPath()).createDirectory()
        val tempImageList = Path(getTempDirectoryPath(), "imageList.txt").createFile().toFile()
        log.info("Created temp image list file: ${tempImageList.absolutePath}")
        images.forEach {
            val tempFilePath = Path(getTempDirectoryPath(), it.originalFilename!!)
            val file = tempFilePath.createFile()
            log.info("Created temp image file: $tempFilePath")
            it.transferTo(file)
            tempImageList.appendText(tempFilePath.absolutePathString().plus("\n"))
        }
        return tempImageList
    }

    fun deleteImageList(){
        getTempFileDirectory().deleteRecursively()
    }


}