package com.projects.objectdetection.objectdetection

import com.projects.objectdetection.darknet.DarknetService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v0/image")
class ObjectDetectionController(private val darknetService: DarknetService) {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @PostMapping("/detect-objects")
    fun processImage(@RequestBody files: List<MultipartFile>): ResponseEntity<String> {
        log.info("Processing files: ${files.size}")
        val start = System.currentTimeMillis()
        val pdfText = darknetService.batchGetObjectDetections(files)
        log.info("Processing files: ${files.size} took ${System.currentTimeMillis() - start}ms")
        return ResponseEntity.ok(pdfText)
    }
}