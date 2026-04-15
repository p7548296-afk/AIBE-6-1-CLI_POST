package global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class Pageable {
    private final int page;
    private final int pageSize;

    public int getOffset() {
        return (page - 1) * pageSize;
    }
}
