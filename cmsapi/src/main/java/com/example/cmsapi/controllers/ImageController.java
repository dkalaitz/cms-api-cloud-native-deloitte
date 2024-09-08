package com.example.cmsapi.controllers;

import com.example.cmsapi.errors.responses.ErrorResponse;
import com.example.cmsapi.model.Article;
import com.example.cmsapi.model.Image;
import com.example.cmsapi.services.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
@Tag(name = "Image Management", description = "Operations related to image management")
public class ImageController {

    @Autowired
    private ImageService imageService;

    // Upload an image
    @Operation(
            summary = "Upload an image",
            description = """
                    Uploads an image. The image file should be provided in the request body.
            
                    **Authorization Required**: You must include a valid JWT token in the `Authorization` header.
            
                    **Responses**:
                    - `201 Created`: Image successfully uploaded.
                    - `401 Unauthorized`: Invalid JWT token.
                    - `403 Forbidden`: Missing JWT token.
                    - `409 Conflict`: Image with the same name already exists.
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Image object to be uploaded. The object should include the path of the image file.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Image.class),
                            examples = @ExampleObject(
                                    value = "{ \"image\": \"/path/to/image.jpg\" }"
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Image successfully uploaded. Returns uploaded image",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Article.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Authentication is required to access this resource",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class,
                                    description = "Unauthorized error response structure"
                            ),
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"UNAUTHORIZED\", \"message\": \"You are not authorized to access this resource.\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - Access is denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class,
                                    description = "Forbidden error response structure"
                            ),
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"FORBIDDEN\", \"message\": \"You do not have permission to access this resource.\" }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "409",
                    description = "Image already exists conflict",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"RESOURCE_EXISTS\", \"message\": \"Image already exists.\" }")
                    ))
    })
    @PostMapping
    public ResponseEntity<?> uploadImage(@Valid @RequestBody Image image) {

        // Call service method to handle the image upload
        Image uploadedImage = imageService.upload(image);

        // Return a success message with HTTP 200 OK status
        return new ResponseEntity<>(uploadedImage, HttpStatus.OK);
    }

    // Retrieve an image by its ID
    @Operation(
            summary = "Retrieve an image",
            description = """
                    Retrieve an image based on its ID.
            
                    **Responses**:
                    - `200 OK`: Returns the retrieved image as binary data.
                    - `404 Not Found`: The image with the specified ID does not exist.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "404",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(name = "Image Not Found Example",
                                    value = "{ \"error\": \"RESOURCE_NOT_FOUND\", \"message\": \"The image with the specified ID does not exist.\" }")
                    ))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Image> retrieveImage(@PathVariable Long id) {

        // Call service method to get the image by ID
        Image image = imageService.retrieve(id);

        // Return the retrieved image with HTTP 200 OK status
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}
