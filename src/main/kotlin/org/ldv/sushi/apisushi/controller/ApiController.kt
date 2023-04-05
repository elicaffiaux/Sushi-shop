package org.ldv.sushi.apisushi.controller

import org.ldv.sushi.apisushi.dto.BoxDtoJson
import org.ldv.sushi.apisushi.repository.BoxRepository
import org.ldv.sushi.apisushi.util.fromBoxToBoxDtoJson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["*"])
@RestController
class ApiController  @Autowired constructor(private val boxRepository: BoxRepository) {

    @GetMapping("/api/boxes")
    fun allBoxes(): ResponseEntity<List<BoxDtoJson>> {
        return ResponseEntity.ok(this.boxRepository.findAll().map { fromBoxToBoxDtoJson(it) })
    }
    @GetMapping("/api/images/{imageName:.+}")
    fun getImage(@PathVariable imageName: String): ResponseEntity<Resource> {
        val image = ClassPathResource("/static/images/$imageName.jpg")
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
            .body(image)

}@GetMapping("/api/boxes/{id}")
    fun getBox(@PathVariable id: Long): ResponseEntity<BoxDtoJson> {
        val box = this.boxRepository.findById(id)
        return if (box.isPresent) {
            ResponseEntity.ok(fromBoxToBoxDtoJson(box.get()))
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
