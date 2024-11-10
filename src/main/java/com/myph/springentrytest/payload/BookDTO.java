package com.myph.springentrytest.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private String id;

    @NotBlank(message = "Title must not be empty")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @NotBlank(message = "Author must not be empty")
    @Pattern(regexp = "^[a-zA-ZàáạảãăắằẳẵặâấầẩẫậèéẹẻẽêếềểễệìíịỉĩòóọỏõôốồổỗộơớờởỡợùúụủũưứừửữựỳýỵỷỹđĐ\\s]+$", message = "Author name can only contain letters, accents, and spaces")
    @Size(max = 50, message = "Author name must not exceed 50 characters")
    private String author;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @PastOrPresent(message = "Published date must be in the present or past")
    private LocalDate publishedDate;

    @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$", message = "Invalid ISBN format")
    private String isbn;

    @NotNull(message = "Price must not be empty")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;
}
