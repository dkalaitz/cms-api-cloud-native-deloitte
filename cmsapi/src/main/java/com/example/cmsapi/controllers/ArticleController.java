package com.example.cmsapi.controllers;

import com.example.cmsapi.errors.responses.ErrorResponse;
import com.example.cmsapi.errors.responses.ValidationErrorResponse;
import com.example.cmsapi.model.Article;
import com.example.cmsapi.dto.ArticleDTO;
import com.example.cmsapi.services.ArticleService;
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

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@Tag(name = "Article Management", description = "Operations related to article management")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // Retrieve all articles
    @Operation(
            summary = "Retrieve all articles",
            description = """
                    Retrieve all articles.
                    
                    **Responses:**
                    - `200 OK`: Returns the retrieved articles"""
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Article.class))),
    })
    @GetMapping
    public ResponseEntity<List<Article>> retrieveAllArticles() {
        // Call the service to get all articles and return with HTTP 200 OK status
        return ResponseEntity.ok(articleService.retrieveAll());
    }

    // Retrieve a specific article by its ID
    @Operation(
            summary = "Retrieve an article",
            description = """
                    Retrieve an article based on its ID.
                    
                    **Responses:**
                    - `200 OK`: Returns the retrieved article
                    - `404 Not Found`: The article with the specified ID does not exist."""
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article successfully retrieved",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "404",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"RESOURCE_NOT_FOUND\", \"message\": \"The article with the specified ID does not exist.\" }")
                    ))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveArticleById(@PathVariable Long id) {
        // Call the service to get the article by ID and return with HTTP 200 OK status
        return ResponseEntity.ok(articleService.retrieveById(id));
    }

    // Create a new article
    @Operation(
            summary = "Create a new article",
            description = """
                    Creates a new article and returns it.
                    
                    **Authorization Required**: You must include a valid JWT token in the `Authorization` header.
                    
                    **Responses:**
                    - `201 Created`: Returns the created article
                    - `400 Bad Request`: Returns a list of validation errors
                    - `401 Unauthorized`: Invalid JWT token.
                    - `403 Forbidden`: Missing JWT token.
                    - `404 Not Found`: The image with the specified path does not exist.""",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Article details",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ArticleDTO.class)
                    )
            )
    )
    @ApiResponses (value = {
            @ApiResponse(responseCode = "201",
                    description = "Article successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Article.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Validation errors",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ValidationErrorResponse.class,
                                    description = "Validation error response structure"
                            ),
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"VALIDATION_ERROR\", \"messages\": [\"Title cannot be blank or null\", \"Content cannot be blank or null\"] }"
                            )
                    )
            ),
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
            @ApiResponse(responseCode = "404",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"RESOURCE_NOT_FOUND\", \"message\": \"The image with the specified path does not exist.\" }")
                    )),
    })
    @PostMapping
    public ResponseEntity<?> createArticle(@Valid @RequestBody ArticleDTO articleDTO) {

        // Call the service to create a new article
        Article createdArticle = articleService.create(articleDTO);

        // Return the created article with HTTP 201 Created status
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    // Update an existing article
    @Operation(
            summary = "Update an existing article",
            description = """
                    Updates an article based on ID and returns the updated article.
                    
                    **Authorization Required**: You must include a valid JWT token in the `Authorization` header.
                    
                    **Responses:**
                    - `201 Created`: Article successfully created.
                    - `400 Bad Request`: Returns a list of validation errors
                    - `401 Unauthorized`: Invalid JWT token.
                    - `403 Forbidden`: Missing JWT token.
                    - `404 Not Found`: The article with the specified ID does not exist.
                    - `404 Not Found`: The image with the specified path does not exist.""",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Article details",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ArticleDTO.class)
                    )
            )
    )
    @ApiResponses (value = {
            @ApiResponse(responseCode = "201",
                    description = "Article successfully updated, returns the updated article",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Article.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Validation errors",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ValidationErrorResponse.class,
                                    description = "Validation error response structure"
                            ),
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"VALIDATION_ERROR\", \"messages\": [\"Title cannot be blank or null\", \"Content cannot be blank or null\"] }"
                            )
                    )
            ),
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
            @ApiResponse(responseCode = "404",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"RESOURCE_NOT_FOUND\", \"message\": \"The article with the specified ID does not exist.\" }")
                    )),
            @ApiResponse(responseCode = "404",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"RESOURCE_NOT_FOUND\", \"message\": \"The image with the specified path does not exist.\" }")
                    )),
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleDTO updatedArticleDetails) {

        // Call the service to update the article with the given ID
        Article updatedArticle = articleService.update(id, updatedArticleDetails);

        // Return updated article with HTTP 200 OK status
        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }

    // Delete an article by its ID
    @Operation(
            summary = "Delete an existing article",
            description = """
                    Deletes an article based on ID and returns a response.
                    
                    **Authorization Required**: You must include a valid JWT token in the `Authorization` header.
                    
                    **Responses:**
                    - `204 No Content`: Deleted successfully,
                    - `401 Unauthorized`: Invalid JWT token.
                    - `403 Forbidden`: Missing JWT token.
                    - `404 Not Found`: The article with the specified ID does not exist."""
    )
    @ApiResponses (value = {
            @ApiResponse(responseCode = "204",
                    description = "Successfully deleted article.",
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
            @ApiResponse(responseCode = "404",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"error\": \"RESOURCE_NOT_FOUND\", \"message\": \"The article with the specified ID does not exist.\" }")
                    ))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {

        // Call the service to delete the article with the given ID
        articleService.delete(id);

        // Return a message with HTTP 204 NO CONTENT status
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}