public class UserSalutationDto {
    
    private Long id;
    private String title;
    private LocalDateTime createdAt;

    public final UserSalutationDto setId(Long id) {
        this.id = id;
        return this;
    }

    public final UserSalutationDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public final UserSalutationDto setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
