package util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Page<T> {
    private final List<T> content;
    private final int currentPage;
    private final int totalPages;
}
