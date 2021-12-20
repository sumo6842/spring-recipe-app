package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.model.Recipe;
import com.springframework.springrecipeapp.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final RecipeRepository repository;

    @Transactional
    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        try {
            var result = repository.findById(recipeId);
            Byte[] bytes = new Byte[file.getBytes().length];
            int i = 0;
            for(byte b : file.getBytes()) {
                bytes[i++] = b;
            }
            result.ifPresent(x -> {
                x.setImage(bytes);
                repository.save(x);
            });

        } catch (Exception e) {
            log.error("Error " + e);
            e.printStackTrace();
        }

        System.out.println("I'm here");
        log.info("Recipe a file");
    }
}
