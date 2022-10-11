package org.almondiz.almondiz.category;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.category.entity.Category;
import org.almondiz.almondiz.category.entity.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category create(String categoryName) {
        Category category = Category.builder()
                                    .categoryName(categoryName)
                                    .build();

        categoryRepository.save(category);
        return category;
    }
}
