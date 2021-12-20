package com.springframework.springrecipeapp.controller;

import com.springframework.springrecipeapp.commands.RecipeCommand;
import com.springframework.springrecipeapp.services.ImageService;
import com.springframework.springrecipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ImageControllerTest {
    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    ImageController imageController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageController = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void handleImagePost() throws Exception {
        //given
        MockMultipartFile multipartFile =
                new MockMultipartFile("file", "test.txt", "text/plain",
                        "Spring framework".getBytes());

        //when
        this.mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", "/"));
    }

    @Test
    void renderImageFromDB() throws Exception {
        //given:
        var command = new RecipeCommand();
        command.setId(1L);
        String s = "fake image text";
        Byte[] bytesBoxed = new Byte[s.getBytes().length];

        int i = 0;

        for (byte primByte : s.getBytes()) {
            bytesBoxed[i++] = primByte;
        }

        command.setImage(bytesBoxed);
        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        //when

        var response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        byte[] responseByte = response.getContentAsByteArray();
        assertEquals(s.getBytes().length, responseByte.length);

    }
}